package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zietaproj.zieta.model.ActivitiesByTask;
import com.zietaproj.zieta.repository.ActivitiesByTaskRepo;
import com.zietaproj.zieta.response.ActivitiesByTaskResponse;
import com.zietaproj.zieta.service.ActivitiesByTaskService;

@Service
@Transactional
public class ActivitiesByTaskServiceImpl implements ActivitiesByTaskService {

	@Autowired
	ActivitiesByTaskRepo activitiesbytaskRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<ActivitiesByTaskResponse> getAllActivitiesByTask(Long task_id) {

		List<ActivitiesByTask> activitiesbytaskList = activitiesbytaskRepo.findByTaskId(task_id);
		List<ActivitiesByTaskResponse> activitiesbytaskResponseList = new ArrayList<ActivitiesByTaskResponse>();
		ActivitiesByTaskResponse activitiesbytaskResponse = null;
		for(ActivitiesByTask activitiesbytask : activitiesbytaskList) {
			activitiesbytaskResponse = modelMapper.map(activitiesbytask, 
					ActivitiesByTaskResponse.class);
			activitiesbytaskResponseList.add(activitiesbytaskResponse);
		}

		return activitiesbytaskResponseList;
	}
}
