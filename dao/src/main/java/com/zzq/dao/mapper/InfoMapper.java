package com.zzq.dao.mapper;

import com.zzq.dao.model.pojo.EmSite;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InfoMapper {

    @Select("SELECT * FROM em_site")
    List<EmSite> findSite();
}
