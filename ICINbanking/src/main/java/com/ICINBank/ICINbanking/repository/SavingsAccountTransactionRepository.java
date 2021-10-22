package com.ICINBank.ICINbanking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ICINBank.ICINbanking.model.SavingsAccountTransaction;

@Repository
public interface SavingsAccountTransactionRepository extends JpaRepository<SavingsAccountTransaction, Integer> {
	
	Page<SavingsAccountTransaction> findByAccountNum(int savingActNo,Pageable pageable);
	
	
	
	

}
