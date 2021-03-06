package com.zieta.tms.controller;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.model.TSInfo;
import com.zieta.tms.request.TimeEntriesByTsIdRequest;
import com.zieta.tms.request.UpdateTaskInfoRequest;
import com.zieta.tms.request.UpdateTimesheetByIdRequest;
import com.zieta.tms.response.TSInfoModel;
import com.zieta.tms.response.TimeEntriesByTimesheetIDResponse;
import com.zieta.tms.service.TimeSheetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Api(tags = "TimeSheet API")
@Slf4j
public class TimeSheetController {

	@Autowired
	TimeSheetService timeSheetService;
	
	@RequestMapping(value = "addTimeSheet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TSInfo>> addTimeSheet(@Valid @RequestBody List<TSInfo> tsinfo) {
		try {
			List<TSInfo> tsinfoEntites = timeSheetService.addTimeSheet(tsinfo);
			
			return new ResponseEntity<List<TSInfo>>(tsinfoEntites, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<TSInfo>>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@ApiOperation(value = "Lists TimeSheet entries based on the ts_date range, client and user filters",notes="Table reference: ts_info, ts_timentries")
	@RequestMapping(value = "getTimeEntriesByUserDates", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TSInfoModel> getTimeEntriesByUserDates(@RequestParam(required = true) Long clientId, 
			@RequestParam(required = true) Long userId, 
			@RequestParam(required = true)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam(required = true)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
		List<TSInfoModel> tsInfoList = null;
		try {
			tsInfoList = timeSheetService.getTimeEntriesByUserDates(clientId, userId, startDate, endDate);
		} catch (Exception e) {
			log.error("Error Occured in getTimeEntriesByUserDates",e);
		}
		return tsInfoList;
	}
	
	
	@GetMapping("/getTimeEntriesByTimesheetID")
	public ResponseEntity<List<TimeEntriesByTimesheetIDResponse>> getTimeEntriesByTimesheetID(@RequestParam(required=true) Long tsId) {
		try {
			List<TimeEntriesByTimesheetIDResponse> tstimeentriesbyIdList = timeSheetService.getTimeEntriesByTsID(tsId);
			return new ResponseEntity<List<TimeEntriesByTimesheetIDResponse>>(tstimeentriesbyIdList, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<TimeEntriesByTimesheetIDResponse>>(HttpStatus.NOT_FOUND);
		} 
	}
	
	
	@RequestMapping(value = "addTimeEntriesByTimesheetID", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addTimeEntriesByTimesheetID(@Valid @RequestBody List<TimeEntriesByTsIdRequest> timeentriesbytsidRequest) throws Exception {
		timeSheetService.addTimeEntriesByTsId(timeentriesbytsidRequest);
		
	}
	
	
	@RequestMapping(value = "updateTimeSheetByID", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateTimeSheetByID(@Valid @RequestBody UpdateTimesheetByIdRequest updatetimesheetRequest) throws Exception {
		timeSheetService.updateTimeSheetById(updatetimesheetRequest);
		
	}
	
	@ApiOperation(value = "Updates Timesheet for the provided Ids", notes = "Table reference: ts_info")
	@RequestMapping(value = "updateTimesheetByIDs", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateTimeSheetByIDs( @RequestBody List<UpdateTimesheetByIdRequest> updatetimesheetRequest) throws Exception {
		timeSheetService.updateTimeSheetByIds(updatetimesheetRequest);
		
	}
	
	

	public void updateTimeEntriesByID(@Valid @RequestBody TimeEntriesByTsIdRequest timeentriesByTsIdRequest) throws Exception {
		timeSheetService.updateTimeEntriesByID(timeentriesByTsIdRequest);
		
	}
	
	@ApiOperation(value = "Updates TimeEntries for the provided Ids", notes = "Table reference: ts_timeentries")
	@RequestMapping(value = "updateTimeEntriesByIDs", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateTimeEntriesByIDs( @RequestBody List<TimeEntriesByTsIdRequest> timeentriesByTsIdRequest) throws Exception {
		timeSheetService.updateTimeEntriesByIds(timeentriesByTsIdRequest);
		
	}
	
	@ApiOperation(value = "Deletes entries from ts_timeentries based on Id", notes = "Table reference: ts_timeentries")
	@RequestMapping(value = "deleteTimeEntriesById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteTimeEntriesById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		timeSheetService.deleteTimeEntriesById(id, modifiedBy);
	}
	
	
	@RequestMapping(value = "submitTimeSheet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void submitTimeSheet(@Valid @RequestBody List<TSInfo> tsInfoList) throws Exception {
		timeSheetService.submitTimeSheet(tsInfoList);
		
	}
	
	
	@ApiOperation(value = "Deletes entries from ts_info based on Id", notes = "Table reference: ts_info")
	@RequestMapping(value = "deleteTsInfoById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteTsInfoById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		timeSheetService.deleteTsInfoById(id, modifiedBy);
	}
	
	
	
}

