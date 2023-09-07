package com.saulmmbp.blog.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saulmmbp.blog.dto.Jwt;
import com.saulmmbp.blog.dto.Login;
import com.saulmmbp.blog.dto.Message;
import com.saulmmbp.blog.dto.Register;
import com.saulmmbp.blog.entity.Role;
import com.saulmmbp.blog.entity.User;
import com.saulmmbp.blog.exception.BlogAPIException;
import com.saulmmbp.blog.repository.RoleRepository;
import com.saulmmbp.blog.repository.UserRepository;
import com.saulmmbp.blog.security.JwtUtility;
import com.saulmmbp.blog.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtUtility jwtUtility;
	
	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtility jwtUtility) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtility = jwtUtility;
	}

	@Override
	public Jwt login(Login loginRequest) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.usernameOrEmail(), loginRequest.password());
		Authentication authentication = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtility.createToken(authentication);
		return new Jwt(jwt);
	}

	@Override
	public Message register(Register registerRequest) {
		if (userRepository.existsByUsername(registerRequest.username())) throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
		if (userRepository.existsByEmail(registerRequest.email())) throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");

		User user = new User();
		user.setName(registerRequest.name());
		user.setUsername(registerRequest.username());
		user.setEmail(registerRequest.email());
		user.setPassword(passwordEncoder.encode(registerRequest.password()));
		
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);
		
		userRepository.save(user);
		
		return new Message("User register successfully!.");
	}

}
