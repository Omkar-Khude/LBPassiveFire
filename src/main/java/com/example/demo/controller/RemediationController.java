package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.Remediation;
import com.example.demo.repository.RemediationRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class RemediationController {
	
	@Autowired
	private RemediationRepository remediationRepository;

	@PostMapping("/addRemediation")
	 @Operation(description ="Post api to add remediation details") 
	    public ResponseEntity<Remediation> addRemediation(@RequestBody Remediation remediation) {
	        Remediation savedRemediation = remediationRepository.save(remediation);
	        return ResponseEntity.ok(savedRemediation);
	    }
}
