package com.dspread.august.service;

import com.dspread.august.entity.UserEntity;
import com.dspread.august.model.UserModel;
import com.dspread.august.repository.UserRepository;
import com.dspread.august.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private CommonService commonService;
    public UserModel updateUser(UserModel model){

        return new UserModel();
    }
    public void addUser(UserModel user) {

        UserEntity entity = userRepository.findByUnionId(user.getUnionId());
        //注册用户
        if(entity == null && !StringUtils.isEmpty(user.getUnionId())){
            user.setCreateTime(new Date());
            userRepository.save(UserEntity.fromModel(user));
        }
        //如果创客端已存在，且值享端没注册，则添加openid
        else if(entity != null && StringUtils.isEmpty(user.getOpenId())){
            entity.setOpenId(user.getOpenId());
            userRepository.save(entity);
        }
    }

    public UserModel login(String openid,String unionid){

        UserModel model = getUserByOpenId(openid);
        if(model !=null && unionid.equalsIgnoreCase(model.getUnionId())){
            return model;
        }else{
            return null;
        }
    }
    //当前用户信息
    public UserModel info(){
        MyUserDetails user = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel model = user.getUser();
        return model;
    }
    public UserModel getUserByOpenId(String openid){
        UserEntity entity = userRepository.findByOpenId(openid);
        if(entity != null ){
            UserModel model = UserModel.fromEntity(entity);
            return model;
        }else{
            return null;
        }
    }
    public UserModel getUserByUnionId(String unionid){
        UserEntity entity = userRepository.findByUnionId(unionid);
        if(entity != null ){
            UserModel model = UserModel.fromEntity(entity);
            return model;
        }else{
            return null;
        }
    }
}
