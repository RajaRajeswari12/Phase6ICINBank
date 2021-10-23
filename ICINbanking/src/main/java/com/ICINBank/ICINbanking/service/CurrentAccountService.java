package com.ICINBank.ICINbanking.service;

import com.ICINBank.ICINbanking.POJO.TransferDetailPOJO;
import com.ICINBank.ICINbanking.model.CurrentAccount;
import com.ICINBank.ICINbanking.model.SavingsAccount;

public interface CurrentAccountService {
	public CurrentAccount createNewCurrentAccount();
	public void transferFromCurrentAccount(TransferDetailPOJO transferDetails);
	public CurrentAccount findByCurrentAccNum(int acctNum);	
	public CurrentAccount saveCurrentAccount(CurrentAccount currentAccount);
	
	public void saveCurrentTransaction(int accNo,TransferDetailPOJO transferDetails,String creditOrDebit);

}
