package com.ICINBank.ICINbanking.service;

import com.ICINBank.ICINbanking.POJO.TransferDetailPOJO;
import com.ICINBank.ICINbanking.model.SavingsAccount;
public interface SavingsAccountService {
	
	public SavingsAccount createNewSavingsAccount();
	public void transferFromSavingsAccount(TransferDetailPOJO transferDetailPOJO);	
	public SavingsAccount findBySavingsAccNum(int acctNum);	
	public SavingsAccount saveSavingsAccount(SavingsAccount savingsAccount);

}
