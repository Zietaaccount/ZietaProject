package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.common.ActionType;
import com.zietaproj.zieta.common.StateType;
import com.zietaproj.zieta.common.Status;
import com.zietaproj.zieta.dto.WorkflowDTO;
import com.zietaproj.zieta.model.ActivityMaster;
import com.zietaproj.zieta.model.ProcessSteps;
import com.zietaproj.zieta.model.StatusMaster;
import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.model.TSTimeEntries;
import com.zietaproj.zieta.model.TSWorkflow;
import com.zietaproj.zieta.model.TaskInfo;
import com.zietaproj.zieta.model.WorkflowRequest;
import com.zietaproj.zieta.repository.ActivityMasterRepository;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.ProcessStepsRepository;
import com.zietaproj.zieta.repository.ProjectInfoRepository;
import com.zietaproj.zieta.repository.StatusMasterRepository;
import com.zietaproj.zieta.repository.TSInfoRepository;
import com.zietaproj.zieta.repository.TSTimeEntriesRepository;
import com.zietaproj.zieta.repository.TaskInfoRepository;
import com.zietaproj.zieta.repository.WorkflowRepository;
import com.zietaproj.zieta.repository.WorkflowRequestRepository;
import com.zietaproj.zieta.request.TimeEntriesByTsIdRequest;
import com.zietaproj.zieta.request.UpdateTimesheetByIdRequest;
import com.zietaproj.zieta.response.TSInfoModel;
import com.zietaproj.zieta.response.TimeEntriesByTimesheetIDResponse;
import com.zietaproj.zieta.service.TimeSheetService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TimeSheetServiceImpl implements TimeSheetService {
	
	@Autowired
	TSInfoRepository tSInfoRepository;
	
	@Autowired
	ActivityMasterRepository  activityMasterRepository;
	
	@Autowired
	TaskInfoRepository taskInfoRepository;
	
	@Autowired
	ProjectInfoRepository  projectInfoRepository;
	
	@Autowired
	TSTimeEntriesRepository tstimeentriesRepository;
	
	@Autowired
	ClientInfoRepository  clientInfoRepository;
	
	@Autowired
	WorkflowRepository workflowRepository;
	
	@Autowired
	WorkflowRequestRepository workflowRequestRepository;
	
	@Autowired
	ProcessStepsRepository processStepsRepository;
	
	@Autowired
	StatusMasterRepository statusMasterRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	
	private final static String TIMESHEET = "TimeSheet";

	/**
	 *  This methods returns ts_info entries based on the date range of ts_date column 
	 *  and filters based on the userid and clientid
	 */
	@Override
	public List<TSInfoModel> getTimeEntriesByUserDates(Long clientId, Long userId, Date startDate, Date endDate) {
		short notDeleted=0;
		List<TSInfoModel> tsInfoModelList = new ArrayList<TSInfoModel>();
		List<TSInfo> tsInfoList = tSInfoRepository.findByClientIdAndUserIdAndIsDeleteAndTsDateBetweenOrderByTaskActivityIdAscIdAsc(clientId, userId, notDeleted, startDate, endDate);
		for (TSInfo tsInfo : tsInfoList) {
			TSInfoModel taskInfoModel = new TSInfoModel();
			taskInfoModel.setTsInfo(tsInfo);
			if(tsInfo.getActivityId() != null && tsInfo.getActivityId() !=0) {
				ActivityMaster activityEntity = activityMasterRepository.findById(tsInfo.getActivityId()).get();
				taskInfoModel.setActivityCode(activityEntity.getActivityCode());
				taskInfoModel.setActivityDescription(activityEntity.getActivityDesc());
			}else {
				taskInfoModel.setActivityCode(null);
				taskInfoModel.setActivityDescription(StringUtils.EMPTY);
			}
			
			if(tsInfo.getProjectId() !=null && tsInfo.getProjectId() !=0) {
				taskInfoModel.setProjectCode(projectInfoRepository.findById(tsInfo.getProjectId()).get().getProjectCode());
			}else {
				taskInfoModel.setProjectCode(null);
			}
			
			if(tsInfo.getTaskId() !=null && tsInfo.getTaskId() !=0) {
				TaskInfo taskInfoEntity =taskInfoRepository.findById(tsInfo.getTaskId()).get();
				taskInfoModel.setTaskCode(taskInfoEntity.getTaskCode());
				taskInfoModel.setTaskDescription(taskInfoEntity.getTaskDescription());
			}else {
				taskInfoModel.setTaskCode(null);
				taskInfoModel.setTaskDescription(StringUtils.EMPTY);
			}
			
			taskInfoModel.setClientCode(clientInfoRepository.findById(tsInfo.getClientId()).get().getClientCode());
			taskInfoModel.setClientDescription(clientInfoRepository.findById(tsInfo.getClientId()).get().getClientName());
			
			tsInfoModelList.add(taskInfoModel);
			
		}

		return tsInfoModelList;
	}
	
	
	@Transactional
	public List<TSInfo> addTimeSheet(@Valid List<TSInfo> tsInfoList) {
		
		 List<TSInfo> tsinfoEntites = tSInfoRepository.saveAll(tsInfoList);
		 
//		 submitTimeSheet(tsinfoEntites);
		 
		 return tsinfoEntites;
	}
	
	
	@Transactional
	public boolean submitTimeSheet(@Valid List<TSInfo> tsInfoList) {
		// call to save workflow_request
		try {
			List<WorkflowRequest> workflowRequestList = new ArrayList<WorkflowRequest>();
			Map<String, Long> statusTypes = null;
			boolean statusTypesLoad = Boolean.FALSE;
			WorkflowRequest workflowRequest = null;
			for (TSInfo tsInfo : tsInfoList) {
				
				//get the approverid from the process_step based on the clientId, projectId and taskId
				List<ProcessSteps> processStepsList = processStepsRepository.findByClientIdAndProjectIdAndProjectTaskId(
						tsInfo.getClientId(),tsInfo.getProjectId(), tsInfo.getTaskId());
				List<ProcessSteps> processStepFiltered = processStepsList.stream().filter(
						step -> step.getStepId().equals(StateType.INITIAL.getStateType())).collect(Collectors.toList());
				String approverIds[] = null;
				if(processStepFiltered.get(0).getApproverId() != null && !processStepFiltered.get(0).getApproverId().isEmpty()){
					
					approverIds = processStepFiltered.get(0).getApproverId().split("\\|");
				}
				
				if(!statusTypesLoad) {
					statusTypes = getStatus(tsInfo);
					//loaded once, no need to retrieve in all iterations;
					statusTypesLoad  = Boolean.TRUE; 
					
				}
				
				if(approverIds == null) {

					// we are in the condition where there are NO approvals
					workflowRequest = new WorkflowRequest();
					tsInfo.setStatusId(statusTypes.get(Status.APPROVED.getStatus()));
					tSInfoRepository.save(tsInfo);
					workflowRequest.setComments("No Approval required");
					workflowRequest.setApproverId(null);
					workflowRequest.setActionType(ActionType.APPROVE.getActionType());
					workflowRequest.setStateType(StateType.COMPLETE.getStateType()); 
					
					workflowRequest.setClientId(tsInfo.getClientId());
					workflowRequest.setActionDate(new Date());
					workflowRequest.setCurrentStep(1L);
					workflowRequest.setProjectId(tsInfo.getProjectId());
					workflowRequest.setProjectTaskId(tsInfo.getTaskId());
					workflowRequest.setRequestorId(tsInfo.getUserId());
					workflowRequest.setRequestDate(new Date());
					workflowRequest
							.setTemplateId(projectInfoRepository.findById(tsInfo.getProjectId()).get().getTemplateId());
					workflowRequest.setTsId(tsInfo.getId());
					workflowRequestList.add(workflowRequest);
					
				
					
				}else {
					for (String approverId : approverIds) {
						
						workflowRequest = new WorkflowRequest();

						tsInfo.setStatusId(statusTypes.get(Status.SUBMITTED.getStatus()));
						workflowRequest.setActionType(ActionType.INITIAL.getActionType());
						workflowRequest.setApproverId(Long.valueOf(approverId));
						workflowRequest.setComments("Submitted for Approval");
						workflowRequest.setStateType(StateType.START.getStateType());

						workflowRequest.setClientId(tsInfo.getClientId());
						workflowRequest.setActionDate(new Date());
						workflowRequest.setCurrentStep(1L);
						workflowRequest.setProjectId(tsInfo.getProjectId());
						workflowRequest.setProjectTaskId(tsInfo.getTaskId());
						workflowRequest.setRequestorId(tsInfo.getUserId());
						workflowRequest.setRequestDate(new Date());
						workflowRequest
								.setTemplateId(projectInfoRepository.findById(tsInfo.getProjectId()).get().getTemplateId());
						workflowRequest.setTsId(tsInfo.getId());
						workflowRequestList.add(workflowRequest);
						
					}
				}
			
				
			}
			
			workflowRequestRepository.saveAll(workflowRequestList);
		} catch (Exception e) {
			log.error("Exception occured while populating workflow request", e);
			return false;
		}
		return true;
	}


	private Map<String, Long> getStatus(TSInfo tsInfo) {
		Map<String, Long> statusTypes;
		short notDeleted = 0;
		List<StatusMaster>  statusMasterList = statusMasterRepository.findByClientIdAndStatusTypeAndIsDelete(
				tsInfo.getClientId(), TIMESHEET, notDeleted);
		statusTypes = statusMasterList.stream().collect(Collectors.toMap(StatusMaster::getStatus, StatusMaster::getId));
		return statusTypes;
	}
	
	
	@Transactional
	public void processWorkFlow(Long workFlowRequestId, short actionType, String comments) {
		
		WorkflowRequest workFlowRequest = workflowRequestRepository.findById(workFlowRequestId).get();
		
		List<ProcessSteps> processStepsList = processStepsRepository.findByClientIdAndProjectIdAndProjectTaskId(
				workFlowRequest.getClientId(),workFlowRequest.getProjectId(), workFlowRequest.getProjectTaskId());
		int workFlowDepth = processStepsList.size();
		
		TSInfo tsInfo = tSInfoRepository.findById(workFlowRequest.getTsId()).get();
		Map<String, Long> statusTypes =  getStatus(tsInfo);
		if(actionType == ActionType.APPROVE.getActionType()) {
			//promote the approval to next level
			long currentStep = workFlowRequest.getCurrentStep();
			if( currentStep <= workFlowDepth) {
				if(currentStep != workFlowDepth) {
					long nextStep = currentStep + 1;
					List<ProcessSteps> processStepFiltered = processStepsList.stream().filter(step -> step.getStepId().equals(nextStep)).collect(Collectors.toList());
					workFlowRequest.setCurrentStep(nextStep);
					//Promoting to next level approval
					workFlowRequest.setComments(comments);
					workFlowRequest.setApproverId(Long.valueOf(processStepFiltered.get(0).getApproverId()));
					workFlowRequest.setStateType(StateType.INPROCESS.getStateType());
					// approved from the current step point of view
					workFlowRequest.setActionType(ActionType.APPROVE.getActionType());
					workFlowRequest.setActionDate(new Date());
				}else {
					//Final Approval Done
					workFlowRequest.setComments(comments);
					tsInfo.setStatusId(statusTypes.get(Status.APPROVED.getStatus()));
					tSInfoRepository.save(tsInfo);
					workFlowRequest.setStateType(StateType.COMPLETE.getStateType());
					workFlowRequest.setActionType(ActionType.APPROVE.getActionType());
					workFlowRequest.setActionDate(new Date());
				}
				
				
			}
		}else if(actionType == ActionType.REJECT.getActionType()) {
			//Request Rejected
			workFlowRequest.setComments(comments);
			workFlowRequest.setStateType(StateType.REJECT.getStateType());
			workFlowRequest.setActionType(ActionType.REJECT.getActionType());
			tsInfo.setStatusId(statusTypes.get(Status.REJECTED.getStatus()));
			tSInfoRepository.saveAndFlush(tsInfo);
			workFlowRequest.setActionDate(new Date());
		}else if(actionType == ActionType.REVISE.getActionType()) {
			//Request sent for revise
			workFlowRequest.setComments(comments);
			workFlowRequest.setActionDate(new Date());
			workFlowRequest.setStateType(StateType.INITIAL.getStateType());
			workFlowRequest.setActionType(ActionType.REVISE.getActionType());
			tsInfo.setStatusId((statusTypes.get(Status.DRAFT.getStatus())));
			tSInfoRepository.save(tsInfo);
			//Do we need to create the new Request or if its with the same request , need to start with Initial Step ?
		}
		
	}
	
	@Override
	public List<TimeEntriesByTimesheetIDResponse> getTimeEntriesByTsID(Long tsId) {
		short notDeleted=0;
		List<TSTimeEntries> timeentriesByTsidList = tstimeentriesRepository.findByTsIdAndIsDelete(tsId, notDeleted);
		List<TimeEntriesByTimesheetIDResponse> timeentriesBytsIdResponseList = new ArrayList<>();
		TimeEntriesByTimesheetIDResponse timeentriesByTsIdResponse = null;
		for (TSTimeEntries timeentriesByTsId : timeentriesByTsidList) {
			timeentriesByTsIdResponse = modelMapper.map(timeentriesByTsId, 
					TimeEntriesByTimesheetIDResponse.class);
			timeentriesBytsIdResponseList.add(timeentriesByTsIdResponse);
		}

		return timeentriesBytsIdResponseList;
		
		
	}
	
	@Override
	@Transactional
	public void addTimeEntriesByTsId(@Valid List<TimeEntriesByTsIdRequest> timeentriesbytsidRequestList) throws Exception {
			for (TimeEntriesByTsIdRequest timeEntriesByTsIdRequest : timeentriesbytsidRequestList) {
				TSTimeEntries tstimeentries = modelMapper.map(timeEntriesByTsIdRequest, TSTimeEntries.class);
				tstimeentriesRepository.save(tstimeentries);
			}

	}
	
	@Override
	public void updateTimeSheetById(@Valid UpdateTimesheetByIdRequest updatetimesheetRequest) throws Exception {
		//for (UpdateTimesheetByIdRequest updateRequest : updatetimesheetRequest) {
		Optional<TSInfo> TsInfoEntity = tSInfoRepository.findById(updatetimesheetRequest.getId());
		if(TsInfoEntity.isPresent()) {
			TSInfo tsInfoSave = TsInfoEntity.get();
			TSInfo tsinfo = modelMapper.map(updatetimesheetRequest, TSInfo.class);
			
			tSInfoRepository.save(tsinfo);
		}
	
		else {
			throw new Exception("Timesheet not found with the provided ID : "+updatetimesheetRequest.getId());
		}
	}
	
	
	@Override
	@Transactional
	public void updateTimeSheetByIds(@Valid List<UpdateTimesheetByIdRequest> updatetimesheetRequest) throws Exception {
		
		for (UpdateTimesheetByIdRequest updateRequest : updatetimesheetRequest) {
			updateTimeSheetById(updateRequest);
		}
	}
	
	
	
	public List<WorkflowDTO> getAllWorkflowDetails() {
		
		List<TSWorkflow> workflows = workflowRepository.findAll();
		List<WorkflowDTO> workflowDTOs = new ArrayList<WorkflowDTO>();
		WorkflowDTO workflowDTO = null;
		for (TSWorkflow workflow : workflows) {
			workflowDTO = modelMapper.map(workflow, WorkflowDTO.class);
			
			workflowDTOs.add(workflowDTO);
		}
		return workflowDTOs;
		
		
	}
	
	
	@Override
	public void updateTimeEntriesByID(@Valid TimeEntriesByTsIdRequest timeentriesByTsIdRequest) throws Exception {
		//for (UpdateTimesheetByIdRequest updateRequest : updatetimesheetRequest) {
		Optional<TSTimeEntries> TsTimeEntity = tstimeentriesRepository.findById(timeentriesByTsIdRequest.getId());
		if(TsTimeEntity.isPresent()) {
			TSTimeEntries tsTimeSave = TsTimeEntity.get();
			TSTimeEntries tstimeentry= modelMapper.map(timeentriesByTsIdRequest, TSTimeEntries.class);
			
			tstimeentriesRepository.save(tstimeentry);
		}
	
		else {
			throw new Exception("Timeentry not found with the provided ID : "+timeentriesByTsIdRequest.getId());
		}
	}
	
	
	@Override
	@Transactional
	public void updateTimeEntriesByIds(@Valid List<TimeEntriesByTsIdRequest> timeentriesByTsIdRequest) throws Exception {
		
		for (TimeEntriesByTsIdRequest updateRequest : timeentriesByTsIdRequest) {
			updateTimeEntriesByID(updateRequest);
		}
	}
	
	
	
	
	
	
	
}
