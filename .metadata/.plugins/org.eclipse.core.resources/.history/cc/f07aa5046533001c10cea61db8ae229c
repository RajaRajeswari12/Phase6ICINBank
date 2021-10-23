package com.ICINBank.ICINbanking.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.model.User;
import com.ICINBank.ICINbanking.service.CustomerService;
import com.ICINBank.ICINbanking.service.UserService;
import com.ICINBank.ICINbanking.validators.UserRegistrationValidator;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	
	@Autowired
	private UserRegistrationValidator UserRegistrationValidator;




	@PostMapping(value="/signup")
	public String saveAdminUser(@ModelAttribute("customer") Customer customer,BindingResult bindingResult) {
		UserRegistrationValidator.validate(customer, bindingResult);

		if(bindingResult.hasErrors()) {
			return "userRegistration";
		}

		customerService.registerNewCustomer(customer);
		return "welcome";
	}

	@GetMapping(value="/signup")
	public ModelAndView registerPage() {
		ModelAndView userRegMV = new ModelAndView();
		userRegMV.setViewName("userRegistration");
		userRegMV.addObject("customer",new Customer());
		return userRegMV;
	}


	@GetMapping(value="/logout")
	public ModelAndView logOutSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.removeAttribute("UserName");
		session.invalidate();
		ModelAndView userLogOutMV = new ModelAndView();
		userLogOutMV.setViewName("userLogin");
		userLogOutMV.addObject("user",new User());
		return userLogOutMV;
	}


}
