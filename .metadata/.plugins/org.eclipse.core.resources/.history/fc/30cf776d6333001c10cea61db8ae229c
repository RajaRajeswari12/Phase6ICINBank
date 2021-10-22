package com.ICINBank.ICINbanking.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ICINBank.ICINbanking.POJO.DepositOrWithdrawPOJO;
import com.ICINBank.ICINbanking.POJO.TransferDetailPOJO;
import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.model.DepositOrWithdraw;
import com.ICINBank.ICINbanking.service.CustomerService;
import com.ICINBank.ICINbanking.service.DepositOrWithdrawService;

@Controller
public class DepositOrWithdrawController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private DepositOrWithdrawService depositOrWithdrawService;
	
	
	private Logger log = LoggerFactory.getLogger(DepositOrWithdrawController.class);
	
	@GetMapping("/depositOrWithdrawFund")
	public ModelAndView goToDepositPage(@RequestParam("actionType") String actionType) {
		ModelAndView depositMV = new ModelAndView();
		depositMV.addObject("depositOrWithdrawPOJO", new DepositOrWithdrawPOJO());
		
		depositMV.setViewName(actionType);		
		return depositMV;
	}
	
	@PostMapping("/depositOrWithdrawFund")
	public ModelAndView depositPage(@ModelAttribute("depositOrWithdrawPojo") DepositOrWithdrawPOJO depositOrWithdrawPOJO,HttpServletRequest request) {
		log.info("Initiated the Funds Transfer Function" );
		Customer cust = customerService.getCustomerBySessionVar(request);
		
		depositOrWithdrawService.saveDepositOrWithdraw(depositOrWithdrawPOJO, cust);
		ModelAndView depositMV = new ModelAndView();
		depositMV.setViewName("homePage");
		depositMV.addObject("authCustomer", cust);	
		return depositMV;
	}

}
