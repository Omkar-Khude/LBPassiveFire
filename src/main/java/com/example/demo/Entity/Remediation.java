package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Remediation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String details;
	
	private String artNo;
	
	private String beforePhoto;
	
	private String afterPhoto;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "survey_id")
	@JsonBackReference
    private Survey survey;
	
	
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

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}


}
