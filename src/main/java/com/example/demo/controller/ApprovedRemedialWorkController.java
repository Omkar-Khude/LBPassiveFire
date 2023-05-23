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
import com.example.demo.Entity.ApprovedRemedialWork;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ApprovedRemedialWorkRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class ApprovedRemedialWorkController {
	
	@Autowired
	private ApprovedRemedialWorkRepository approvedRemedialWorkRepository;
	
	@PostMapping("/addApprovedRemedialWorkDetails")
	 @Operation(description ="Post api to add approvedRemedialWork details") 
	    public ResponseEntity<ApprovedRemedialWork> addApprovedRemedialWork(@RequestBody ApprovedRemedialWork approvedRemedialWork) {
		ApprovedRemedialWork savedApprovedRemedialWork = approvedRemedialWorkRepository.save(approvedRemedialWork);
	        return ResponseEntity.ok(savedApprovedRemedialWork);
	    }

	@PutMapping("/editApprovedRemedialWorkDetails/{id}")
	@Operation(description ="put api to edit approvedRemedialWork details by id")
	public ApprovedRemedialWork updateApprovedRemedialWork(@PathVariable int id, @RequestBody ApprovedRemedialWork updatedApprovedRemedialWork) {
		ApprovedRemedialWork approvedRemedialWork = approvedRemedialWorkRepository.findById(id).orElse(null);
	    if (approvedRemedialWork != null) {
	    	approvedRemedialWork.setName(updatedApprovedRemedialWork.getName());
	        return approvedRemedialWorkRepository.save(approvedRemedialWork);
	    } else {
	        throw new IllegalArgumentException("approvedRemedialWork not found with id: " + id);
	    }
	}
	
	@DeleteMapping("/deleteApprovedRemedialWorkDetails/{id}")
	@Operation(description ="delete api to delete approvedRemedialWork details by id")
	public void deleteApprovedRemedialWork(@PathVariable int id) {
		approvedRemedialWorkRepository.deleteById(id);
	}
	
	@GetMapping("/getApprovedRemedialWorkDetails/{id}")
	@Operation(description ="get api to get approvedRemedialWork details by id")
	public ResponseEntity<ApprovedRemedialWork> getApprovedRemedialWorkById(@PathVariable int id) {
	    Optional<ApprovedRemedialWork> approvedRemedialWorkOptional = approvedRemedialWorkRepository.findById(id);
	    if (approvedRemedialWorkOptional.isPresent()) {
	    	ApprovedRemedialWork approvedRemedialWork = approvedRemedialWorkOptional.get();
	        return ResponseEntity.ok(approvedRemedialWork);
	    } else {
	    	throw new NotFoundException("approvedRemedialWork not found with id: " + id);
	    }
	}
	
	 @GetMapping("/getApprovedRemedialWorkDetails")
	 @Operation(description ="Get api to get all approvedRemedialWork details") 
	 public List<ApprovedRemedialWork> getAllApprovedRemedialWork() {
	        return approvedRemedialWorkRepository.findAll();
	 }

}
