package com.zietaproj.zieta.dto;

import java.util.Date;

public class ClientInfoDTO {

	private Long id;
    private String client_code;
    private String client_comments;
    private String client_name;
    private Long client_status;
  //  private Date created_date;
  //  private String created_by;
   // private String modified_by;
   // private Date modified_date;
   // private boolean IS_DELETE;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClient_code() {
		return client_code;
	}
	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public Long getClient_status() {
		return client_status;
	}
	public void setClient_status(Long client_status) {
		this.client_status = client_status;
	}
//	public Date getCreated_date() {
//		return created_date;
//	}
//	public void setCreated_date(Date created_date) {
//		this.created_date = created_date;
//	}
//	public String getCreated_by() {
//		return created_by;
//	}
//	public void setCreated_by(String created_by) {
//		this.created_by = created_by;
//	}
//	public String getModified_by() {
//		return modified_by;
//	}
//	public void setModified_by(String modified_by) {
//		this.modified_by = modified_by;
//	}
//	public Date getModified_date() {
//		return modified_date;
//	}
//	public void setModified_date(Date modified_date) {
//		this.modified_date = modified_date;
//	}
//	public boolean isIS_DELETE() {
//		return IS_DELETE;
//	}
//	public void setIS_DELETE(boolean iS_DELETE) {
//		IS_DELETE = iS_DELETE;
//	}
	public String getClient_comments() {
		return client_comments;
	}
	public void setClient_comments(String client_comments) {
		this.client_comments = client_comments;
	}
	
    
}
