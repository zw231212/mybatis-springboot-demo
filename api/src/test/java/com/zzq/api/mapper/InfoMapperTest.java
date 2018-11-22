package com.zzq.api.mapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzq.dao.mapper.InfoMapper;
import com.zzq.dao.model.pojo.EmSite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InfoMapperTest {

    @Resource
    private InfoMapper infoMapper;

    @Test
    public void testPage(){
        PageHelper.startPage(0, 2);
        List<EmSite> site = infoMapper.findSite();
        for (EmSite emSite : site) {
            System.out.println(emSite);
        }
        PageInfo<EmSite> pageInfo = new PageInfo<>(site);
        System.out.println(pageInfo);

    }
}
