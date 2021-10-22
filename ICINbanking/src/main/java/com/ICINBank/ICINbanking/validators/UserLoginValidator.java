package com.ICINBank.ICINbanking.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.service.CustomerService;



public class UserLoginValidator implements Validator{
	
	@Autowired
	private CustomerService customerService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Customer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
