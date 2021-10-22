package com.ICINBank.ICINbanking.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ICINBank.ICINbanking.model.SavingsAccount;
import com.ICINBank.ICINbanking.model.SavingsAccountTransaction;
import com.ICINBank.ICINbanking.repository.SavingsAccountTransactionRepository;
import com.ICINBank.ICINbanking.service.SavingsAccountTransactionService;

@Service
public class SavingsAccountTransactionServiceImpl implements SavingsAccountTransactionService {
	
	@Autowired
	private SavingsAccountTransactionRepository savingsAccTransactionRepo;
	
	
	

	@Override
	public Page<SavingsAccountTransaction> findByAccountNum(int savingActNo,int pageNo, int reportCount) {
		Pageable pageable = PageRequest.of(pageNo-1, reportCount);
		return savingsAccTransactionRepo.findByAccountNum(savingActNo, pageable);
	}




	@Override
	public SavingsAccountTransaction saveSavingsAccountTrans(SavingsAccountTransaction savingsAccountTransaction) {
		// TODO Auto-generated method stub
		return savingsAccTransactionRepo.save(savingsAccountTransaction);
	}







}
