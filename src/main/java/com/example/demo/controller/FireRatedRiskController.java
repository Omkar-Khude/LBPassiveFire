package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.FireRatedRisk;
import com.example.demo.repository.FireRatedRiskRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class FireRatedRiskController {
	
	@Autowired
	private FireRatedRiskRepository fireRatedRiskRepository;
	
	@PostMapping("/addFireRatedRiskDetails")
	 @Operation(description ="Post api to add fire rated risk details") 
	    public ResponseEntity<FireRatedRisk> addFireRatedRisk(@RequestBody FireRatedRisk fireRatedRisk) {
	        FireRatedRisk savedFireRatedRisk = fireRatedRiskRepository.save(fireRatedRisk);
	        return ResponseEntity.ok(savedFireRatedRisk);
	    }

}
