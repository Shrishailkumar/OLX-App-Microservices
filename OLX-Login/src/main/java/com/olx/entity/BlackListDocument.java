package com.olx.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "blacklist")
public class BlackListDocument {
	@org.springframework.data.annotation.Id
	private String authToken;

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public BlackListDocument() {
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
