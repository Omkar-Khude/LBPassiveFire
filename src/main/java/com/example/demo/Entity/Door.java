package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Door {
	
	private String picture;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String blockReference;
	
	private String level;
	
	private String location;
	
	private String notes;
	
	private String access;
	
	private String fireRating;
	
	private String labels;
	
	private String testEvidence;
	
	private String doorReplacement;
	
	private String artNo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBlockReference() {
		return blockReference;
	}

	public void setBlockReference(String blockReference) {
		this.blockReference = blockReference;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getFireRating() {
		return fireRating;
	}

	public void setFireRating(String fireRating) {
		this.fireRating = fireRating;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getTestEvidence() {
		return testEvidence;
	}

	public void setTestEvidence(String testEvidence) {
		this.testEvidence = testEvidence;
	}

	public String getDoorReplacement() {
		return doorReplacement;
	}

	public void setDoorReplacement(String doorReplacement) {
		this.doorReplacement = doorReplacement;
	}

	public String getArtNo() {
		return artNo;
	}

	public void setArtNo(String artNo) {
		this.artNo = artNo;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}



}
