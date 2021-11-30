package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExceptionGeneric extends RuntimeException{
	
	public String message ;

	public ExceptionGeneric() {
		this.message = "";
	}
	public ExceptionGeneric(String message) {
		super();
		this.message = message;
	}
	
@Override
public String toString() {
	// TODO Auto-generated method stub
	return "<<<< Exception occure in Advertise micro service >>> " +this.message;
}
}