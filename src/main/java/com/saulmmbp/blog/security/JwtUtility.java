package com.saulmmbp.blog.security;


import java.util.Date;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtility {

	@Value("${api.jwt.issuer}")
	private String issuer;
	
	@Value("${api.jwt.secret}")
	private String secret;
	
	@Value("${api.jwt.expiration-milliseconds}")
	private Long expirationTime;
	
	private Logger logger;
	
	public JwtUtility(Logger logger) {
		this.logger = logger;
	}

	public String createToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expirationDate = new Date(currentDate.getTime() + expirationTime);
		
		String jwt = null;
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			jwt = JWT.create()
					.withIssuer(issuer)
					.withSubject(username)
					.withExpiresAt(expirationDate)
					.sign(algorithm);
		} catch (IllegalArgumentException | JWTCreationException e) {
			logger.error(e.getMessage());
		}
		return jwt;
	}
	
	private DecodedJWT verifyJWT(String token) { 
		DecodedJWT decodedJWT = null;
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(issuer)
					.build();
			decodedJWT = verifier.verify(token);
		} catch (IllegalArgumentException | JWTVerificationException e) {
			logger.error(e.getMessage());
		}
		return decodedJWT;
	}
	
	public String getUsername(String token) {
		DecodedJWT decodedJWT = verifyJWT(token);
		return decodedJWT != null ? decodedJWT.getSubject() : null;
	}
	
}
