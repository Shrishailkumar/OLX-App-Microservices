package com.olx.exception;

public class InvalidDateConditionException 
	extends RuntimeException{

	public String message;

	public InvalidDateConditionException () {
			this.message = "";
		}

	public InvalidDateConditionException(String message) {
			super();
			this.message = message;
		}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "InvalidDateConditionException occure! " + this.message;
	}
}