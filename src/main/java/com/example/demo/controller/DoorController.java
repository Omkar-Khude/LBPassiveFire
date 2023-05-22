package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.Door;
import com.example.demo.exception.NotFoundException;
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
	 
	 @GetMapping("/getDoorDetails")
	 @Operation(description ="Get api to get all door details") 
	 public List<Door> getAllDoor() {
	        return doorRepository.findAll();
	 }
	 
	 @GetMapping("/getDoorDetails/{id}")
	 @Operation(description ="Get api to get door details by id") 
		public Door getDoorById(@PathVariable int id) {
		    if(doorRepository.findById(id)==null) {
		    	throw new NotFoundException("Door with Id: "+id+" Not Found");
		    }else {
		    	return doorRepository.findById(id);
		    }
		}

}
