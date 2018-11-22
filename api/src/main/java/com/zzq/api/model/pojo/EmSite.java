/*
*
* EmSite.java
* Copyright(C) 2017-2020 http://www.escience.org.cn
* @date 2018-11-20
*/
package com.zzq.api.model.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class EmSite {
    /**
     * 
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    /**
     * 用户的id
     */
    private String uid;

    /**
     * 网站的名称信息
     */
    private String name;

    /**
     * 网络的url信息
     */
    private String url;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间，主动更新
     */
    private Date updateTime;


}