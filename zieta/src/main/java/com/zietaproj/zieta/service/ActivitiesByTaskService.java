package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.response.ActivitiesByTaskResponse;

public interface ActivitiesByTaskService {

	public List<ActivitiesByTaskResponse> getAllActivitiesByTask(Long task_id);
}
