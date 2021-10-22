package com.ICINBank.ICINbanking.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ICINBank.ICINbanking.model.CurrentAccountTransaction;
import com.ICINBank.ICINbanking.repository.CurrentAccountTransactionRepository;
import com.ICINBank.ICINbanking.service.CurrentAccountTransactionService;
@Service
public class CurrentAccountTransactionServiceImpl implements CurrentAccountTransactionService{
	@Autowired
	private CurrentAccountTransactionRepository currentAccTransRepo;

	@Override
	public Page<CurrentAccountTransaction> findByCurrentAccountNum(int currentActNo, int pageNo, int transactionCount) {
		Pageable pageable = PageRequest.of(pageNo-1, transactionCount);
		return currentAccTransRepo.getByCurrentActNo(currentActNo, pageable);
	}

	@Override
	public CurrentAccountTransaction saveCurrentAccountTransaction(
			CurrentAccountTransaction currentAccountTransaction) {
		// TODO Auto-generated method stub
		return currentAccTransRepo.save(currentAccountTransaction);
	}

}
