springboot集成mybatis通用mapper和pagehelper

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