package com.ICINBank.ICINbanking.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public ChequeBook chequeBookApproval(int chequeBookId) {
		ChequeBook chequeBook = chequeBookRepo.getById(chequeBookId);
		chequeBook.setStatus("approved");
		
		return chequeBookRepo.save(chequeBook);
	}

	@Override
	public Page<ChequeBook> findAllChequeBookRequest(int pageNo, int requestCount) {
	Pageable pageable = PageRequest.of(pageNo-1, requestCount);
		return chequeBookRepo.findAll(pageable);
	}

}
