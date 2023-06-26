package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.DoorCheck;
import com.example.demo.repository.DoorCheckRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class DoorCheckController {
	
	@Autowired
	private DoorCheckRepository doorCheckRepository;
	
	 @PostMapping("/addDoorCheckDetails")
	 @Operation(description ="Post api to add door check details") 
	    public ResponseEntity<DoorCheck> addDoorCheck(@RequestBody DoorCheck doorCheck) {
	        DoorCheck savedDoorCheck = doorCheckRepository.save(doorCheck);
	        return ResponseEntity.ok(savedDoorCheck);
	    }
	 @GetMapping("/getDoorCheckDetails")
	 @Operation(description ="Get api to get all door check details") 
	 public ResponseEntity<List<DoorCheck>> getAllDoorChecks() {
		    List<DoorCheck> doorChecks = doorCheckRepository.findAll();
		    return new ResponseEntity<>(doorChecks, HttpStatus.OK);
		}

}
