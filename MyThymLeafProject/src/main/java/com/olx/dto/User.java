package com.olx.dto;

public class User {
	private String username;
	private String city;
	public User(String username, String city) {
		super();
		this.username = username;
		this.city = city;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", city=" + city + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	};
	
	

}
