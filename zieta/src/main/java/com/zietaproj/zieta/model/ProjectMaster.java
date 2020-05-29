package com.zietaproj.zieta.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "PROJECT_TYPE_MASTER")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created_date", "modified_date"}, 
        allowGetters = true)

public class ProjectMaster implements Serializable {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotNull
	    private Long client_id;
	
	    @NotBlank
	    private String client_code;

	    @NotBlank
	    private String project_type;
	    
	    @NotBlank
	    private String project_code;
	    
		@NotBlank
		private String created_by;

	    @Column(nullable = false, updatable = false)
	    @Temporal(TemporalType.TIMESTAMP)
	    @CreatedDate
	    private Date created_date;

	    @Column(nullable = false)
	    @Temporal(TemporalType.TIMESTAMP)
	    @LastModifiedDate
	    private Date modified_date;
		
		@NotBlank
		private String modified_by;
		
		private boolean IS_DELETE;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getClient_id() {
			return client_id;
		}

		public void setClient_id(Long client_id) {
			this.client_id = client_id;
		}

		public String getProject_type() {
			return project_type;
		}

		public void setProject_type(String project_type) {
			this.project_type = project_type;
		}

		public String getCreated_by() {
			return created_by;
		}

		public void setCreaed_by(String created_by) {
			this.created_by = created_by;
		}

		public Date getCreated_date() {
			return created_date;
		}

		public void setCreated_date(Date created_date) {
			this.created_date = created_date;
		}

		public Date getModified_date() {
			return modified_date;
		}

		public void setModified_date(Date modified_date) {
			this.modified_date = modified_date;
		}

		public String getModified_by() {
			return modified_by;
		}

		public void setModified_by(String modified_by) {
			this.modified_by = modified_by;
		}

		public boolean isIS_DELETE() {
			return IS_DELETE;
		}

		public void setIS_DELETE(boolean iS_DELETE) {
			IS_DELETE = iS_DELETE;
		}

		public String getClient_code() {
			return client_code;
		}

		public void setClient_code(String client_code) {
			this.client_code = client_code;
		}

		public String getProject_code() {
			return project_code;
		}

		public void setProject_code(String project_code) {
			this.project_code = project_code;
		}

		public void setCreated_by(String created_by) {
			this.created_by = created_by;
		}
		
		
	
	
}
