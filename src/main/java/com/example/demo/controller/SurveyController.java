package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Survey;
import com.example.demo.repository.SurveyRepository;

import io.swagger.v3.oas.annotations.Operation;



@RestController
@RequestMapping("/api")
public class SurveyController {
	
	@Autowired
	private SurveyRepository surveyRepository;
	
	 @PostMapping("/addSurveyDetails")
	 @Operation(description ="Post api to add survey details") 
	    public ResponseEntity<Survey> addSurvey(@RequestBody Survey survey) {
	        Survey savedSurvey = surveyRepository.save(survey);
	        return ResponseEntity.ok(savedSurvey);
	    }
	 
	 
	 @GetMapping("/getSurveyDetails")
	 @Operation(description ="Get api to get all survey details") 
	 public List<Survey> getAllSurveys() {
	        return surveyRepository.findAll();
	 }
	
	 @GetMapping("/getSurvey/FD")
	 @Operation(description ="Get api to get FD survey details") 
	 public ResponseEntity<List<Survey>> getFDSurvey() {
	     List<Survey> fdSurvey = surveyRepository.findBySurveyTypeIgnoreCase("FD");
	     return ResponseEntity.ok(fdSurvey);
	 }
}
