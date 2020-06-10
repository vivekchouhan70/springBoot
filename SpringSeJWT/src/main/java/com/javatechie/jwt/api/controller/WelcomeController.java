package com.javatechie.jwt.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.jwt.api.entity.AuthRequest;
import com.javatechie.jwt.api.util.jwtUtil;

@RestController
public class WelcomeController {

	@Autowired
	private jwtUtil util;
	
	@Autowired
	private AuthenticationManager authmanager;
	
	@GetMapping("/")
	public String welcome() {
		return "welcome to JavaTechie";
	}
	
	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		try {
		authmanager.authenticate(
			new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
				);
		}catch (Exception e) {
			throw new Exception("invalid username/password");
		}
		return util.generateToken(authRequest.getUserName());
	}
	
}
