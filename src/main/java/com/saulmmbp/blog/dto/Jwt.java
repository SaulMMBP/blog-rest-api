package com.saulmmbp.blog.dto;

public record Jwt(String accessToken, String tokenType) {
	public Jwt(String accessToken) {
		this(accessToken, "Bearer");
	}
}
