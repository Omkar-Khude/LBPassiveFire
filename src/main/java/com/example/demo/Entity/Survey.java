package com.example.demo.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Survey {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String surveyType;
	private String siteName;
	private String siteAddress;
	private String dateTime;
	private String engineer;
	
	@OneToMany(mappedBy = "survey")
	@JsonManagedReference
    private List<Door> doors = new ArrayList<>();
	
	@OneToMany(mappedBy = "survey")
	@JsonManagedReference
    private List<Remediation> remediations = new ArrayList<>();
	
	@OneToMany(mappedBy = "survey")
	@JsonManagedReference
    private List<FireRisk> fireRisks = new ArrayList<>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSurveyType() {
		return surveyType;
	}
	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getEngineer() {
		return engineer;
	}
	public void setEngineer(String engineer) {
		this.engineer = engineer;
	}
	public String getSiteAddress() {
		return siteAddress;
	}
	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}
	public List<Door> getDoors() {
		return doors;
	}
	public void setDoors(List<Door> doors) {
		this.doors = doors;
	}
	public List<Remediation> getRemediations() {
		return remediations;
	}
	public void setRemediations(List<Remediation> remediations) {
		this.remediations = remediations;
	}
	public List<FireRisk> getFireRisks() {
		return fireRisks;
	}
	public void setFireRisks(List<FireRisk> fireRisks) {
		this.fireRisks = fireRisks;
	}
	
}
