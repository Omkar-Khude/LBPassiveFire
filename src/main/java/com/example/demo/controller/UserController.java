package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.User;
import com.example.demo.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	 @PostMapping("/adduser")
	    public ResponseEntity<User> addUser(@RequestBody User user) {
	        User savedUser = userRepository.save(user);
	        return ResponseEntity.ok(savedUser);
	    }
	 
	 
	 @GetMapping("/getuser")
	 public List<User> getAllusers() {
	        return userRepository.findAll();
	 }

}
