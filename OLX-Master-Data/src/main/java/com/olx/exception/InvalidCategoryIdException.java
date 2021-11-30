package com.olx.exception;

public class InvalidCategoryIdException extends RuntimeException{
	
	public String message ;

	public InvalidCategoryIdException() {
		this.message = "";
	}
	public InvalidCategoryIdException(String message) {
		super();
		this.message = message;
	}
	
@Override
public String toString() {
	// TODO Auto-generated method stub
	return "invalid Category id " +this.message;
}
}