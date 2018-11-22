package com.zzq.api.mapper;

import com.zzq.api.model.pojo.EmSite;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InfoMapper {

    @Select("SELECT * FROM em_site")
    List<EmSite> findSite();
}
