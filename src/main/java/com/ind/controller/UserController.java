package com.ind.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ind.entity.UserJWT;
import com.ind.payload.UserRequest;
import com.ind.payload.UserResponse;
import com.ind.service.UserService;
import com.ind.util.JwtUtil;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService usrService;

	@PostMapping("/save")
	public ResponseEntity<String> saveUser(@RequestBody UserJWT user) {

		Integer num = usrService.saveUser(user);
		return new ResponseEntity<String>("User Created " + num, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserResponse> login(@RequestBody UserRequest req){
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUserName(), req.getPassword()));
		
		String token = jwtUtil.generateToken(req.getUserName());
		return new ResponseEntity<UserResponse>(new UserResponse(token, "Generated by Ajay"), HttpStatus.OK);	
	}
	
	@PostMapping("/welcome")
	public ResponseEntity<String> showUserData(Principal p){
		return new ResponseEntity<String>("Hello "+p.getName()+" have a cheerful day",HttpStatus.OK);
	}

}
