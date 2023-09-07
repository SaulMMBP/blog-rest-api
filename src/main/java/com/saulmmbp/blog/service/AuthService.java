package com.saulmmbp.blog.service;

import com.saulmmbp.blog.dto.Jwt;
import com.saulmmbp.blog.dto.Login;
import com.saulmmbp.blog.dto.Message;
import com.saulmmbp.blog.dto.Register;

public interface AuthService {

	Jwt login(Login loginRequest);
	
	Message register(Register registerRequest);
	
}
