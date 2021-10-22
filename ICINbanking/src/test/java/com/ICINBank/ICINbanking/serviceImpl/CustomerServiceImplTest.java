package com.ICINBank.ICINbanking.serviceImpl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ICINBank.ICINbanking.IciNbankingApplication;
import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=IciNbankingApplication.class) 
public class CustomerServiceImplTest {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Test
	void contextLoads() {
	}
	
	@Autowired
	private CustomerServiceImpl customerServiceImpl;
	
	@Test
	void addNewCustomer() {
		Customer customer = new Customer();
		
		customer.setFirstName("Raja1");
		customer.setLastName("KR");
		customer.setPhoneNum("2222222222");
		customer.setEmail("Raji1@gmail.com");
		
		User user = new User();
		user.setUserName("Raji1");
		user.setPassword("QQQ");
		user.setRole("User");
		user.setActive(true);
		//user.se
		
		customer.setUser(user);
		customerServiceImpl.registerNewCustomer(customer);
//		customer.s
	}
	
	
	

}
