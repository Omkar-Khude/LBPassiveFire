package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.Admin;
import com.example.demo.dto.LoginRequest;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.AdminRepository;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api")
public class AdminController {
	
	@Autowired
	private AdminRepository adminRepository;


	@PostMapping("/adminlogin")
	@Operation(description ="Post api to login admin by emailId and password") 
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws NotFoundException {
        String emailId = loginRequest.getEmailId();
        String password = loginRequest.getPassword();

        Admin admin = adminRepository.findByEmailId(emailId);

        if (admin == null) {
        	throw new NotFoundException("Invalid emailId or password!");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, admin.getPassword())) {
        	throw new NotFoundException("Invalid emailId or password!");
        }

        return ResponseEntity.ok().body("Logged in successfully!");
    }
	
}
