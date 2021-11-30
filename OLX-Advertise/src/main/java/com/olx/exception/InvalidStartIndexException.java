package com.olx.exception;

public class InvalidStartIndexException extends RuntimeException{
	
	public String message ;

	public InvalidStartIndexException() {
		this.message = "";
	}
	public InvalidStartIndexException(String message) {
		super();
		this.message = message;
	}
	
@Override
public String toString() {
	// TODO Auto-generated method stub
	return "InvalidStartIndexException " +this.message;
}
}
