package com.dspread.august.model;

import com.dspread.august.entity.ErrorMsgEntity;
import com.dspread.august.entity.TransactionEntity;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class ErrorMsgModel {

    private long id;

    private int flag;

    private String terminalId;

    private String data;

    private String errorMsg;

    private Date createTime;

    public ErrorMsgModel(int flag , String terminalId, String data) {
        this.terminalId = terminalId;
        this.data = data;
        this.flag = flag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static ErrorMsgEntity toEntity(ErrorMsgModel model){
        ErrorMsgEntity entity = new ErrorMsgEntity();
        BeanUtils.copyProperties(model,entity);
        return entity;
    }
}
