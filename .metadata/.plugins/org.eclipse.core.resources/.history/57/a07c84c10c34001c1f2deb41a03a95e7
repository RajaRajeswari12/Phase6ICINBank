package com.ICINBank.ICINbanking.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import com.ICINBank.ICINbanking.model.Customer;

public interface CustomerService {
	
	public Customer getCustomerBySessionVar(HttpServletRequest request);
	
	public Customer findByUserName(String userName);
	
	public Customer registerNewCustomer(Customer customer);
	
	Page<Customer> findAllCustomer(int pageNo, int userCount);
	
	public Customer enableDisableUserAcc(int userId);
	

}
