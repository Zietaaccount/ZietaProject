package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.ErrorMaster;


@Repository
public interface ErrorMasterRepository extends JpaRepository<ErrorMaster, Long> {
	
	ErrorMaster findByErrorCode(String errorCode);
	long deleteByErrorCode(String errorCode);
	
	
	

}