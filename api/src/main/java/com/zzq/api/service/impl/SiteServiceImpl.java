package com.zzq.api.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzq.api.mapper.EmSiteMapper;
import com.zzq.api.model.pojo.EmSite;
import com.zzq.api.model.vo.SiteVo;
import org.apache.ibatis.session.RowBounds;
import com.zzq.api.model.Page;
import com.zzq.api.service.SiteService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SiteServiceImpl implements SiteService {

    @Resource
    private EmSiteMapper emSiteMapper;

    @Override
    public SiteVo save(SiteVo svo) {
        if (svo == null ){
            return null;
        }
        EmSite es = svo.getSitePojo();
        emSiteMapper.insert(es);
        return svo;
    }

    @Override
    public SiteVo update(SiteVo svo) {
        if (svo == null) {
            return null;
        }
        EmSite es = svo.getSitePojo();
        emSiteMapper.updateByPrimaryKey(es);
        return svo;
    }

    @Override
    public SiteVo delete(Integer siteid) {
        EmSite emSite = emSiteMapper.selectByPrimaryKey(siteid);
        emSiteMapper.deleteByPrimaryKey(siteid);
        return new SiteVo(emSite);
    }

    @Override
    public Page<SiteVo> findPage(String uid, Integer num, Integer size) {

        Example example = new Example.Builder(EmSite.class).where(Sqls.custom()
                .andEqualTo("uid", uid)).build();

        RowBounds rowBounds = new RowBounds(num, size);
        List<EmSite> emSites = emSiteMapper.selectByExampleAndRowBounds(example, rowBounds);
        System.out.println("==================*****************==============================");
        for (EmSite emSite : emSites) {
            System.out.println(emSite);
        }
        System.out.println(rowBounds.getLimit());
        System.out.println(rowBounds.getOffset());
        System.out.println("==================*****************==============================");

        PageHelper.startPage(num, size);
        List<EmSite> sites = emSiteMapper.selectByExample(example);
        List<SiteVo> svoList = new ArrayList<>();
        sites.forEach(site -> svoList.add(new SiteVo(site)));
        System.out.println("************************************");
        for (SiteVo site : svoList) {
            System.out.println(site);
        }
        System.out.println("************************************");
        PageInfo<SiteVo> pageInfo = new PageInfo<>(svoList, 5);
        System.out.println(pageInfo);

        return new Page<>();
    }
}