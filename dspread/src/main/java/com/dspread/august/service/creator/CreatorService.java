/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.dspread.august.service.creator;
import com.dspread.august.entity.CreatorEntity;
import com.dspread.august.model.UserModel;
import com.dspread.august.repository.CreatorRepository;
import com.dspread.august.security.MyUserDetails;
import com.dspread.august.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class CreatorService {

    @Autowired
    CreatorRepository creatorRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private RedisTemplate redisTemplate;

    public UserModel login(String openid, String unionid){

        UserModel model = getUserByCreatorOpenId(openid);
        if(model !=null && unionid.equalsIgnoreCase(model.getUnionId())){
            return model;
        }else{
            return null;
        }
    }

    public void addCreator(UserModel user){
        CreatorEntity entity = creatorRepository.findByUnionId(user.getUnionId());
        //不存在用户且unionid不为空
        if(entity == null && !StringUtils.isEmpty(user.getUnionId())){
            user.setCreateTime(new Date());
            creatorRepository.save(CreatorEntity.fromModel(user));
        }//如果值享端已存在，且创客端没注册，则添加creatorOpenid
        else if(entity !=null && StringUtils.isEmpty(user.getOpenId())){
            entity.setOpenId(user.getOpenId());
            creatorRepository.save(entity);
        }
    }

    public UserModel updateCreator(UserModel user){
        return new UserModel();
    }

    //当前用户信息
    public UserModel info(){
        MyUserDetails user = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel model = user.getUser();
        return model;
    }
    //用户名获取用户
    public UserModel getUserByPhone(String phone){
        CreatorEntity entity = creatorRepository.findByPhone(phone);
        if(entity != null ){
            UserModel model = UserModel.fromEntity(entity);
            return model;
        }else{
            return null;
        }
    }
    //用户名获取用户
    public UserModel getUserByCreatorOpenId(String openid){
        CreatorEntity entity = creatorRepository.findByOpenId(openid);
        if(entity != null ){
            UserModel model = UserModel.fromEntity(entity);
            return model;
        }else{
            return null;
        }
    }
    //用户名获取用户
    public UserModel getUserByUnionId(String unionid){
        CreatorEntity entity = creatorRepository.findByUnionId(unionid);
        if(entity != null ){
            UserModel model = UserModel.fromEntity(entity);
            return model;
        }else{
            return null;
        }
    }
}
