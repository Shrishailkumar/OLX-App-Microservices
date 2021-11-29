package com.olx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class LoginDeligateImpl implements LoginDeligate {

	@Autowired
	RestTemplate loginRestTemplate;

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	private static final String AUTHORIZATION_HEADER = "Authorization";

	

	@Override
	@CircuitBreaker(name = "CATEGORY-CIRCUIT-BREAKER", fallbackMethod = "fallBackisValidUser")
	public Boolean isUserLoggedIn(String Authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION_HEADER, Authorization);
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<Boolean> response = this.loginRestTemplate.exchange(
				"http://API-GATEWAY/olx/login/user/checkLoggedIn", HttpMethod.GET, requestEntity, Boolean.class);
		return response.getBody();
	}
	
	@CircuitBreaker(name = "CATEGORY-CIRCUIT-BREAKER")
	@Override
	public boolean validateToken(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION_HEADER, token);
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<Boolean> response = this.loginRestTemplate.exchange(
				"http://API-GATEWAY/olx/login/user/validate/token", HttpMethod.GET, requestEntity, Boolean.class);
		return response.getBody();
	}
	@Override
	@CircuitBreaker(name = "CATEGORY-CIRCUIT-BREAKER", fallbackMethod = "fallBackGetUserName")
	public String getUserName(String authToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION_HEADER, authToken);
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = this.loginRestTemplate
				.exchange("http://API-GATEWAY/olx/login/user/getUserName", HttpMethod.GET, requestEntity, String.class);
		return response.getBody();
	}

	public Boolean fallBackValidateTokenFun(String token, Throwable ex) {
		System.out.println("<<<<<<<<<<<<<<<<< loginDeligate data failed : >>>>>>>>>>>>>>>>>>>>>>>>> " + ex);
		return true;
	}

	public Boolean fallBackisValidUser(String Authorization, Throwable ex) {
		System.out.println("<<<<<<<<<<<<<<<<< loginDeligate data failed : >>>>>>>>>>>>>>>>>>>>>>>>> " + ex);
		return true;
	}

	public String fallBackGetUserName(String Authorization, Throwable ex) {
		System.out.println("<<<<<<<<<<<<<<<<< loginDeligate data failed : >>>>>>>>>>>>>>>>>>>>>>>>> " + ex);
		return "error in the login details";
	}

}
