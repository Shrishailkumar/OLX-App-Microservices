package com.olx.controller;

import java.security.Principal;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NaukriController {
	
	@GetMapping(value="/")
	public String helloUser(Principal principal){
		OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) principal;
		String loginUname = (String) auth2AuthenticationToken.getPrincipal().getAttributes().get("login");
		return "Hello"+	loginUname+ "how are you?";
	}

}
