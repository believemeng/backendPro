package com.dspread.august.repository;

import com.dspread.august.entity.BaseLineEntity;
import com.dspread.august.entity.MonitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseLineRepository extends JpaRepository<BaseLineEntity, Long>, JpaSpecificationExecutor<BaseLineEntity> {

    List<BaseLineEntity> findByCotsModelLike(String cotsModel);
}
