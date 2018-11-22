package com.zzq.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DatasourceTest {

    @Resource
    private DataSource dataSource;

    @Test
    public void testDS(){
        Assert.notNull(dataSource, "数据源不能为空！");
    }

}
