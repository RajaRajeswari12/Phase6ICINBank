package com.ICINBank.ICINbanking.serviceImpl;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.ICINBank.ICINbanking.POJO.DepositOrWithdrawPOJO;
import com.ICINBank.ICINbanking.POJO.TransferDetailPOJO;
import com.ICINBank.ICINbanking.model.ChequeBook;
import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.model.DepositOrWithdraw;
import com.ICINBank.ICINbanking.repository.DepositOrWithdrawRepository;
import com.ICINBank.ICINbanking.service.CurrentAccountService;
import com.ICINBank.ICINbanking.service.CustomerService;
import com.ICINBank.ICINbanking.service.DepositOrWithdrawService;
import com.ICINBank.ICINbanking.service.SavingsAccountService;

@Service
public class DepositOrWithdrawServiceImpl implements DepositOrWithdrawService {
	
	@Autowired
	private DepositOrWithdrawRepository depositOrWithdrawRepo;
	@Autowired
	private CurrentAccountService currentAccService;
	@Autowired
	private SavingsAccountService savingsAccService;
	
	private Logger log = LoggerFactory.getLogger(DepositOrWithdrawServiceImpl.class);

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
		depositOrWithdraw.setAmount(depositOrWithdrawPojo.getAmount());
		depositOrWithdraw.setStatus("pending");
		
		
		
		return depositOrWithdrawRepo.save(depositOrWithdraw);
	}

	@Override
	public Page<DepositOrWithdraw> findAllDepositOrWithdrawRequest(int pageNo, int requestCount) {
		Pageable pageable = PageRequest.of(pageNo-1, requestCount);
		return depositOrWithdrawRepo.findAll(pageable);
	}

	@Override
	public void doDepositOrWithdraw(int id) {
		DepositOrWithdraw depositOrWithdraw = depositOrWithdrawRepo.getById(id);
		TransferDetailPOJO transferDetails = new TransferDetailPOJO();
		transferDetails.setFromAccNo(depositOrWithdraw.getAccountNum());
		transferDetails.setToAccNo(0);
		transferDetails.setAmountToBeTranfered(depositOrWithdraw.getAmount());
		transferDetails.setFromAccType(depositOrWithdraw.getAccountType());
		String creditOrDebit = null;
		log.info(depositOrWithdraw.getActionType() + depositOrWithdraw.getAccountNum());
		if(depositOrWithdraw.getActionType().equalsIgnoreCase("deposit")) {
			creditOrDebit = "Credit";
		}else if(depositOrWithdraw.getActionType().equalsIgnoreCase("withdraw")) {
			creditOrDebit = "Debit";
		}
		
		if(depositOrWithdraw.getAccountType().equalsIgnoreCase("savings")) {
			savingsAccService.saveSavingsTransaction(depositOrWithdraw.getAccountNum(), transferDetails, creditOrDebit);
		}else if(depositOrWithdraw.getAccountType().equalsIgnoreCase("primary")) {
			currentAccService.saveCurrentTransaction(depositOrWithdraw.getAccountNum(), transferDetails, creditOrDebit);
		}		
		
		depositOrWithdraw.setRequestApprovedDate(new Date());
		depositOrWithdraw.setStatus("Approved");
		depositOrWithdrawRepo.save(depositOrWithdraw);
//		return depositOrWithdraw.get();
	}

	@Override
	public void cancelDepositOrWithdraw(int id) {
		DepositOrWithdraw depositOrWithdraw = depositOrWithdrawRepo.getById(id);
		depositOrWithdraw.setRequestApprovedDate(new Date());
		depositOrWithdraw.setStatus("Rejected");
		depositOrWithdrawRepo.save(depositOrWithdraw);
	}

	@Override
	public void validateDepositOrWithdrawPage(DepositOrWithdrawPOJO depositOrWithdrawPOJO,
			BindingResult bindingResult,Customer customer) {
		String errorMessage = null;

		if(depositOrWithdrawPOJO.getAmount() <= 0) {
			errorMessage = "Enter Valid Amount";
		}else if(depositOrWithdrawPOJO.getAmount() >= 200000) {
			errorMessage="Amount exceeds the limit";
		}else if(depositOrWithdrawPOJO.getActionType().equalsIgnoreCase("withdraw")) {
			if(depositOrWithdrawPOJO.getAccountType().equalsIgnoreCase("savings") && (depositOrWithdrawPOJO.getAmount() > customer.getSavingsAccount().getAmount())) {
				errorMessage="Insufficient Amount";
			}else if(depositOrWithdrawPOJO.getAccountType().equalsIgnoreCase("primary") && (depositOrWithdrawPOJO.getAmount() > customer.getCurrentAccount().getAmount())) {
				errorMessage="Insufficient Amount";
			}
			
		}
		
		String objName=depositOrWithdrawPOJO.getActionType()+"Error";
			
		if(errorMessage != null) {
			ObjectError error = new ObjectError("depositOrWithdrawPOJOError",errorMessage);
			bindingResult.addError(error);
		}
		
	}

	
	
}
