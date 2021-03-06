package com.zieta.tms.dto;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleMasterDTO extends BaseEntity {

	private Long id;
    private Long clientId;
    private String userRole;
    private String clientCode;
    private String clientDescription;
}


