package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.Door;
import com.example.demo.repository.DoorRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class DoorController {
	
	@Autowired
	private DoorRepository doorRepository;
	

	 @PostMapping("/addDoorDetails")
	 @Operation(description ="Post api to add door details") 
	 public ResponseEntity<Door> addDoorDetails(@RequestBody Door door) {
	        if ("no".equals(door.getAccess())) {
	            door.setFireRating(null);
	           door.setLabels(null);
	            door.setTestEvidence(null);
	            door.setDoorReplacement(null);
	            door.setArtNo(null);
	        } else if ("yes".equals(door.getAccess())) {
	            if ("no".equals(door.getDoorReplacement())) {
	                door.setArtNo(null);
	            }
	        }

	        Door savedDoor = doorRepository.save(door);
	        return ResponseEntity.ok(savedDoor);
	    }

}
