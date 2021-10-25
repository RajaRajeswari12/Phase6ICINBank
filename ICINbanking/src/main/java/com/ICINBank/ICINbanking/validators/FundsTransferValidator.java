package com.ICINBank.ICINbanking.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import com.ICINBank.ICINbanking.POJO.DepositOrWithdrawPOJO;
import com.ICINBank.ICINbanking.POJO.TransferDetailPOJO;
import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.service.CurrentAccountService;
import com.ICINBank.ICINbanking.service.CustomerService;
import com.ICINBank.ICINbanking.service.SavingsAccountService;

@Component
public class FundsTransferValidator implements Validator{

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CurrentAccountService currentAccountService;
	
	@Autowired
	private SavingsAccountService savingsAccountService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return TransferDetailPOJO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		TransferDetailPOJO transferDetailPOJO = (TransferDetailPOJO)target;
		Customer customer = customerService.findByUserName(transferDetailPOJO.getUserName());
		
		if(transferDetailPOJO.getToAccType().equalsIgnoreCase("savings")) {
			if(savingsAccountService.findBySavingsAccNum(transferDetailPOJO.getToAccNo()) == null) {
				errors.rejectValue("toAccNo", "wrong.Account");
			}
		}else if(transferDetailPOJO.getToAccType().equalsIgnoreCase("primary")) {
			if(currentAccountService.findByCurrentAccNum(transferDetailPOJO.getToAccNo()) == null) {
				errors.rejectValue("toAccNo", "wrong.Account");
			}
		}
		
	
		if(transferDetailPOJO.getAmountToBeTranfered() <= 0) {
			errors.rejectValue("amountToBeTranfered", "Valid.Amount");			
		}else if(transferDetailPOJO.getAmountToBeTranfered() >= 200000) {
			errors.rejectValue("amountToBeTranfered", "Exceeds");
		}else if((transferDetailPOJO.getFromAccType().equalsIgnoreCase("primary")) &&(transferDetailPOJO.getAmountToBeTranfered() > customer.getCurrentAccount().getAmount())) {
			errors.rejectValue("amountToBeTranfered", "insufficient");
		}else if((transferDetailPOJO.getFromAccType().equalsIgnoreCase("savings")) &&(transferDetailPOJO.getAmountToBeTranfered() > customer.getSavingsAccount().getAmount())) {
			errors.rejectValue("amountToBeTranfered", "insufficient");
		}

	}

}
