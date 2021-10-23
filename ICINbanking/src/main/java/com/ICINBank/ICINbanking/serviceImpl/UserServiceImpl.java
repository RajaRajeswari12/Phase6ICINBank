package com.ICINBank.ICINbanking.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.ICINBank.ICINbanking.model.User;
import com.ICINBank.ICINbanking.repository.UserRepository;
import com.ICINBank.ICINbanking.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public User createNewUser(User user) {
		System.out.println(user.getUserName()+ "^^^^^" + user.toString());
		return userRepo.save(user);
	}

	@Override
	public void authenticateUser(User user, BindingResult result) {
		User getValidUser = userRepo.findByUserName(user.getUserName());
		String errorMessage = null;

		if(getValidUser != null) {
			if(!getValidUser.getPassword().equals(user.getPassword())){
				errorMessage = "Invalid password. Try Again with correct credentials.";
			}else if(getValidUser.getRole() == null) {
				errorMessage = "Authorization Not given";
			}				
			else if(!getValidUser.getRole().equalsIgnoreCase("user")) {
				errorMessage = "Authorization Required";
			}else if(!getValidUser.isActive()) {
				errorMessage = "User Account is disabled";
			}
		}else {
			errorMessage = "Username doesn't exist.";			
		}
		if(errorMessage != null) {
			ObjectError error = new ObjectError("userLoginError",errorMessage);
			result.addError(error);
		}
		
	}

	@Override
	public void authenticateAdmin(User user, BindingResult result) {
		User getValidUser = userRepo.findByUserName(user.getUserName());
		String errorMessage = null;

		if(getValidUser != null) {
			if(!getValidUser.getPassword().equals(user.getPassword())){
				errorMessage = "Invalid password. Try Again with correct credentials.";
			}else if(getValidUser.getRole() == null) {
				errorMessage = "Authorization Not given";
			}				
			else if(!getValidUser.getRole().equalsIgnoreCase("admin")) {
				errorMessage = "Authorization Required";
			}else if(!getValidUser.isActive()) {
				errorMessage = "Admin Account is disabled";
			}
		}else {
			errorMessage = "Username doesn't exist.";			
		}
		if(errorMessage != null) {
			ObjectError error = new ObjectError("adminLoginError",errorMessage);
			result.addError(error);
		}
		
		
	}
	

	
}
