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
		CurrentAccount fromAcc = currentAccountRepo.findByCurrentActNo(transferDetails.getFromAccNo());
		SavingsAccount toAcc = null;
		CurrentAccount toCurAcc = null ;
		double fromPrevBalance = fromAcc.getAmount();
		double fromAvailBalance = fromPrevBalance-transferDetails.getAmountToBeTranfered();
		
		
		double toPrevBalance = 0;
		double toAvailBalance = 0;
		if(transferDetails.getToAccType().equalsIgnoreCase("savings")) {
			toAcc = savingsAccService.findBySavingsAccNum(transferDetails.getToAccNo());	
			toPrevBalance = toAcc.getAmount();
			 toAvailBalance = toPrevBalance+transferDetails.getAmountToBeTranfered();
			 toAcc.setAmount(toAvailBalance);
			 
			 SavingsAccountTransaction srt = new SavingsAccountTransaction(toAcc,transferDetails.getFromAccNo(),transferDetails.getToAccNo(),new Date(),toPrevBalance,toAvailBalance,"Credit");
			 srtService.saveSavingsAccountTrans(srt);
			 savingsAccService.saveSavingsAccount(toAcc);
			 
			
		}else if(transferDetails.getToAccType().equalsIgnoreCase("primary")) {
			toCurAcc = currentAccountRepo.findByCurrentActNo(transferDetails.getToAccNo());
			toPrevBalance = toCurAcc.getAmount();
			 toAvailBalance = toPrevBalance+transferDetails.getAmountToBeTranfered();
			 toCurAcc.setAmount(toAvailBalance);
			 CurrentAccountTransaction crt = new CurrentAccountTransaction(fromAcc,transferDetails.getFromAccNo(),transferDetails.getToAccNo(),new Date(),toPrevBalance,toAvailBalance,"Credit");
			 crtService.saveCurrentAccountTransaction(crt);
			 saveCurrentAccount(toCurAcc);
		}
		
		fromAcc.setAmount(fromAvailBalance);
		
		 CurrentAccountTransaction crt = new CurrentAccountTransaction(fromAcc,transferDetails.getFromAccNo(),transferDetails.getToAccNo(),new Date(),fromPrevBalance,fromAvailBalance,"Debit");
		 crtService.saveCurrentAccountTransaction(crt);
		
		 saveCurrentAccount(fromAcc);
		
		
		
		
		
	}
	
	
	

	@Override
	public CurrentAccount saveCurrentAccount(CurrentAccount currentAccount) {
		
		return currentAccountRepo.save(currentAccount);
		
	}

	@Override
	public CurrentAccount findByCurrentAccNum(int acctNum) {
		// TODO Auto-generated method stub
		return currentAccountRepo.findByCurrentActNo(acctNum);
	}

}