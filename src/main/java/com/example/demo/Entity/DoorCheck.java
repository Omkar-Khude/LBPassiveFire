package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class DoorCheck {
	@Id
	private int id;
	
	private String name;
	
	private String details;
	
	private String artNo;
	
	private String remedialStatus;
	
	private String remark;
	
	private String beforePhoto;
	
	private String afterPhoto;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getArtNo() {
		return artNo;
	}

	public void setArtNo(String artNo) {
		this.artNo = artNo;
	}

	public String getRemedialStatus() {
		return remedialStatus;
	}

	public void setRemedialStatus(String remedialStatus) {
		this.remedialStatus = remedialStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBeforePhoto() {
		return beforePhoto;
	}

	public void setBeforePhoto(String beforePhoto) {
		this.beforePhoto = beforePhoto;
	}

	public String getAfterPhoto() {
		return afterPhoto;
	}

	public void setAfterPhoto(String afterPhoto) {
		this.afterPhoto = afterPhoto;
	}

	

}
