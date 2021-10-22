package com.ICINBank.ICINbanking.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ICINBank.ICINbanking.model.ChequeBook;
import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.service.ChequeBookService;
import com.ICINBank.ICINbanking.service.CustomerService;



@Controller
public class ChequeBookController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ChequeBookService chequeBookService;
	
	@GetMapping(value="/requestChequeBook")
	public ModelAndView chequeRequestPage() {
		
	
		
		ModelAndView ChequeRequestMV = new ModelAndView();
		ChequeRequestMV.setViewName("chequeBkRequest");
		ChequeRequestMV.addObject("chequeBook",new ChequeBook());
		return ChequeRequestMV;
		
	}
	
	@PostMapping(value="/requestChequeBook")
	public ModelAndView raiseChequeRequest(@ModelAttribute("chequeBook") ChequeBook chequeBook,HttpServletRequest request) {
		
		Customer cust = customerService.getCustomerBySessionVar(request);
		chequeBook.setCustomer(cust);
		chequeBookService.createChequeBookRequest(chequeBook);
		ModelAndView ChequeRequestMV = new ModelAndView();
		ChequeRequestMV.setViewName("homePage");
		
		return ChequeRequestMV;
		
	}
	
	

}
