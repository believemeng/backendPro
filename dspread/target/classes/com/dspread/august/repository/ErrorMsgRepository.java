package com.dspread.august.repository;

import com.dspread.august.entity.ErrorMsgEntity;
import com.dspread.august.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ErrorMsgRepository extends JpaRepository<ErrorMsgEntity, Long>, JpaSpecificationExecutor<ErrorMsgEntity> {

}