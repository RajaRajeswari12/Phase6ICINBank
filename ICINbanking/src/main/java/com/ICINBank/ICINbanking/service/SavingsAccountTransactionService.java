package com.ICINBank.ICINbanking.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ICINBank.ICINbanking.model.SavingsAccountTransaction;

public interface SavingsAccountTransactionService {

	
	Page<SavingsAccountTransaction> findByAccountNum(int savingActNo,int pageNo, int reportCount);
	
	public SavingsAccountTransaction saveSavingsAccountTrans(SavingsAccountTransaction savingsAccountTransaction);
	
	
	
//	public SavingsAccountTransaction setSavingsAccountTransaction();
	
	

}
