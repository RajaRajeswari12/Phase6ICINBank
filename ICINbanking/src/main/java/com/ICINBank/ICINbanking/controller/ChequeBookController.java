package com.ICINBank.ICINbanking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ICINBank.ICINbanking.model.ChequeBook;
import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.service.ChequeBookService;
import com.ICINBank.ICINbanking.service.CustomerService;



@Controller
public class ChequeBookController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ChequeBookService chequeBookService;
	
	private Logger log = LoggerFactory.getLogger(ChequeBookController.class);
	
	@GetMapping(value="/requestChequeBook")
	public ModelAndView chequeRequestPage() {
		
	
		
		ModelAndView ChequeRequestMV = new ModelAndView();
		ChequeRequestMV.setViewName("chequeBkRequest");
		ChequeRequestMV.addObject("chequeBook",new ChequeBook());
		return ChequeRequestMV;
		
	}
	
	@PostMapping(value="/requestChequeBook")
	public ModelAndView raiseChequeRequest(@ModelAttribute("chequeBook") ChequeBook chequeBook,HttpServletRequest request) {
		
		Customer cust = customerService.getCustomerBySessionVar(request);
		chequeBook.setCustomer(cust);
		chequeBookService.createChequeBookRequest(chequeBook);
		ModelAndView ChequeRequestMV = new ModelAndView();
	
		ChequeRequestMV.addObject("authCustomer", cust);
		ChequeRequestMV.setViewName("homePage");
		
		return ChequeRequestMV;
		
	}
	
	@GetMapping("/viewChequeBkList")
	public String gotoChequeBookReqListPg(Model model) {
				
		return paginateViewChequeBookReqList(1,model);
		
	}
	
	@GetMapping("/viewChequeBookList/{pageNo}")
	public String paginateViewChequeBookReqList(@PathVariable(value="pageNo") int pageNo,Model model ) 
	{		
		log.info("Entered paginateViewChequeBookReqList Function");
		int requestCount = 10;
		Page<ChequeBook> page = chequeBookService.findAllChequeBookRequest(pageNo, requestCount);
		List<ChequeBook> listOfChequeBookReq = page.getContent();		
		model.addAttribute("activePage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalRecords",page.getTotalElements());
		model.addAttribute("listOfChequeBookReq",listOfChequeBookReq);
		return "viewChequeBkRequestList";
	}
	
	@GetMapping("/approveChequeBookReq")
	public String approveChequeBookRequest(@RequestParam(value="id") int id,@RequestParam(value="pageNo") int pageNo,Model model) {
		
		log.info("Entered Cheque Book Approval Function");
		
		chequeBookService.chequeBookApproval(id);
		
		return paginateViewChequeBookReqList(pageNo,model);
		
		
	}
	

}
