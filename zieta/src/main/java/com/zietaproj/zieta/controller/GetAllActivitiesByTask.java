package com.zietaproj.zieta.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zietaproj.zieta.response.ActivitiesByTaskResponse;
import com.zietaproj.zieta.service.ActivitiesByTaskService;

@RestController
@RequestMapping("/api")
public class GetAllActivitiesByTask {

	@Autowired
	ActivitiesByTaskService activitiesbytaskservice;
	
	//get all activities by task
		@GetMapping("/getAllActivitiesByTask/{task_id}")
		 public ResponseEntity<List<ActivitiesByTaskResponse>> getAllActivitiesByTask(@PathVariable Long task_id) {
			try {
		        List<ActivitiesByTaskResponse> activitiesbytaskList = activitiesbytaskservice.getAllActivitiesByTask(task_id);
		        return new ResponseEntity<List<ActivitiesByTaskResponse>>(activitiesbytaskList, HttpStatus.OK);
		     } catch (NoSuchElementException e) {
		         return new ResponseEntity<List<ActivitiesByTaskResponse>>(HttpStatus.NOT_FOUND);
		     }        
}

}