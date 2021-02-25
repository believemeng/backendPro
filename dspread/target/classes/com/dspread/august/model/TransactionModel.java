package com.dspread.august.model;

import com.dspread.august.entity.TransactionEntity;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class TransactionModel {

    private long id;
    private String orderNo;
    private String mchName;
    private String dataHash;
    private String holderData;
    private String pin;
    private String amount;
    private Date tranTime;
    private Date createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMchName() {
        return mchName;
    }

    public void setMchName(String mchName) {
        this.mchName = mchName;
    }

    public String getDataHash() {
        return dataHash;
    }

    public void setDataHash(String dataHash) {
        this.dataHash = dataHash;
    }

    public String getHolderData() {
        return holderData;
    }

    public void setHolderData(String holderData) {
        this.holderData = holderData;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static TransactionEntity toEntity(TransactionModel model){
        TransactionEntity entity = new TransactionEntity();
        BeanUtils.copyProperties(model,entity);
        return entity;
    }
}
