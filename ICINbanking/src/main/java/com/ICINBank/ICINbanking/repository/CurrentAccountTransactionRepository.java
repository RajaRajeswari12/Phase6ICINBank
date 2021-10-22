package com.ICINBank.ICINbanking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ICINBank.ICINbanking.model.CurrentAccountTransaction;

@Repository
public interface CurrentAccountTransactionRepository extends JpaRepository<CurrentAccountTransaction, Integer>{
	
//	public CurrentAccountTransaction getByCurrentActNo(int currentActNo);
	
	Page<CurrentAccountTransaction> getByCurrentActNo(int currentActNo,Pageable pageable);

}
