/*
 *
 * EmSiteMapper.java
 * Copyright(C) 2017-2020 http://www.escience.org.cn
* @date 2018-11-20
*/
package com.zzq.api.mapper;


import com.zzq.api.model.pojo.EmSite;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface EmSiteMapper extends Mapper<EmSite> , MySqlMapper<EmSite> {

    @Select("SELECT * FROM em_site")
    List<EmSite> findPage();
}