package com.zieta.tms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;


@Entity
@Table(name = "SKILLSET_CATEGORY_MASTER")
@EntityListeners(AuditingEntityListener.class)
@Data

public class SkillsetCategoryMaster implements Serializable {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    @Column(name = "client_id")
    private Long clientId;
    
    
    @Column(name = "skill_category")
    private String skillCategory;
}
