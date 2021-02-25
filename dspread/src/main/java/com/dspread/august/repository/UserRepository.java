package com.dspread.august.repository;

import com.dspread.august.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

        UserEntity findByUserId(long userId);
        UserEntity findByUnionId(String unionId);
        UserEntity findByOpenId(String openId);
        UserEntity findByPhone(String phone);

    }



