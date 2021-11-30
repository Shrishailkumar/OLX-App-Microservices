package com.olx.exception;

public class InvalidRecordNoException extends RuntimeException{
	
	public String message ;

	public InvalidRecordNoException () {
		this.message = "";
	}
	public InvalidRecordNoException(String message) {
		super();
		this.message = message;
	}
	
@Override
public String toString() {
	// TODO Auto-generated method stub
	return "InvalidRecordNoException " +this.message;
}
}