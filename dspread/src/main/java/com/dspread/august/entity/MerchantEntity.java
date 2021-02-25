package com.dspread.august.entity;


import com.dspread.august.model.MerchantModel;
import com.dspread.august.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "merchant")
public class MerchantEntity {


    /*
     * 主键
     * */
    @Id
    @Column(name = "merchant_id", unique = true, nullable = false)
    private String merchantId;

    @Column(name = "merchant_name")
    private String username;

    @Column(name = "merchant_password")
    private String password;


    @Column(name = "merchant_company")
    private String company;

    @Column(name = "merchant_phone")
    private String phone;

    @Column(name = "create_time")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static MerchantEntity fromModel(MerchantModel model) {
        if (model == null) {
            return null;
        }
        MerchantEntity entity = new MerchantEntity();
        BeanUtils.copyProperties(model, entity);
        return entity;
    }
}
