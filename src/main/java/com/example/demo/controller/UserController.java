package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Admin;
import com.example.demo.Entity.User;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	

	 @PostMapping("/adduser")
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
	 public List<User> getAllusers() {
	        return userRepository.findAll();
	 }
}
