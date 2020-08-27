package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;

import com.zietaproj.zieta.dto.StateTypeDTO;
import com.zietaproj.zieta.model.StateTypeMaster;

public interface StateTypeService {

	public List<StateTypeDTO> getAllStateTypes();

	public void addStateTypes(@Valid StateTypeMaster stateTypemaster);

	public void editStateTypesById(@Valid StateTypeDTO statetypeDTO) throws Exception;

	public void deleteStateTypesById(Long id) throws Exception;

	public StateTypeMaster getStateByName(String stateName);
	
}
