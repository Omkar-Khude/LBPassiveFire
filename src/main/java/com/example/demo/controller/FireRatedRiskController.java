package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.FireRatedRisk;
import com.example.demo.exception.NotFoundException;
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

	@PutMapping("/editFireRatedRiskDetails/{id}")
	@Operation(description ="put api to edit fire rated risk details by id")
	public FireRatedRisk updateFireRatedRisk(@PathVariable int id, @RequestBody FireRatedRisk updatedFireRatedRisk) {
	    FireRatedRisk fireRatedRisk = fireRatedRiskRepository.findById(id).orElse(null);
	    if (fireRatedRisk != null) {
	    	fireRatedRisk.setName(updatedFireRatedRisk.getName());
	        return fireRatedRiskRepository.save(fireRatedRisk);
	    } else {
	        throw new IllegalArgumentException("fireRatedRisk not found with id: " + id);
	    }
	}
	
	@DeleteMapping("/deleteFireRatedRiskDetails/{id}")
	@Operation(description ="delete api to delete fire rated risk details by id")
	public void deleteFireRatedRisk(@PathVariable int id) {
	    fireRatedRiskRepository.deleteById(id);
	}
	
	@GetMapping("/getFireRatedRiskDetails/{id}")
	@Operation(description ="get api to get fire rated risk details by id")
	public ResponseEntity<FireRatedRisk> getFireRatedRiskById(@PathVariable int id) {
	    Optional<FireRatedRisk> fireRatedRiskOptional = fireRatedRiskRepository.findById(id);
	    if (fireRatedRiskOptional.isPresent()) {
	    	FireRatedRisk fireRatedRisk = fireRatedRiskOptional.get();
	        return ResponseEntity.ok(fireRatedRisk);
	    } else {
	        throw new NotFoundException("fireRatedRisk not found with id: " + id);
	    }
	}
	
	 @GetMapping("/getFireRatedRiskDetails")
	 @Operation(description ="Get api to get all FireRatedRisk details") 
	 public List<FireRatedRisk> getAllFireRatedRisk() {
	        return fireRatedRiskRepository.findAll();
	 }
}
