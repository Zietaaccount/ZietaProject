package com.zietaproj.zieta.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "TASK_ACTIVITY")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_date", "modified_date" }, allowGetters = true)
@Data
public class TaskActivity extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "client_id")
	@NotNull
	private Long clientId;

	@Column(name = "project_id")
	@NotNull
	private Long projectId;

	@NotNull
	@Column(name = "task_id")
	private Long taskId;

	@NotNull
	private Long activity_id;

	

}
