package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException  extends RuntimeException{
	private HttpStatus status;
	private String message;
	public BlogApiException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public BlogApiException(String message,HttpStatus status, String message1) {
		super(message);
		this.status = status;
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	
	
	
	
	
	
	

}
