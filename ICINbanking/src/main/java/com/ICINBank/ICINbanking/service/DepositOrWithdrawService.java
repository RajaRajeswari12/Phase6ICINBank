package com.ICINBank.ICINbanking.service;

import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

import com.ICINBank.ICINbanking.POJO.DepositOrWithdrawPOJO;
import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.model.DepositOrWithdraw;

public interface DepositOrWithdrawService {
	
	public DepositOrWithdraw saveDepositOrWithdraw(DepositOrWithdrawPOJO depositOrWithdrawPojo,Customer customer);
	
	Page<DepositOrWithdraw> findAllDepositOrWithdrawRequest(int pageNo, int requestCount);
	
	void doDepositOrWithdraw(int id);
	
	void cancelDepositOrWithdraw(int id);
	
	public void validateDepositOrWithdrawPage(DepositOrWithdrawPOJO depositOrWithdrawPOJO,BindingResult bindingResult,Customer customer);

}
