package com.zensar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidStockIdException extends RuntimeException{
	
	public String message ;

	public InvalidStockIdException() {
		this.message = "";
	}
	public InvalidStockIdException(String message) {
		super();
		this.message = message;
	}
	
@Override
public String toString() {
	// TODO Auto-generated method stub
	return "invalid stock id " +this.message;
}
}
