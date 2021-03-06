package com.zieta.tms.dto;

import java.util.List;

import com.zieta.tms.request.TimeEntriesByTsIdRequest;
import com.zieta.tms.request.WorkflowRequestProcessModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TSWorkFlowRequestDTO {
	
	List<TimeEntriesByTsIdRequest> timeentriesByTsIdRequest;
	WorkflowRequestProcessModel workflowRequestProcessModel;
	

}
