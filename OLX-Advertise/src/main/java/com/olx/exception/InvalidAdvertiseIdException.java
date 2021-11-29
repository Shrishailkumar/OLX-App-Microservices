package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidAdvertiseIdException extends RuntimeException{
	
	public String message ;

	public InvalidAdvertiseIdException() {
		this.message = "";
	}
	public InvalidAdvertiseIdException(String message) {
		super();
		this.message = message;
	}
	
@Override
public String toString() {
	// TODO Auto-generated method stub
	return "invalid Advertise id " +this.message;
}
}
