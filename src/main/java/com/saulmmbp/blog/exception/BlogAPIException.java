package com.saulmmbp.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException {

	private static final long serialVersionUID = -7239557647268002719L;
	private HttpStatus status;
	private String message;
	
	public BlogAPIException(String message, HttpStatus status, String message2) {
		super(message);
		this.status = status;
		this.message = message2;
	}

	public BlogAPIException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

}
