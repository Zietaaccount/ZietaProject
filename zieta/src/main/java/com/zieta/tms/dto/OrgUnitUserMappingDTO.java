package com.zieta.tms.dto;

import java.util.List;

import javax.persistence.Column;

import com.zieta.tms.model.BaseEntity;
import com.zieta.tms.model.ScreensMaster;
import com.zieta.tms.model.UserInfo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrgUnitUserMappingDTO {

	private Long id;
    private Long clientId;
    private Long orgUnitId;
    private Long userId;
    
    //additional Data
    
    private String clientCode;
    private String clientDescription;
    private String orgNodeCode;
    private String orgNodeName;
    private String userName;
    
    
    private List<UserInfo> usersByClient;
}
