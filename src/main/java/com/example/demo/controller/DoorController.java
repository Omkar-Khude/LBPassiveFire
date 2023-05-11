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
	
	 @PostMapping("/addDoor")
	 @Operation(description ="Post api to add door details") 
	    public ResponseEntity<Door> addDoor(@RequestBody Door door) {
	        Door savedDoor = doorRepository.save(door);
	        return ResponseEntity.ok(savedDoor);
	    }

}
