package com.example.demo.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.User;
import com.example.demo.dto.ForgotPasswordRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	 @PostMapping("/adduser")
	 @Operation(description ="Post api to add user details") 
	 public ResponseEntity<User> addUser(@RequestBody User user) {
	        User savedUser = userRepository.save(user);
	        return ResponseEntity.ok(savedUser);
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
	 
	 
	 @PostMapping("/adminLogin")
		@Operation(description ="Post api to login admin by emailId and password") 
		public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws NotFoundException {
	        String emailId = loginRequest.getEmailId();
	        String password = loginRequest.getPassword();

	        User user = userRepository.findByEmailId(emailId);

	        if (user == null  || !user.getUserType().equals("admin")) {
	        	throw new NotFoundException("Invalid emailId or password!");
	        }

	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        if (!passwordEncoder.matches(password, user.getPassword())) {
	        	throw new NotFoundException("Invalid emailId or password!");
	        }

	        return ResponseEntity.ok().body("Logged in successfully!");
	    }
	 
	 @PostMapping("/employeeLogin")
		@Operation(description ="Post api to login employee by emailId and password") 
		public ResponseEntity<?> loginEmployee(@RequestBody LoginRequest loginRequest) throws NotFoundException {
	        String emailId = loginRequest.getEmailId();
	        String password = loginRequest.getPassword();

	        User user = userRepository.findByEmailId(emailId);

	        if (user == null  || !user.getUserType().equals("employee")) {
	        	throw new NotFoundException("Invalid emailId or password!");
	        }

	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        if (!passwordEncoder.matches(password, user.getPassword())) {
	        	throw new NotFoundException("Invalid emailId or password!");
	        }

	        return ResponseEntity.ok().body("Logged in successfully!");
	    }
	 
	 
	 
	 private JavaMailSender javaMailSender;

	    @Autowired
	    public UserController(UserRepository userRepository, JavaMailSender javaMailSender) {
	        this.userRepository = userRepository;
	        this.javaMailSender = javaMailSender;
	    }

	    @PostMapping("/forgotPassword")
	    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
	        User user = userRepository.findByEmailId(forgotPasswordRequest.getEmailId());

	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
	        }

	        String newPassword = generateNewPassword();

	        user.setPassword(newPassword);

	        userRepository.save(user);

	        sendNewPasswordEmailId(user.getEmailId(), newPassword);

	        return ResponseEntity.ok("Password reset successfully. Please check your email for the new password.");
	    }

	    private String generateNewPassword() {

	    	 String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
	    	    int length = 10;

	    	    Random random = new Random();
	    	    StringBuilder newPassword = new StringBuilder();

	    	    for (int i = 0; i < length; i++) {
	    	        int index = random.nextInt(characters.length());
	    	        newPassword.append(characters.charAt(index));
	    	    }

	    	    return newPassword.toString();
	    }

	    private void sendNewPasswordEmailId(String emailId, String newPassword) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(emailId);
	        message.setSubject("Password Reset");
	        message.setText("Your new password: " + newPassword);

	        javaMailSender.send(message);
	    }
	}

