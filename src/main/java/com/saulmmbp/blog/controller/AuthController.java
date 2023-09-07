package com.saulmmbp.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saulmmbp.blog.dto.Jwt;
import com.saulmmbp.blog.dto.Login;
import com.saulmmbp.blog.dto.Message;
import com.saulmmbp.blog.dto.Register;
import com.saulmmbp.blog.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping(value = {"/login", "/signin"})
	public ResponseEntity<Jwt> login(@Valid @RequestBody Login loginRequest) {
		return ResponseEntity.ok(authService.login(loginRequest));
	}
	
	@PostMapping(value = {"/signup", "/register"})
	public ResponseEntity<Message> register(@Valid @RequestBody Register registerRequest) {
		return new ResponseEntity<>(authService.register(registerRequest), HttpStatus.CREATED);
	}
	
}
