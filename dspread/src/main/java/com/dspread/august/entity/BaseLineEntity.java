package com.dspread.august.entity;

import com.dspread.august.model.BaseLineModel;
import com.dspread.august.model.MonitorModel;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "baseline")
public class BaseLineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "cots_model")
    private String cotsModel;

    @Column(name = "security_patch_level")
    private String securityPatchLevel;


    @Column(name = "cots_version")
    private String cotsVersion;


    @Column(name = "create_time")
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

    public static BaseLineModel toModel(BaseLineEntity entity){
        BaseLineModel model = new BaseLineModel();
        BeanUtils.copyProperties(entity,model);
        return model;
    }
}
