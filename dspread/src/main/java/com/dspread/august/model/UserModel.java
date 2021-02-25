package com.dspread.august.model;

import com.dspread.august.entity.CreatorEntity;
import com.dspread.august.entity.UserEntity;
import com.dspread.august.security.MyUserDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class UserModel {


    private long userId;
    private String nickName;
    private String unionId;
    private String openId;

    private String avatarUrl;
    private String wechatNo;
    private int sex;
    private String phone;
    private Date createTime;

    private boolean isCreator = false;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isCreator() {
        return isCreator;
    }

    public void setCreator(boolean creator) {
        isCreator = creator;
    }

    public static UserModel fromEntity(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserModel model = new UserModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
    public static UserModel fromEntity(CreatorEntity entity) {
        if (entity == null) {
            return null;
        }
        UserModel model = new UserModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }

    public static long getCurrentUserId(){
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MyUserDetails) {
            MyUserDetails currentUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return currentUser.getCurrentUserId();
        }
        return  0;
    }

    public static UserModel getCurrentUser(){
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MyUserDetails) {
            MyUserDetails currentUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return currentUser.getUser();
        }else {
            return null;
        }
    }
}
