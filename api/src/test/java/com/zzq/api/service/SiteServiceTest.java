package com.zzq.api.service;

import com.zzq.api.model.Page;
import com.zzq.api.model.vo.SiteVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SiteServiceTest {

    @Resource
    private SiteService siteService;

    @Test
    public void testSave(){
        SiteVo svo = new SiteVo();
        svo.setName("共享杯");
        svo.setUid("12312");
        svo.setUrl("http://share.escience.net.cn");
        svo.setCreateTime(System.currentTimeMillis());
        SiteVo save = siteService.save(svo);
        System.out.println(save);
    }

    @Test
    public void testFind(){
        Page<SiteVo> page = siteService.findPage("12312", 1, 2);
        System.out.println("========================================");
        System.out.println(page);
        System.out.println("========================================");
    }

}
