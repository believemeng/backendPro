package com.dspread.august.model;

import com.dspread.august.entity.MonitorEntity;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class MonitorModel {

    private long id;
    private String phoneModel;
    private String systemVersion;
    private String isRoot;
    private String appid;

    private String lnt;
    private String lat;

    private Date createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(String isRoot) {
        this.isRoot = isRoot;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLnt() {
        return lnt;
    }

    public void setLnt(String lnt) {
        this.lnt = lnt;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
    public static MonitorEntity toEntity(MonitorModel model){
        MonitorEntity entity = new MonitorEntity();
        BeanUtils.copyProperties(model,entity);
        return entity;
    }
}
