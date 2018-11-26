springboot集成mybatis通用mapper和pagehelper
（问题解决了，具体问题看最后吧，这是泪崩的，这个问题纠结了好久，蛋疼！）

问题描述

多模块项目，子模块集成Pagehelper后通用mapper分页失效
--------------------------
项目是父子项目，基于springboot，版本是1.5.16.RELEASE：

parent：
 -api
 -core

在api中gradle配置是：
```gradle
 compile group: 'org.mybatis.spring.boot', name: 'mybatis-spring-boot-starter', version: '1.3.2'
 compile group: 'tk.mybatis', name: 'mapper-spring-boot-starter', version: '2.0.4'
 compile ("com.github.pagehelper:pagehelper-spring-boot-starter:1.2.10"){
        exclude group: 'org.mybatis.spring.boot', module: 'mybatis-spring-boot-starter' //by both name and group
    }
```
application.yml配置是：
```yml
# MyBatis
mybatis:
  typeAliasesPackage: xx.xx.xx.xx.model.pojo
  mapperLocations: classpath:mapper/*.xml
  configLocation: classpath:mybatis-conf.xml

mapper:
  mappers: tk.mybatis.mapper.common.Mapper
  notEmpty: true
  identity: MYSQL

pagehelper:
  helperDialect: mysql
  reasonable: true
  supportMethodsArguments: true
  params: count=countSql
```
项目的层级是：service->mapper，在通用mapper集成后分页就不起作用了，自己写的mapper里面分页能够起作用。
自己写的mapper
```java
//mapper信息
public interface InfoMapper {

    @Select("SELECT * FROM em_site")
    List<EmSite> findSite();
}
//test信息
PageHelper.startPage(0, 2);
List<EmSite> site = infoMapper.findSite();
PageInfo<EmSite> pageInfo = new PageInfo<>(site);
System.out.println(pageInfo);
```
上述正确显示了分页，pages显示正确（总共有5条记录，pages=5）。集成通用mapper后分页失效。
通用mapper查询
```java 
//mapper结构
public interface EmSiteMapper extends Mapper<EmSite> , MySqlMapper<EmSite>{}
//service里面调用
Example example = new Example.Builder(EmSite.class).where(Sqls.custom()
                .andEqualTo("uid", uid)).build();
PageHelper.startPage(num, size);
List<EmSite> sites = emSiteMapper.selectByExample(example);
PageInfo<EmSite> pageInfo = new PageInfo<>(sites , 5);
 System.out.println(pageInfo);
```
这里pages显示为和size一样的大小（size=1，pages=1;size=2,pages=2）。

sql信息
```sql
-- ----------------------------
-- Table structure for `em_site`
-- ----------------------------
DROP TABLE IF EXISTS `em_site`;
CREATE TABLE `em_site` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) NOT NULL COMMENT '用户的id',
  `name` varchar(128) DEFAULT NULL COMMENT '网站的名称信息',
  `url` varchar(255) NOT NULL COMMENT '网络的url信息',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间，主动更新',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of em_site
-- ----------------------------
INSERT INTO `em_site` VALUES ('1', '12312', '共享', 'https://www.zzq.org.cn', '1542705391242', '2018-11-22 16:03:41');
INSERT INTO `em_site` VALUES ('2', '12312', '共享', 'https://www.zzq1.org.cn', '1542705446545', '2018-11-22 16:03:43');
INSERT INTO `em_site` VALUES ('3', '12312', '共享', 'http://share.zzq2.net.cn', '1542706587154', '2018-11-22 16:03:45');
INSERT INTO `em_site` VALUES ('4', '12312', '共享', 'http://share.zzq3.net.cn', '1542706675007', '2018-11-22 16:03:47');
INSERT INTO `em_site` VALUES ('5', '12312', '共享', 'http://share.zzq.net.cn', '1542706702309', '2018-11-22 16:03:53');

```

失效的原因在于我在查询出list后又进行了一步操作，如下所示：
```java
PageHelper.startPage(num, size);
List<EmSite> sites = emSiteMapper.selectByExample(example);
//将原来的数据库的bean转换一下后就出问题了
List<SiteVo> svoList = new ArrayList<>();
sites.forEach(site -> svoList.add(new SiteVo(site)));
System.out.println("************************************");
for (SiteVo site : svoList) {
   System.out.println(site);
}
System.out.println("************************************");
PageInfo<SiteVo> pageInfo = new PageInfo<>(svoList, 5);//这里分页信息会显示有问题
PageInfo<EmSite> pageInfo1 = new PageInfo<>(sites, 5);//这里不会显示有问题，是正确的!
System.out.println(pageInfo);
```
 
 具体的原因在于，在new PageInfo的时候，它有个判断这个List是不是Page对象
 ```java
  public PageInfo(List<T> list, int navigatePages) {
        super(list);
        if (list instanceof Page) {
      
        /**
        * //这里的这个page对象属于：com.github.pagehelper.Page<T>，如下所示：
        * public class Page<E> extends ArrayList<E> implements Closeable
        */
            Page page = (Page) list;
            ……
        } else if (list instanceof Collection) {
            ……
        }
        ……
    }
 ```

```
 那这个Page对象是在什么时候转的了？
 debug溯源：
 PageInterceptor.intercept:进入dialect.afterPage(resultList, parameter, rowBounds)方法 -->
 进去后是一个接口：Dialect，它有几个实现类AbstractHelperDialect、AbstractRowBoundsDialect、PageHelper。进去AbstractHelperDialect，进入PageHelper最终也会导航到AbstractHelperDialect。
 这个抽象类的afterPage方法如下：
    @Override
    public Object afterPage(List pageList, Object parameterObject, RowBounds rowBounds) {
        Page page = getLocalPage();
        if (page == null) {
            return pageList;
        }
        page.addAll(pageList);
        ……
        return page;
    }
    在这里完成了一个原生的list到pagehelper插件的Page对象的转换，具体深入的先到这里吧，后续再跟进吧，也许还是有问题的。PageHelper这边是把查询返回的model bean 和Page相关联了。
```
 
