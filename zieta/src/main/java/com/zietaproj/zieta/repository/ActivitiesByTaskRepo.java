package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zietaproj.zieta.model.ActivitiesByTask;

public interface ActivitiesByTaskRepo extends JpaRepository<ActivitiesByTask, Long> {

	List<ActivitiesByTask> findByTaskId(long taskId);
}
