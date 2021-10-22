package com.ICINBank.ICINbanking.service;

import com.ICINBank.ICINbanking.model.ChequeBook;

public interface ChequeBookService {
	
	public ChequeBook createChequeBookRequest(ChequeBook chequeBook);
	
	public ChequeBook chequeBookApproval(ChequeBook chequeBook);

}
