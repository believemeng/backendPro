package com.dspread.august.service;

import com.dspread.august.common.StringUtil;
import com.dspread.august.common.UUIDUtil;
import com.dspread.august.entity.MerchantEntity;

import com.dspread.august.model.MerchantModel;

import com.dspread.august.repository.MerchantRepository;

import com.dspread.august.security.merchant.base.JwtUserService;
import com.dspread.august.security.merchant.base.JwtUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class MerchantService extends JwtUserService {
    @Autowired
    MerchantRepository merchantRepository;


    public void addMerchant(MerchantModel merchantModel) {
        MerchantEntity entity = MerchantModel.toEntity(merchantModel);
        entity.setCreateTime(new Date());
        entity.setMerchantId(UUIDUtil.getUUID());

        if (StringUtils.isEmpty(entity.getPhone())){
            entity.setPhone("");
        }
        if (StringUtils.isEmpty(entity.getCompany())){
            entity.setCompany("");
        }
        merchantRepository.save(entity);
    }


    public MerchantModel regist(MerchantModel model) {
        String userName = model.getUsername();
        MerchantModel userByMerchantName = getUserByMerchantName(userName);
        if (userByMerchantName == null) {
            addMerchant(model);
            return model;
        } else {
            return null;
        }
    }

    public MerchantModel getUserByMerchantName(String name) {
        MerchantEntity entity = merchantRepository.findByUsername(name);
        if (entity != null) {
            MerchantModel model = MerchantModel.fromEntity(entity);
            return model;
        } else {
            return null;
        }
    }

    @Override
    public JwtUserModel getUserByName(String username) {
        return getUserByMerchantName(username);
    }

}
