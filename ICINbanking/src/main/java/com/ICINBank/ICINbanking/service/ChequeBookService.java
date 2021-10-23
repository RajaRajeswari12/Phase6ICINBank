package com.ICINBank.ICINbanking.service;

import org.springframework.data.domain.Page;

import com.ICINBank.ICINbanking.model.ChequeBook;

public interface ChequeBookService {
	
	public ChequeBook createChequeBookRequest(ChequeBook chequeBook);
	
	public ChequeBook chequeBookApproval(int chequeBookId);
	
	Page<ChequeBook> findAllChequeBookRequest(int pageNo, int requestCount);

}
