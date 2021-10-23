package com.ICINBank.ICINbanking.service;

import com.ICINBank.ICINbanking.POJO.TransferDetailPOJO;
import com.ICINBank.ICINbanking.model.SavingsAccount;
import com.ICINBank.ICINbanking.model.SavingsAccountTransaction;
public interface SavingsAccountService {
	
	public SavingsAccount createNewSavingsAccount();
	public void transferFromSavingsAccount(TransferDetailPOJO transferDetailPOJO);	
	public SavingsAccount findBySavingsAccNum(int acctNum);	
	public SavingsAccount saveSavingsAccount(SavingsAccount savingsAccount);

	public void saveSavingsTransaction(int accNo,TransferDetailPOJO transferDetails,String creditOrDebit);
	
}
