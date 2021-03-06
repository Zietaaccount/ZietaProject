package com.zieta.tms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Immutable
@Table(name = "v_get_all_timesheets")
@Subselect("select uuid() as id, ts.* from v_get_all_timesheets ts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeSheetReport {

	@Id
	private String id;

	@Column(name = "userid")
	private Long userId;
	
	@Column(name = "client_id")
	private Long clientId;

	@Column(name = "ts_id")
	private Long tsId;
	
	@Column(name = "ts_date")
	private Date tsDate;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "emp_id")
	private String empId;
	
	@Column(name = "project_id")
	private Long projectId;
	
	@Column(name = "project_name")
	private String projectName;

	@Column(name = "task_name")
	private String taskName;

	@Column(name = "request_date")
	private Date requestDate;

	@Column(name = "action_date")
	private Date actionDate;

	@Column(name = "state_name")
	private String stateName;

	@Column(name = "action_name")
	private String actionName;
	
	@Column(name = "Submitted_hours")
	private Float submittedHours;
	
	@Column(name = "Approved_hours")
	private Float approvedHours;

	@Column(name = "approver_id")
	private Long approverId;
	
	@Column(name = "approver_name")
	private String approverName;

	@Column(name = "comments")
	private String comments;

}
