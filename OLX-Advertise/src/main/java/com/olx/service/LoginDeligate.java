package com.olx.service;

public interface LoginDeligate {
	boolean validateToken(String token);
	Boolean isUserLoggedIn(String Authorization);
	String getUserName(String authToken);
}
