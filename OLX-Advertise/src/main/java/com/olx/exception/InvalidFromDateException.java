package com.olx.exception;

public class InvalidFromDateException extends RuntimeException{
	
	public String message ;

	public InvalidFromDateException() {
		this.message = "";
	}
	public InvalidFromDateException(String message) {
		super();
		this.message = message;
	}
	
@Override
public String toString() {
	// TODO Auto-generated method stub
	return "invalid FromDate Exception " +this.message;
}
}
