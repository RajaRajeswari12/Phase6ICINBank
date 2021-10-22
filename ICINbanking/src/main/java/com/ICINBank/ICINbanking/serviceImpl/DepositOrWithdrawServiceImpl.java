package com.ICINBank.ICINbanking.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ICINBank.ICINbanking.POJO.DepositOrWithdrawPOJO;
import com.ICINBank.ICINbanking.model.ChequeBook;
import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.model.DepositOrWithdraw;
import com.ICINBank.ICINbanking.repository.DepositOrWithdrawRepository;
import com.ICINBank.ICINbanking.service.DepositOrWithdrawService;

@Service
public class DepositOrWithdrawServiceImpl implements DepositOrWithdrawService {
	
	@Autowired
	private DepositOrWithdrawRepository depositOrWithdrawRepo;

	@Override
	public DepositOrWithdraw saveDepositOrWithdraw(DepositOrWithdrawPOJO depositOrWithdrawPojo,Customer customer) {
		DepositOrWithdraw depositOrWithdraw = new DepositOrWithdraw();
		if(depositOrWithdrawPojo.getAccountType().equalsIgnoreCase("primary")) {
			depositOrWithdraw.setAccountNum(customer.getCurrentAccount().getCurrentActNo());
		}else if(depositOrWithdrawPojo.getAccountType().equalsIgnoreCase("savings")) {
			depositOrWithdraw.setAccountNum(customer.getSavingsAccount().getSavingActNo());
		}
		depositOrWithdraw.setAccountType(depositOrWithdrawPojo.getAccountType());
		depositOrWithdraw.setActionType(depositOrWithdrawPojo.getActionType());
		depositOrWithdraw.setCustomerName(customer.getFirstName()+customer.getLastName());
		depositOrWithdraw.setRequestRaisedDate(new Date());
		
		depositOrWithdraw.setStatus("pending");
		
		
		
		return depositOrWithdrawRepo.save(depositOrWithdraw);
	}

	@Override
	public Page<DepositOrWithdraw> findAllDepositOrWithdrawRequest(int pageNo, int requestCount) {
		Pageable pageable = PageRequest.of(pageNo-1, requestCount);
		return depositOrWithdrawRepo.findAll(pageable);
	}

	
	
}
