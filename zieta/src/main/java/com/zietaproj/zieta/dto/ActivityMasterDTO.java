package com.zietaproj.zieta.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityMasterDTO {

	private Long id;
	private Long client_id;
	private Long project_id;
	private String activity_code;
	private String activity_desc;
	private String created_by;
	private String modified_by;
	private boolean IS_DELETE;
	private String client_code;
	private String project_code;
	
	}
