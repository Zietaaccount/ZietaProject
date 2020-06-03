package com.zietaproj.zieta.response;


//import lombok.Getter;
//import lombok.Setter;

//@Getter
//@Setter
public class ActivitiesByTaskResponse {

	 Long id;
	 Long task_id;
    private Long client_id;
 //  private Long project_id;
    private Long activity_id;
    private String activity_code;
    private String activity_desc;
	 
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
//	public Long getProject_id() {
//		return project_id;
//	}
//	public void setProject_id(Long project_id) {
//		this.project_id = project_id;
//	}
	public Long getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(Long activity_id) {
		this.activity_id = activity_id;
	}
	public String getActivity_code() {
		return activity_code;
	}
	public void setActivity_code(String activity_code) {
		this.activity_code = activity_code;
	}
	public String getActivity_desc() {
		return activity_desc;
	}
	public void setActivity_desc(String activity_desc) {
		this.activity_desc = activity_desc;
	}
	
	

	
		 
	
}
