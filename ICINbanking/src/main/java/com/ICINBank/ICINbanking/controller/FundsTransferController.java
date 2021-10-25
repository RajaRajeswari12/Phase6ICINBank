package com.ICINBank.ICINbanking.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ICINBank.ICINbanking.POJO.TransferDetailPOJO;
import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.service.CurrentAccountService;
import com.ICINBank.ICINbanking.service.CustomerService;
import com.ICINBank.ICINbanking.service.SavingsAccountService;
import com.ICINBank.ICINbanking.validators.FundsTransferValidator;

@Controller
public class FundsTransferController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private CurrentAccountService currentAccountService;
	@Autowired
	private SavingsAccountService savingsAccountService;
	
	@Autowired
	private FundsTransferValidator fundsTransferValidator;


	private Logger log = LoggerFactory.getLogger(FundsTransferController.class);

	@GetMapping(value="/transferFund")
	public ModelAndView getFundsTransferPage(@RequestParam("accType") String accType) {
		log.info("Entered the Funds Transfer Page Function");

		TransferDetailPOJO transferDetailPojo = new TransferDetailPOJO();
		transferDetailPojo.setFromAccType(accType);
		if(accType.equalsIgnoreCase("primary")) {
			transferDetailPojo.setCurrentAcc(true);
			transferDetailPojo.setSavingsAcc(false);
		}else if(accType.equalsIgnoreCase("savings")) {
			transferDetailPojo.setCurrentAcc(false);
			transferDetailPojo.setSavingsAcc(true);
		}


		ModelAndView fundTransfertMV = new ModelAndView();
		fundTransfertMV.setViewName("FundTransfer");
		fundTransfertMV.addObject("transferDetailPOJO",transferDetailPojo);
		log.info("Navigate to the Funds Transfer Page" + transferDetailPojo);
		return fundTransfertMV;
	}

	@PostMapping(value="/transferFund")
	public ModelAndView verifyAndTransferFund(@ModelAttribute("transferDetailPOJO") TransferDetailPOJO transferDetailPOJO,BindingResult bindingResult,HttpServletRequest request) {
		log.info("Initiated the Funds Transfer Function" + transferDetailPOJO.toString());
		Customer cust = customerService.getCustomerBySessionVar(request);
		ModelAndView fundTransfertMV = new ModelAndView();
		transferDetailPOJO.setUserName(cust.getUser().getUserName());
		fundsTransferValidator.validate(transferDetailPOJO, bindingResult);
		if(bindingResult.hasErrors()) {
			fundTransfertMV.setViewName("FundTransfer");
			return fundTransfertMV;
		}
		if(transferDetailPOJO.getFromAccType().equalsIgnoreCase("primary")) {
			transferDetailPOJO.setFromAccNo(cust.getCurrentAccount().getCurrentActNo());
			currentAccountService.transferFromCurrentAccount(transferDetailPOJO);
		}else if(transferDetailPOJO.getFromAccType().equalsIgnoreCase("savings")) {
			transferDetailPOJO.setFromAccNo(cust.getSavingsAccount().getSavingActNo());
			savingsAccountService.transferFromSavingsAccount(transferDetailPOJO);
		}


		fundTransfertMV.setViewName("homePage");
		fundTransfertMV.addObject("authCustomer", cust);


		return fundTransfertMV;
	}






}
