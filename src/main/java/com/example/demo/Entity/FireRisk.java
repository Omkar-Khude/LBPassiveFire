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
public class FireRisk {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String locationOfRisk;
	
	private String apertureSize;
	
	private String intendedFireResistancePeriod;
	
	private String descriptionOfFireRatedRisk;
	
	private String approvedRemedialWorks;
	
	private String componentManufacturer;
	
	private String engineerNotes;
	
	private String photo;
	
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

	public String getLocationOfRisk() {
		return locationOfRisk;
	}

	public void setLocationOfRisk(String locationOfRisk) {
		this.locationOfRisk = locationOfRisk;
	}

	public String getApertureSize() {
		return apertureSize;
	}

	public void setApertureSize(String apertureSize) {
		this.apertureSize = apertureSize;
	}

	public String getIntendedFireResistancePeriod() {
		return intendedFireResistancePeriod;
	}

	public void setIntendedFireResistancePeriod(String intendedFireResistancePeriod) {
		this.intendedFireResistancePeriod = intendedFireResistancePeriod;
	}

	public String getDescriptionOfFireRatedRisk() {
		return descriptionOfFireRatedRisk;
	}

	public void setDescriptionOfFireRatedRisk(String descriptionOfFireRatedRisk) {
		this.descriptionOfFireRatedRisk = descriptionOfFireRatedRisk;
	}

	public String getApprovedRemedialWorks() {
		return approvedRemedialWorks;
	}

	public void setApprovedRemedialWorks(String approvedRemedialWorks) {
		this.approvedRemedialWorks = approvedRemedialWorks;
	}

	public String getComponentManufacturer() {
		return componentManufacturer;
	}

	public void setComponentManufacturer(String componentManufacturer) {
		this.componentManufacturer = componentManufacturer;
	}

	public String getEngineerNotes() {
		return engineerNotes;
	}

	public void setEngineerNotes(String engineerNotes) {
		this.engineerNotes = engineerNotes;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

}
