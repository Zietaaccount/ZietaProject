package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zietaproj.zieta.model.ActivitiesByTask;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.repository.ActivitiesByTaskRepo;
import com.zietaproj.zieta.repository.ActivityMasterRepository;
import com.zietaproj.zieta.response.ActivitiesByTaskResponse;
import com.zietaproj.zieta.service.ActivitiesByTaskService;

@Service
@Transactional
public class ActivitiesByTaskServiceImpl implements ActivitiesByTaskService {

	@Autowired
	ActivitiesByTaskRepo activitiesbytaskRepo;
	@Autowired
	ActivityMasterRepository activityMasterRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
//	@SuppressWarnings("null")
	@Override
	public List<ActivitiesByTaskResponse> getActivitiesByTask(Long taskId) {

		List<ActivitiesByTask> activitiesbytaskList = activitiesbytaskRepo.findByTaskId(taskId);
//		List<ActivityMaster> activityMasters= activityMasterRepository.findAll();
		List<ActivitiesByTaskResponse> activitiesbytaskResponseList = new ArrayList<ActivitiesByTaskResponse>();
		ActivitiesByTaskResponse activitiesbytaskResponse = null;
		for(ActivitiesByTask activitiesbytask : activitiesbytaskList) {
//			activitiesbytaskResponse = modelMapper.map(activitiesbytask, 
//					ActivitiesByTaskResponse.class);
			activitiesbytaskResponse = new ActivitiesByTaskResponse();
			activitiesbytaskResponse.setId(activitiesbytask.getId());
			activitiesbytaskResponse.setClient_id(activitiesbytask.getClientId());
			activitiesbytaskResponse.setActivity_id(activitiesbytask.getActivity_id());
//			long Id = ;
//			long ClientId = ;
//			long projectId = activitiesbytask.getProjectId();
//			long activity_id = ;
			System.out.println(activitiesbytask.getActivity_id());
			Optional<ActivityMaster> activitymaster = activityMasterRepository.findById(activitiesbytask.getActivity_id());
			if(activitymaster.isPresent()) {
				activitiesbytaskResponse.setActivity_code(activitymaster.get().getActivity_code());
				activitiesbytaskResponse.setActivity_desc(activitymaster.get().getActivity_desc());
				
			}
			
			
			activitiesbytaskResponseList.add(activitiesbytaskResponse);
		}

		return activitiesbytaskResponseList;
	}
}
