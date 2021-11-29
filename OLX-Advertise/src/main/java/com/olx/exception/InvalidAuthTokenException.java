package com.olx.exception;

public class InvalidAuthTokenException extends RuntimeException{
	
	public String message ;

	public InvalidAuthTokenException() {
		this.message = "";
	}
	public InvalidAuthTokenException(String message) {
		super();
		this.message = message;
	}
	
@Override
public String toString() {
	// TODO Auto-generated method stub
	return "invalid Auth token id " +this.message;
}
}