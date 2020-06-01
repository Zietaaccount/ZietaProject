package com.zietaproj.zieta.response;


//import lombok.Getter;
//import lombok.Setter;

//@Getter
//@Setter
public class ActivitiesByTaskResponse {

	 Long id;
    private Long client_id;
    private Long project_id;
    private Long activity_id;
	 
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
	public Long getProject_id() {
		return project_id;
	}
	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}
	public Long getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(Long activity_id) {
		this.activity_id = activity_id;
	}

	
		 
	
}
