package com.zzq.api.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.zzq.api.model.pojo.EmSite;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteVo implements Serializable {

    private Integer id;
    private String uid;
    private String name;
    private String url;
    private Date updateTime;
    private Long createTime;

    public SiteVo(EmSite site){
        this.id = site.getId();
        this.uid = site.getUid();
        this.name = site.getName();
        this.url = site.getUrl();
        this.updateTime = site.getUpdateTime();
        this.createTime = site.getCreateTime();
    }

    public EmSite getSitePojo(){
        if(this == null){
            return null;
        }
        EmSite esite = new EmSite();

        esite.setId(this.getId());
        esite.setCreateTime(this.getCreateTime());
        esite.setName(this.name);
        esite.setUid(this.getUid());
        esite.setUrl(this.url);
        esite.setUpdateTime(this.updateTime);
        return esite;
    }

}
