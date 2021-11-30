package com.olx.service;

public interface BlackListService  {
	public boolean logoutUser(String authToken);
	public boolean insertBlackListUserId(String  authToken);
	public boolean checkBlackListUser(String authToken);
    
}
