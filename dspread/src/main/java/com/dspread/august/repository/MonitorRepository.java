package com.dspread.august.repository;

import com.dspread.august.entity.MonitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorRepository extends JpaRepository<MonitorEntity, Long>, JpaSpecificationExecutor<MonitorEntity> {

}
