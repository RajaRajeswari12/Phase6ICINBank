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

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;

	
	@RequestMapping(value = "/index",method=RequestMethod.GET)
	public ModelAndView userLoginPage(Model model) {
		ModelAndView userLoginMV = new ModelAndView();
		userLoginMV.setViewName("userLogin");
		userLoginMV.addObject("user",new User());
		return userLoginMV;

	}

	@PostMapping(value="/index")
	public ModelAndView userLogin(@ModelAttribute("user") User user,BindingResult bindingResult,HttpServletRequest request) {
		ModelAndView userDetailMV= new ModelAndView();
		HttpSession session = request.getSession();
		userService.authenticateUser(user, bindingResult);
		if(bindingResult.hasErrors()) {
			userDetailMV.setViewName("userLogin");
			return userDetailMV;
		}else {
			session.setAttribute("UserName", user.getUserName());
		}
		Customer cust = customerService.findByUserName(user.getUserName());
		userDetailMV.addObject("authCustomer", cust);
		userDetailMV.setViewName("homePage");
		return userDetailMV;
	}
	
	@GetMapping(value = "/login")
	public ModelAndView adminLoginPage(Model model) {
		ModelAndView userLoginMV = new ModelAndView();
		userLoginMV.setViewName("adminLogin");
		userLoginMV.addObject("user",new User());
		return userLoginMV;

	}

	@PostMapping(value="/login")
	public String adminLogin(@ModelAttribute("user") User user,BindingResult bindingResult,HttpServletRequest request) {
		HttpSession session = request.getSession();
		userService.authenticateAdmin(user, bindingResult);
		if(bindingResult.hasErrors()) {
			return "adminLogin";
		}else {
			session.setAttribute("AdminName", user.getUserName());
		}

		return "adminHomePage";
	}
	
	@GetMapping(value="/home")
	public ModelAndView goToHomePage(HttpServletRequest request) {
		ModelAndView userDetailMV= new ModelAndView();
		Customer cust = customerService.getCustomerBySessionVar(request);
		if(cust != null) {
			userDetailMV.addObject("authCustomer", cust);
		}
		userDetailMV.setViewName("homePage");
		return userDetailMV;
	}
	
	@GetMapping(value="/adminHome")
	public ModelAndView goToAdminHomePage(HttpServletRequest request) {
		ModelAndView userDetailMV= new ModelAndView();

		userDetailMV.setViewName("adminHomePage");
		return userDetailMV;
	}
	
	
	@GetMapping(value="/viewProfile")
	public ModelAndView goToProfilePage(HttpServletRequest request) {
		ModelAndView userDetailMV= new ModelAndView();
		Customer cust = customerService.getCustomerBySessionVar(request);
		userDetailMV.addObject("customer", cust);
		userDetailMV.setViewName("ViewCustomerDetail");
		return userDetailMV;
	}

}
