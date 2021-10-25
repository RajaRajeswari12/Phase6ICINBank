package com.ICINBank.ICINbanking.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.service.CustomerService;


@Component
public class UserRegistrationValidator implements Validator{

@Autowired
private CustomerService customerService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return Customer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Customer customerReg = (Customer) target;
		
		if(customerReg.getPhoneNum().length() < 10) {
			errors.rejectValue("phoneNum", "phone.length");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.userName", "NotEmpty");
		
		if(customerReg.getUser().getUserName().length() < 5 || customerReg.getUser().getUserName().length() > 20) {
			errors.rejectValue("user.userName", "Size.customer.UserName");
		}
		
		if(customerService.findByUserName(customerReg.getUser().getUserName())!=null) {
			errors.rejectValue("user.userName", "Duplicate.adminUser.adminUserName");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"user.password" , "NotEmpty");
		
		if(customerReg.getUser().getPassword().length() < 8 || customerReg.getUser().getPassword().length() > 24) {
			errors.rejectValue("user.password", "Size.adminUser.adminPassword");
		}
		

		
	}

}
