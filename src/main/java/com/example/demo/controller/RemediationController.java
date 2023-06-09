package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.Remediation;
import com.example.demo.Entity.Survey;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.RemediationRepository;
import com.example.demo.repository.SurveyRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class RemediationController {
	
	@Autowired
	private RemediationRepository remediationRepository;
	
	@Autowired
	private SurveyRepository surveyRepository;
	

	@PostMapping("/addRemediationDetails")
	 @Operation(description ="Post api to add remediation details") 
	    public ResponseEntity<Remediation> addRemediation(@RequestBody Remediation remediation) {
	        Remediation savedRemediation = remediationRepository.save(remediation);
	        return ResponseEntity.ok(savedRemediation);
	    }
	
	@PostMapping("/addRemediationDetails/{surveyId}")
	 @Operation(description ="Post api to add remediation details by surveyId") 
	public Remediation addRemediationBySurveyId(@PathVariable int surveyId, @RequestBody Remediation remediation) {
	    Optional<Survey> surveyOptional = surveyRepository.findById(surveyId);
	    if (surveyOptional.isPresent()) {
	        Survey survey = surveyOptional.get();
	        remediation.setSurvey(survey);
	        return remediationRepository.save(remediation);
	    } else {
	        throw new IllegalArgumentException("Survey not found with id: " + surveyId);
	    }
	}
	 
	@GetMapping("/getRemediationDetails")
	 @Operation(description ="Get api to get all remediation details") 
	 public List<Remediation> getAllRemediation() {
	        return remediationRepository.findAll();
	 }
	 
	 @GetMapping("/getRemediationDetails/{id}")
	 @Operation(description ="Get api to get Remediation details by id") 
		public Remediation getRemediationById(@PathVariable int id) {
		    if(remediationRepository.findById(id)==null) {
		    	throw new NotFoundException("Remediation with Id: "+id+" Not Found");
		    }else {
		    	return remediationRepository.findById(id);
		    }
		}
}
