package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.Admin;
import com.example.demo.exception.DuplicateException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.AdminRepository;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api")
public class AdminController {
	
	@Autowired
	private AdminRepository adminRepository;
	
	
	 @PostMapping("/registeradmin")
	 @Operation(description = "Post admin details")
	    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) throws DuplicateException {
	        if (adminRepository.findByEmailId(admin.getEmailId()) != null) {
	            throw new DuplicateException("EmailId already exists!");
	        }
	        Admin newAdmin = adminRepository.save(admin);
	        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
	    }
	

	 @PostMapping("/adminlogin") 
	 @Operation(description ="Post api to login admin by emailId and password") 
	 public ResponseEntity<String> adminLogin(@RequestBody Admin admin) throws NotFoundException {
		    Admin existingAdmin = adminRepository.findByEmailId(admin.getEmailId());
		    if (existingAdmin == null) {
		        throw new NotFoundException("Invalid emailId or password!");
		    }
		    if (!existingAdmin.getPassword().equals(admin.getPassword())) {
		        throw new NotFoundException("Invalid emailId or password!");
		    }
		    return new ResponseEntity<>("Logged in successfully!", HttpStatus.OK);
		}
}
