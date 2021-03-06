package com.zieta.tms.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Table(name = "task_info")
@Data
@EqualsAndHashCode(callSuper=true)
public class TaskInfo extends BaseEntity implements Serializable {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long taskInfoId;

	    @Column(name = "client_id")
	    private Long clientId;
	
	    @NotNull
	    @Column(name = "project_id")
	    private Long projectId;
	    
	    @Column(name = "task_name")
	    private String taskDescription;
	    
	  //  @NotBlank
	//    @Column(name ="task_code")
	 //   private String taskCode;
	    
	    @Column(name = "task_type")
	    private Long taskType;
	    
	    @Column(name = "task_parent")
	    private Long taskParent;
	    
	    @Column(name = "task_manager")
	    private Long taskManager;
	    
	    @Column(name = "task_status")
	    private Long taskStatus;
	    
	   
	    @Column(nullable = true, name = "task_start_date")
	    private Date taskStartDate;
	    
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(nullable = true, name = "task_end_date")
	    private Date taskendDate;
	    
	    @Column(name = "sortkey")
	    private Long sortKey;
	
}
