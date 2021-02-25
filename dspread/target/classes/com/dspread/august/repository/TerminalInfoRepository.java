package com.dspread.august.repository;

import com.dspread.august.entity.TerminalInfoEntity;
import com.dspread.august.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalInfoRepository extends JpaRepository<TerminalInfoEntity, Long>, JpaSpecificationExecutor<TerminalInfoEntity> {

    TerminalInfoEntity findByTerminalId(String terminalId);
}