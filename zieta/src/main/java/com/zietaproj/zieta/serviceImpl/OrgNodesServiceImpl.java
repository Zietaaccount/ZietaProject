package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.OrgInfo;
import com.zietaproj.zieta.model.OrgUnitTypeMaster;
import com.zietaproj.zieta.repository.OrgInfoRepository;
import com.zietaproj.zieta.repository.OrgUnitTypeRepository;
import com.zietaproj.zieta.response.ActivitiesByClientResponse;
import com.zietaproj.zieta.response.OrgNodesByClientResponse;
import com.zietaproj.zieta.service.OrgNodesService;

import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class OrgNodesServiceImpl implements OrgNodesService {

	@Autowired
	OrgInfoRepository orgInfoRepository;
	
	@Autowired
	OrgUnitTypeRepository orgunitTypeRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public List<OrgNodesByClientResponse> getOrgNodesByClient(Long clientId) {

		List<OrgInfo> orgnodesByClientList = orgInfoRepository.findByClientId(clientId);
		List<OrgNodesByClientResponse> orgnodesByClientResponseList = new ArrayList<>();
		OrgNodesByClientResponse orgnodesByClientResponse = null;
		for (OrgInfo orgnodesByClient : orgnodesByClientList) {
			orgnodesByClientResponse = modelMapper.map(orgnodesByClient, 
					OrgNodesByClientResponse.class);
			orgnodesByClientResponse.setOrgUnitTypeDescription(orgunitTypeRepository.findById(orgnodesByClient.getOrgType()).get().getTypeName());
			orgnodesByClientResponseList.add(orgnodesByClientResponse);
		}

		return orgnodesByClientResponseList;
		
	
	}
}
