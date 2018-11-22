package com.zzq.api.service;

import com.zzq.api.model.Page;
import com.zzq.api.model.vo.SiteVo;

public interface SiteService {

    SiteVo save(SiteVo svo);
    SiteVo update(SiteVo svo);
    SiteVo delete(Integer siteid);

    Page<SiteVo> findPage(String uid, Integer num, Integer size);
}
