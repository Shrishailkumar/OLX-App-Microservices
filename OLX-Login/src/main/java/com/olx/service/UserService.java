package com.olx.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.olx.entity.UserEntity;

public interface UserService extends UserDetailsService{
	public UserEntity createNewUser(UserEntity user);
	//public boolean logoutUser(String authToken);
	public UserEntity getUserDetails(String username);
}
