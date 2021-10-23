package com.ICINBank.ICINbanking.serviceImpl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ICINBank.ICINbanking.POJO.TransferDetailPOJO;
import com.ICINBank.ICINbanking.model.CurrentAccount;
import com.ICINBank.ICINbanking.model.CurrentAccountTransaction;
import com.ICINBank.ICINbanking.model.SavingsAccount;
import com.ICINBank.ICINbanking.model.SavingsAccountTransaction;
import com.ICINBank.ICINbanking.repository.CurrentAccountRepository;
import com.ICINBank.ICINbanking.repository.SavingsAccountRepository;
import com.ICINBank.ICINbanking.service.CurrentAccountService;
import com.ICINBank.ICINbanking.service.CurrentAccountTransactionService;
import com.ICINBank.ICINbanking.service.SavingsAccountService;
import com.ICINBank.ICINbanking.service.SavingsAccountTransactionService;

@Service
public class SavingsAccountServiceImpl implements SavingsAccountService{
	@Autowired
	private SavingsAccountRepository savingsAccountRepo;
	
	@Autowired
	private CurrentAccountService crAccService;
	
	@Autowired
	private SavingsAccountTransactionService srtService;
	
	@Autowired
	private CurrentAccountTransactionService crtService;
	
	private Logger log = LoggerFactory.getLogger(SavingsAccountServiceImpl.class);

	@Override
	public SavingsAccount createNewSavingsAccount()  {
		SavingsAccount newAccount = new SavingsAccount();		
		newAccount.setAccountType("savings");
		return savingsAccountRepo.save(newAccount);	
		
	}
	
	@Override
	public void saveSavingsTransaction(int accNo,TransferDetailPOJO transferDetails,String creditOrDebit) {
		
		SavingsAccount savAccount = findBySavingsAccNum(accNo);
		log.info("Inside SaveTransaction &&&&&&&&&&&&"+accNo) ;
		double prevBalance = savAccount.getAmount();
		double availBalance = 0;
		if(creditOrDebit.equalsIgnoreCase("credit")) {
			availBalance = prevBalance+transferDetails.getAmountToBeTranfered();
		}else if(creditOrDebit.equalsIgnoreCase("debit")) {
			availBalance = prevBalance-transferDetails.getAmountToBeTranfered();
		}
		savAccount.setAmount(availBalance);
		SavingsAccountTransaction srt = new SavingsAccountTransaction(savAccount,transferDetails.getFromAccNo(),transferDetails.getToAccNo(),new Date(),prevBalance,availBalance,creditOrDebit);
		 srtService.saveSavingsAccountTrans(srt);
		 saveSavingsAccount(savAccount);
		
	}

	@Override
	public void transferFromSavingsAccount(TransferDetailPOJO transferDetails) {
		
		saveSavingsTransaction(transferDetails.getFromAccNo(),transferDetails,"Debit");
	
		if(transferDetails.getToAccType().equalsIgnoreCase("savings")) {
			
			saveSavingsTransaction(transferDetails.getToAccNo(),transferDetails,"Credit");			 
			
		}else if(transferDetails.getToAccType().equalsIgnoreCase("primary")) {
			
			 crAccService.saveCurrentTransaction(transferDetails.getToAccNo(),transferDetails,"Credit");
		}
				
	}
	

	@Override
	public SavingsAccount saveSavingsAccount(SavingsAccount saveAccount) {
		
		return savingsAccountRepo.save(saveAccount);
		
	}

	@Override
	public SavingsAccount findBySavingsAccNum(int acctNum) {
		// TODO Auto-generated method stub
		return savingsAccountRepo.findBySavingActNo(acctNum);
	}



}
