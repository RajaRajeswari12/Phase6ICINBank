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

	@Override
	public SavingsAccount createNewSavingsAccount()  {
		SavingsAccount newAccount = new SavingsAccount();		
		newAccount.setAccountType("savings");
		return savingsAccountRepo.save(newAccount);	
		
	}

	@Override
	public void transferFromSavingsAccount(TransferDetailPOJO transferDetails) {
		SavingsAccount fromAcc = findBySavingsAccNum(transferDetails.getFromAccNo());
		SavingsAccount toAcc = null;
		CurrentAccount toCurAcc = null ;
		double fromPrevBalance = fromAcc.getAmount();
		double fromAvailBalance = fromPrevBalance-transferDetails.getAmountToBeTranfered();
		
		
		double toPrevBalance = 0;
		double toAvailBalance = 0;
		if(transferDetails.getToAccType().equalsIgnoreCase("savings")) {
			toAcc = findBySavingsAccNum(transferDetails.getToAccNo());	
			toPrevBalance = toAcc.getAmount();
			 toAvailBalance = toPrevBalance+transferDetails.getAmountToBeTranfered();
			 toAcc.setAmount(toAvailBalance);
			 
			 SavingsAccountTransaction srt = new SavingsAccountTransaction(toAcc,transferDetails.getFromAccNo(),transferDetails.getToAccNo(),new Date(),toPrevBalance,toAvailBalance,"Credit");
			 srtService.saveSavingsAccountTrans(srt);
			 saveSavingsAccount(toAcc);
			 
			
		}else if(transferDetails.getToAccType().equalsIgnoreCase("primary")) {
			toCurAcc = crAccService.findByCurrentAccNum(transferDetails.getToAccNo());
			toPrevBalance = toCurAcc.getAmount();
			 toAvailBalance = toPrevBalance+transferDetails.getAmountToBeTranfered();
			 toCurAcc.setAmount(toAvailBalance);
			 CurrentAccountTransaction crt = new CurrentAccountTransaction(toCurAcc,transferDetails.getFromAccNo(),transferDetails.getToAccNo(),new Date(),toPrevBalance,toAvailBalance,"Credit");
			 crtService.saveCurrentAccountTransaction(crt);
			 crAccService.saveCurrentAccount(toCurAcc);
		}
		
		fromAcc.setAmount(fromAvailBalance);
		
		 SavingsAccountTransaction srt = new SavingsAccountTransaction(fromAcc,transferDetails.getFromAccNo(),transferDetails.getToAccNo(),new Date(),fromPrevBalance,fromAvailBalance,"Debit");
		 srtService.saveSavingsAccountTrans(srt);
		
		savingsAccountRepo.save(fromAcc);
		
		
		
		
		
		
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