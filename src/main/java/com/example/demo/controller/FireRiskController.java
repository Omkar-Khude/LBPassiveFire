package com.example.demo.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.FireRisk;
import com.example.demo.Entity.Survey;
import com.example.demo.repository.FireRiskRepository;
import com.example.demo.repository.SurveyRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class FireRiskController {
	
	@Autowired
	private FireRiskRepository fireRiskRepository;
	
	@Autowired
	private SurveyRepository surveyRepository;

	
	@PostMapping("/addFireRiskDetails")
	 @Operation(description ="Post api to add fire risk details") 
	    public ResponseEntity<FireRisk> addFireRisk(@RequestBody FireRisk fireRisk) {
	        FireRisk savedFireRisk = fireRiskRepository.save(fireRisk);
	        return ResponseEntity.ok(savedFireRisk);
	    }
	
	@PostMapping("/addFireRiskDetails/{surveyId}")
	 @Operation(description ="Post api to add fire risk details by surveyId") 
	public FireRisk addFireRiskBySurveyId(@PathVariable int surveyId, @RequestBody FireRisk fireRisk) {
	    Optional<Survey> surveyOptional = surveyRepository.findById(surveyId);
	    if (surveyOptional.isPresent()) {
	        Survey survey = surveyOptional.get();
	        fireRisk.setSurvey(survey);
	        return fireRiskRepository.save(fireRisk);
	    } else {
	        throw new IllegalArgumentException("Survey not found with id: " + surveyId);
	    }
	}
}
