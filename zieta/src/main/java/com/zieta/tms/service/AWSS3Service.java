package com.zieta.tms.service;

import org.springframework.web.multipart.MultipartFile;

import com.zieta.tms.request.S3AttributesModel;

public interface AWSS3Service {

	void uploadFile(MultipartFile multipartFile, String clietnId);
	
	byte[] downloadFile(String keyName);
}

