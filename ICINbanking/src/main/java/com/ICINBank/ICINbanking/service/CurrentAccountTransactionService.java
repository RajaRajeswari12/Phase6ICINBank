package com.ICINBank.ICINbanking.service;

import org.springframework.data.domain.Page;

import com.ICINBank.ICINbanking.model.CurrentAccountTransaction;

public interface CurrentAccountTransactionService {
	

	Page<CurrentAccountTransaction> findByCurrentAccountNum(int currentActNo,int pageNo, int reportCount);
	
	public CurrentAccountTransaction saveCurrentAccountTransaction(CurrentAccountTransaction currentAccountTransaction);

}
