package com.dspread.august.model;

import com.dspread.august.entity.MerchantEntity;
import com.dspread.august.security.merchant.base.JwtUserModel;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import java.util.Date;

public class MerchantModel extends JwtUserModel {

    private String merchantId;
    //公司
    private String company;
    //电话
    private String phone;

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public MerchantModel() {
        super();
    }

    public MerchantModel(String username, String password) {
        super(username, password);
    }


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static MerchantEntity toEntity(MerchantModel model) {
        MerchantEntity entity = new MerchantEntity();
        BeanUtils.copyProperties(model, entity);
        return entity;
    }

    public static MerchantModel fromEntity(MerchantEntity entity) {
        if (entity == null) {
            return null;
        }
        MerchantModel model = new MerchantModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
