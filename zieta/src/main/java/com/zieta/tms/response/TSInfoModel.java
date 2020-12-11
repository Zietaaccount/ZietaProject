package com.zieta.tms.response;

import java.util.List;

import com.zieta.tms.model.TSInfo;
import com.zieta.tms.model.TSTimeEntries;
import com.zieta.tms.model.TimeType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TSInfoModel {
	
	//String projectCode;
	//String taskCode;
	String taskDescription;
	//String activityCode;
	String activityDescription;
	String projectDescription;
	
	TSInfo tsInfo;
	List<TSTimeEntries> timeEntries;
	
	String timeTypeDesc;
	
	String clientCode;

	String clientDescription;
	
		
	

}
