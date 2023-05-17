package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.FireRisk;
import com.example.demo.repository.FireRiskRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class FireRiskController {
	
	@Autowired
	private FireRiskRepository fireRiskRepository;

	
	@PostMapping("/addFireRiskDetails")
	 @Operation(description ="Post api to add fire risk details") 
	    public ResponseEntity<FireRisk> addFireRisk(@RequestBody FireRisk fireRisk) {
	        FireRisk savedFireRisk = fireRiskRepository.save(fireRisk);
	        return ResponseEntity.ok(savedFireRisk);
	    }
}
