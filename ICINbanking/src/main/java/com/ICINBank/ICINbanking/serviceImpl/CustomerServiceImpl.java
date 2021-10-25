package com.ICINBank.ICINbanking.serviceImpl;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ICINBank.ICINbanking.model.CurrentAccount;
import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.model.SavingsAccount;
import com.ICINBank.ICINbanking.model.User;
import com.ICINBank.ICINbanking.repository.CustomerRepository;
import com.ICINBank.ICINbanking.service.CurrentAccountService;
import com.ICINBank.ICINbanking.service.CustomerService;
import com.ICINBank.ICINbanking.service.SavingsAccountService;
import com.ICINBank.ICINbanking.service.UserService;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private CurrentAccountService currentAccService;
	
	@Autowired 
	private SavingsAccountService savingsAccService;

	@Override
	public Customer registerNewCustomer(Customer customer) {
		User user = customer.getUser();
		user.setRole("User");
		user.setActive(true);
		
		userService.createNewUser(user);
		customer.setSavingsAccount(savingsAccService.createNewSavingsAccount());
		customer.setCurrentAccount(currentAccService.createNewCurrentAccount());

		return customerRepo.save(customer);
	}

	@Override
	public Customer findByUserName(String userName) {
		// TODO Auto-generated method stub
		return customerRepo.getCustomerByUserName(userName);
	}

	@Override
	public Customer getCustomerBySessionVar(HttpServletRequest request) {			
			HttpSession session = request.getSession(false);
			Customer cust = null;
			if(session != null) {
			String username = (String) session.getAttribute("UserName"); 
			cust = findByUserName(username);
			}
			return cust;	
			
		}

	@Override
	public Page<Customer> findAllCustomer(int pageNo, int userCount) {
		Pageable pageable = PageRequest.of(pageNo-1, userCount);
		return customerRepo.findAll(pageable);
	}

	@Override
	public Customer enableDisableUserAcc(int userId) {
		Customer customer = customerRepo.getById(userId);
		customer.getUser().setActive(!customer.getUser().isActive());
		
		return customerRepo.save(customer);
	}
	
	
//	@Override
//	public String generateAccountNumber() {		
//	 	Random rnd = new Random();
//	    int number = rnd.nextInt(999999999);
//	    return String.format("%09d", number);		
//	}


}
