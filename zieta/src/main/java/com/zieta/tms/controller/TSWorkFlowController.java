package com.zieta.tms.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.dto.TSWorkFlowRequestDTO;
import com.zieta.tms.dto.WorkflowDTO;
import com.zieta.tms.request.WorkflowRequestProcessModel;
import com.zieta.tms.response.WFRDetailsForApprover;
import com.zieta.tms.response.WorkFlowHistoryModel;
import com.zieta.tms.response.WorkFlowRequestorData;
import com.zieta.tms.service.TimeSheetService;
import com.zieta.tms.service.WorkFlowRequestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Api(tags = "TS WorkFlow API")
@Slf4j
public class TSWorkFlowController {
	

	@Autowired
	TimeSheetService timesheetService;
	
	@Autowired
	WorkFlowRequestService workFlowRequestService;

	@RequestMapping(value = "getAllWorkflowDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<WorkflowDTO> getAllWorkflowDetails() {
		List<WorkflowDTO> workflowDetails = null;
		try {
			workflowDetails = timesheetService.getAllWorkflowDetails();
		} catch (Exception e) {
			log.error("Error Occured in TSWorkFlowController#getAllWorkflowDetails", e);
		}
		return workflowDetails;

	}
	
	@RequestMapping(value = "getActiveWorkFlowRequestsByApprover", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<WFRDetailsForApprover> getActiveWorkFlowRequestsByApprover(@RequestParam(required = true) Long approverId) {
		List<WFRDetailsForApprover> workFlowRequestList = null;
		try {
			workFlowRequestList = workFlowRequestService.findActiveWorkFlowRequestsByApproverId(approverId);
		} catch (Exception e) {

			log.error("Error Occured in TSWorkFlowController#getActiveWorkFlowRequestsByApprover", e);
		}
		return workFlowRequestList;

	}

	@RequestMapping(value = "getWorkFlowRequestsByApprover", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<WFRDetailsForApprover> getWorkFlowRequestsByApprover(@RequestParam(required = true) Long approverId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startActiondate, 
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endActionDate) {
		List<WFRDetailsForApprover> workFlowRequestList = null;
		try {
			workFlowRequestList = workFlowRequestService.findWorkFlowRequestsByApproverId(approverId, startActiondate, endActionDate);
		} catch (Exception e) {

			log.error("Error Occured in TSWorkFlowController#getWorkFlowRequestsByApprover", e);
		}
		return workFlowRequestList;

	}
	
	@ApiOperation(value = "Instead getTimeEntriesByUserDates API is used for the retrieval of Information based on DateRange")
	@RequestMapping(value = "getWorkFlowRequestsByRequestor", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<WorkFlowRequestorData> getWorkFlowRequestsByRequestor(@RequestParam(required = true) Long requestorId)  {
		List<WorkFlowRequestorData> workFlowRequestList = null;
		try {
			workFlowRequestList = workFlowRequestService.findByRequestorId(requestorId);
		} catch (Exception e) {
			log.error("Error Occured in TSWorkFlowController#getWorkFlowRequestsByRequestor", e);

		}
		return workFlowRequestList;

	}
	
	@ApiOperation(value = "Updates the timeentries during firststep and process workflow for each step", notes = "Table reference:ts_timeentries,wf_request")
	@RequestMapping(value = "processWorkFlow", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void processWorkFlow(@Valid @RequestBody TSWorkFlowRequestDTO tSWorkFlowRequestDTO) throws Exception {
		workFlowRequestService.processTSWorkFlow(tSWorkFlowRequestDTO);
		
	}
	
	@RequestMapping(value = "getWFRHistoryForTS", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<WorkFlowHistoryModel> workFlowHistoryModelList(@RequestParam(required = true) Long tsId){
		List<WorkFlowHistoryModel> workFlowHistoryModelList = null;
		try {
			workFlowHistoryModelList = workFlowRequestService.getWorkFlowHistoryForTS(tsId);
		}catch (Exception e) {
			log.error("Error Occured in TSWorkFlowController#getWFRHistoryForTS", e);

		}
		return workFlowHistoryModelList;
		
	}
	
	
}
