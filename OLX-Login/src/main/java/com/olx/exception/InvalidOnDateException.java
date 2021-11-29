package com.olx.exception;

public class InvalidOnDateException  extends RuntimeException{
	
	public String message ;

	public InvalidOnDateException() {
		this.message = "";
	}
	public InvalidOnDateException(String message) {
		super();
		this.message = message;
	}
	
@Override
public String toString() {
	// TODO Auto-generated method stub
	return "invalid onDateException " +this.message;
}
}