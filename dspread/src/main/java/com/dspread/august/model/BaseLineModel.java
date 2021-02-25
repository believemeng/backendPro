package com.dspread.august.model;

import javax.persistence.*;
import java.util.Date;


public class BaseLineModel {


    private long id;

    private String cotsModel;

    private String securityPatchLevel;

    private String cotsVersion;

    private Date createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCotsModel() {
        return cotsModel;
    }

    public void setCotsModel(String cotsModel) {
        this.cotsModel = cotsModel;
    }

    public String getSecurityPatchLevel() {
        return securityPatchLevel;
    }

    public void setSecurityPatchLevel(String securityPatchLevel) {
        this.securityPatchLevel = securityPatchLevel;
    }

    public String getCotsVersion() {
        return cotsVersion;
    }

    public void setCotsVersion(String cotsVersion) {
        this.cotsVersion = cotsVersion;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
