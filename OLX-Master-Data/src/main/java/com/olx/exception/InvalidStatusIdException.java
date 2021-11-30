package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidStatusIdException extends RuntimeException{
	
	public String message ;

	public InvalidStatusIdException() {
		this.message = "";
	}
	public InvalidStatusIdException(String message) {
		super();
		this.message = message;
	}
	
@Override
public String toString() {
	// TODO Auto-generated method stub
	return "invalid Status id " +this.message;
}
}
