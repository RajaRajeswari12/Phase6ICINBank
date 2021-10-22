package com.ICINBank.ICINbanking.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ICINBank.ICINbanking.model.ChequeBook;
import com.ICINBank.ICINbanking.repository.ChequeBookRepository;
import com.ICINBank.ICINbanking.service.ChequeBookService;

@Service
public class ChequeBookServiceImpl implements ChequeBookService {
	
	@Autowired
	private ChequeBookRepository chequeBookRepo;

	@Override
	public ChequeBook createChequeBookRequest(ChequeBook chequeBook) {
		chequeBook.setStatus("pending");
		return chequeBookRepo.save(chequeBook);
	}

	@Override
	public ChequeBook chequeBookApproval(ChequeBook chequeBook) {
		chequeBook.setStatus("approved");
		
		return chequeBookRepo.save(chequeBook);
	}

}
