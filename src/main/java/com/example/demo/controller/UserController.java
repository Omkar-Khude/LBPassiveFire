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
import com.example.demo.Entity.Admin;
import com.example.demo.Entity.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	

	 @PostMapping("/adduser")
	 @Operation(description ="Post api to add user details") 
	 public ResponseEntity<User> addUser(@RequestBody User user) {
	     if ("admin".equalsIgnoreCase(user.getUsertype())) {
	         Admin admin = new Admin();
	         admin.setFirstName(user.getFirstName());
	         admin.setLastName(user.getLastName());
	         admin.setEmailId(user.getEmailId());
	         admin.setPassword(user.getPassword());
	         admin.setStatus(user.getStatus());
	         adminRepository.save(admin);
	     }

	     userRepository.save(user);
	     return ResponseEntity.ok(user);
	 }
	 
	 @GetMapping("/getuser")
	 @Operation(description ="Get api to get all user details") 
	 public List<User> getAllusers() {
	        return userRepository.findAll();
	 }
	 
	 @GetMapping("/getuser/{id}")
	 @Operation(description ="Get api to get user details by id") 
		public User getUserById(@PathVariable int id) {
		    if(userRepository.findById(id)==null) {
		    	throw new NotFoundException("User with Id: "+id+" Not Found");
		    }else {
		    	return userRepository.findById(id);
		    }
		}
}
