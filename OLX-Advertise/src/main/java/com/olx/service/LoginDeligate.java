package com.olx.service;

import org.springframework.web.bind.annotation.RequestHeader;

public interface LoginDeligate {
	boolean validateToken(String token);
	Boolean isUserLoggedIn(String Authorization);
	String getUserName(String authToken);
}
