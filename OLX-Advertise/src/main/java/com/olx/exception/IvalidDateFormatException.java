package com.olx.exception;

public class IvalidDateFormatException extends RuntimeException{
	
	public String message ;

	public IvalidDateFormatException() {
		this.message = "";
	}
	public IvalidDateFormatException(String message) {
		super();
		this.message = message;
	}
	
@Override
public String toString() {
	// TODO Auto-generated method stub
	return "IvalidDateFormatException " +this.message;
}
}