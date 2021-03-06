package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.SkillsetCategoryMasterDTO;
import com.zieta.tms.dto.SkillsetMasterDTO;
import com.zieta.tms.dto.SkillsetUserMappingDTO;
import com.zieta.tms.model.SkillsetCategoryMaster;
import com.zieta.tms.model.SkillsetMaster;
import com.zieta.tms.model.SkillsetUserMapping;
import com.zieta.tms.model.StatusMaster;

public interface SkillsetMasterService {



public void addSkillmaster(@Valid SkillsetMaster skillmaster);

public List<SkillsetMasterDTO> getAllSkillset();

public void deleteSkillsetById(Long id) throws Exception;

public List<SkillsetMasterDTO> findByClientId(Long clientId);

public void editskillmaster(@Valid SkillsetMasterDTO skilldto) throws Exception;

public List<SkillsetUserMappingDTO> getAllSkillsetUserMapping();

public void addSkillsetUserMapping(@Valid List<SkillsetUserMapping> skilluserMapping);

public void deleteSkillUserMappingById(Long id) throws Exception;

public List<SkillsetUserMappingDTO> findByClientIdAndUserId(Long clientId, Long userId);


public void editSkillUserMapping(@Valid List<SkillsetUserMappingDTO> skillusermappingDTO) throws Exception;

public List<SkillsetCategoryMasterDTO> getAllSkillsetCategoryMaster();

public List<SkillsetCategoryMasterDTO> findSkillCategoryByClientId(Long clientId);

public void addSkillsetCategory(@Valid List<SkillsetCategoryMaster> skillCategory);

public void editSkillsetCategoryById(@Valid SkillsetCategoryMasterDTO skillCategorydto) throws Exception;

public void deleteSkillsetCategoryById(Long id) throws Exception;

	
}
