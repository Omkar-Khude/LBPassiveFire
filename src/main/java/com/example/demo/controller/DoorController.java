package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.Door;
import com.example.demo.repository.DoorRepository;

@RestController
public class DoorController {
	
	@Autowired
	private DoorRepository doorRepository;
	
	 @PostMapping("/addDoor")
	    public ResponseEntity<Door> addDoor(@RequestBody Door door) {
	        Door savedDoor = doorRepository.save(door);
	        return ResponseEntity.ok(savedDoor);
	    }

}
