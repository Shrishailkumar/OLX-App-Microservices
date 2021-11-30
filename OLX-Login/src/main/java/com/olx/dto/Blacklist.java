package com.olx.dto;

public class Blacklist {
	public Blacklist(String authToken) {
		super();
		this.authToken = authToken;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public Blacklist() {
	}

	private String authToken;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
