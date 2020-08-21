package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.common.StateType;
import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.model.TSTimeEntries;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.model.WorkflowRequest;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.ProjectInfoRepository;
import com.zietaproj.zieta.repository.StateTypeMasterRepository;
import com.zietaproj.zieta.repository.TSInfoRepository;
import com.zietaproj.zieta.repository.TSTimeEntriesRepository;
import com.zietaproj.zieta.repository.UserInfoRepository;
import com.zietaproj.zieta.repository.WorkflowRequestRepository;
import com.zietaproj.zieta.response.WFRDetailsForApprover;
import com.zietaproj.zieta.response.WorkFlowRequestorData;
import com.zietaproj.zieta.service.WorkFlowRequestService;
import com.zietaproj.zieta.util.TSMUtil;


@Service
public class WorkFlowRequestServiceImpl implements WorkFlowRequestService {

	@Autowired
	WorkflowRequestRepository workflowRequestRepository;

	
	@Autowired
	TSInfoRepository tsInfoRepository;
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	ProjectInfoRepository projectInfoRepository;
	
	@Autowired
	TSTimeEntriesRepository tSTimeEntriesRepository;
	
	
	@Autowired
	StateTypeMasterRepository stateTypeMasterRepository;
	
	
	@Autowired
	ClientInfoRepository clientInfoRepository;

	@Override
	public List<WFRDetailsForApprover> findByApproverId(long approverId) {
		List<WorkflowRequest> workFlowRequestList = workflowRequestRepository.findByApproverId(approverId);
		List<WFRDetailsForApprover> wFRDetailsForApproverList = new ArrayList<WFRDetailsForApprover>();
		WFRDetailsForApprover wFRDetailsForApprover = null;
		for (WorkflowRequest workflowRequest : workFlowRequestList) {

			wFRDetailsForApprover = new WFRDetailsForApprover();
			wFRDetailsForApprover.setWorkflowRequest(workflowRequest);

			TSInfo tsInfo = tsInfoRepository.findById(workflowRequest.getTsId()).get();
			wFRDetailsForApprover.setTsinfo(tsInfo);

			List<TSTimeEntries> tsTimeEntriesList = tSTimeEntriesRepository.findByTsId(tsInfo.getId());

			wFRDetailsForApprover.setTimeEntriesList(tsTimeEntriesList);

			wFRDetailsForApprover
					.setProjectName(projectInfoRepository.findById(tsInfo.getProjectId()).get().getProjectName());
			wFRDetailsForApprover
					.setRequestorName(TSMUtil.getFullName(userInfoRepository.findById(tsInfo.getUserId()).get()));
			wFRDetailsForApprover
					.setClientName(clientInfoRepository.findById(workflowRequest.getClientId()).get().getClientName());

			wFRDetailsForApproverList.add(wFRDetailsForApprover);

		}

		return wFRDetailsForApproverList;
	}


	public List<WorkFlowRequestorData> findByRequestorId(long requestorId) {
		List<WorkflowRequest> workFlowRequestorItems = workflowRequestRepository.findByRequestorId(requestorId);
		List<WorkFlowRequestorData> workFlowRequestorDataList = new ArrayList<WorkFlowRequestorData>();
		WorkFlowRequestorData workFlowRequestorData = null;

		for (WorkflowRequest workflowRequest : workFlowRequestorItems) {
			workFlowRequestorData = new WorkFlowRequestorData();
			TSInfo tsInfo = tsInfoRepository.findById(workflowRequest.getTsId()).get();
			UserInfo userInfo = userInfoRepository.findById(tsInfo.getUserId()).get();
			String requestorName = TSMUtil.getFullName(userInfo);
			workFlowRequestorData.setRequestorName(requestorName);
			workFlowRequestorData.setTotalSubmittedTime(String.valueOf(tsInfo.getTsTotalSubmittedTime()));
			workFlowRequestorData.setTotalApprovedTime(String.valueOf(tsInfo.getTsTotalApprovedTime()));
			if (workflowRequest.getStateType() == StateType.COMPLETE.getStateTypeId()) {

				workFlowRequestorData.setApprovedDate(String.valueOf(workflowRequest.getActionDate()));
			}

			if (workflowRequest.getStateType() == StateType.REJECT.getStateTypeId()) {

				workFlowRequestorData.setRejectedDate(String.valueOf(workflowRequest.getActionDate()));
			}
			workFlowRequestorData.setSubmittedDate(workflowRequest.getRequestDate().toString());
			workFlowRequestorData
					.setStatus(stateTypeMasterRepository.findById(workflowRequest.getStateType()).get().getStateName());

			workFlowRequestorDataList.add(workFlowRequestorData);
		}
		return workFlowRequestorDataList;
	}


	@Override
	public WorkflowRequest findByTsIdAndApproverId(long tsId, long approverId) {
		return workflowRequestRepository.findByTsIdAndApproverId(tsId, approverId);
	}

}