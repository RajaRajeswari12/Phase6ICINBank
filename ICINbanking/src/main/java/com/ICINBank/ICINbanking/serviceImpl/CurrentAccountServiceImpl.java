package com.ICINBank.ICINbanking.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ICINBank.ICINbanking.POJO.TransferDetailPOJO;
import com.ICINBank.ICINbanking.model.CurrentAccount;
import com.ICINBank.ICINbanking.model.CurrentAccountTransaction;
import com.ICINBank.ICINbanking.model.SavingsAccount;
import com.ICINBank.ICINbanking.model.SavingsAccountTransaction;
import com.ICINBank.ICINbanking.repository.CurrentAccountRepository;
import com.ICINBank.ICINbanking.service.CurrentAccountService;
import com.ICINBank.ICINbanking.service.CurrentAccountTransactionService;
import com.ICINBank.ICINbanking.service.SavingsAccountService;
import com.ICINBank.ICINbanking.service.SavingsAccountTransactionService;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService{
	
	@Autowired
	private CurrentAccountRepository currentAccountRepo;
	
	@Autowired
	private CurrentAccountTransactionService crtService;
	
	@Autowired
	private SavingsAccountTransactionService srtService;
	
	@Autowired
	private SavingsAccountService savingsAccService;

	@Override
	public CurrentAccount createNewCurrentAccount() {
		CurrentAccount newAccount = new CurrentAccount();
	
		newAccount.setAccountType("primary");
		return currentAccountRepo.save(newAccount);	
	}

	@Override
	public void transferFromCurrentAccount(TransferDetailPOJO transferDetails) {

		saveCurrentTransaction(transferDetails.getFromAccNo(), transferDetails, "Debit");
		if(transferDetails.getToAccType().equalsIgnoreCase("savings")) {
		
			 savingsAccService.saveSavingsTransaction(transferDetails.getToAccNo(),transferDetails,"Credit");
			 
			
		}else if(transferDetails.getToAccType().equalsIgnoreCase("primary")) {
			saveCurrentTransaction(transferDetails.getToAccNo(), transferDetails, "Credit");
		}
		
	
		
		
		
		
		
	}
	
	
	

	@Override
	public CurrentAccount saveCurrentAccount(CurrentAccount currentAccount) {
		
		return currentAccountRepo.save(currentAccount);
		
	}

	@Override
	public CurrentAccount findByCurrentAccNum(int acctNum) {
	
		return currentAccountRepo.findByCurrentActNo(acctNum);
	}

	@Override
	public void saveCurrentTransaction(int accNo, TransferDetailPOJO transferDetails, String creditOrDebit) {
		CurrentAccount currentAccount = findByCurrentAccNum(accNo);
//		log.info("Inside saveCurrentTransaction &&&&&&&&&&&&"+accNo) ;
		double prevBalance = currentAccount.getAmount();
		double availBalance = 0;
		if(creditOrDebit.equalsIgnoreCase("credit")) {
			availBalance = prevBalance+transferDetails.getAmountToBeTranfered();
		}else if(creditOrDebit.equalsIgnoreCase("debit")) {
			availBalance = prevBalance-transferDetails.getAmountToBeTranfered();
		}
		currentAccount.setAmount(availBalance);
		CurrentAccountTransaction crt = new CurrentAccountTransaction(currentAccount,transferDetails.getFromAccNo(),transferDetails.getToAccNo(),new Date(),prevBalance,availBalance,creditOrDebit);
		 crtService.saveCurrentAccountTransaction(crt);
		 saveCurrentAccount(currentAccount);
		
	}

}
