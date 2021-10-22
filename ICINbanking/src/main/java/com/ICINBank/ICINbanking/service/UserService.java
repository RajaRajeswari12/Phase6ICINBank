package com.ICINBank.ICINbanking.service;

import org.springframework.validation.BindingResult;

import com.ICINBank.ICINbanking.model.User;

public interface UserService {

	public User createNewUser(User user);
	
	public void authenticateUser(User user,BindingResult result);
	
	public void authenticateAdmin(User user,BindingResult result);
	
	
	
	
}
