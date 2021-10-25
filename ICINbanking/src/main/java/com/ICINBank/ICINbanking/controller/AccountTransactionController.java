package com.ICINBank.ICINbanking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ICINBank.ICINbanking.model.CurrentAccountTransaction;
import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.model.SavingsAccountTransaction;
import com.ICINBank.ICINbanking.service.CurrentAccountTransactionService;
import com.ICINBank.ICINbanking.service.CustomerService;
import com.ICINBank.ICINbanking.service.SavingsAccountTransactionService;

@Controller
public class AccountTransactionController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private SavingsAccountTransactionService savingsAccTransactionService;
	
	@Autowired
	private CurrentAccountTransactionService currentAccountTransactionService;
	
	@GetMapping("/viewSavingsAccountTransaction")
	public String getSavingsAccountTrans(Model model,@RequestParam("TransactionAccNo") int TransactionAccNo ) {

		return paginateViewBySavingsAccNo(1,model,TransactionAccNo);
		
	}
	
	@GetMapping("/SavingsAccountTransaction/{pageNo}")
	public String paginateViewBySavingsAccNo(@PathVariable(value="pageNo") int pageNo,Model model,
			@RequestParam("TransactionAccNo") int TransactionAccNo ) 
	{		
		int reportCount = 10;
		Page<SavingsAccountTransaction> page = savingsAccTransactionService.findByAccountNum(TransactionAccNo,pageNo, reportCount);
		List<SavingsAccountTransaction> listOfSavingsAccountTransaction = page.getContent();		
		model.addAttribute("activePage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalRecords",page.getTotalElements());
		model.addAttribute("listOfAccountTransaction",listOfSavingsAccountTransaction);
		model.addAttribute("paginate","savings");
		model.addAttribute("AccNO", TransactionAccNo);
		return "viewTransactionDetails";
	}

	@GetMapping("/viewCurrentAccountTransaction")
	public String getCurrentAccountTrans(Model model,@RequestParam("TransactionAccNo") int TransactionAccNo ) {
				
		return paginateViewByCurrentAccNo(1,model,TransactionAccNo);
		
	}
	
	@GetMapping("/CurrentAccountTransaction/{pageNo}")
	public String paginateViewByCurrentAccNo(@PathVariable(value="pageNo") int pageNo,Model model,
			@RequestParam("TransactionAccNo") int TransactionAccNo ) 
	{		
		int reportCount = 10;
		Page<CurrentAccountTransaction> page = currentAccountTransactionService.findByCurrentAccountNum(TransactionAccNo,pageNo, reportCount);
		List<CurrentAccountTransaction> listOfCurrentAccountTransaction = page.getContent();		
		model.addAttribute("activePage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalRecords",page.getTotalElements());
		model.addAttribute("listOfAccountTransaction",listOfCurrentAccountTransaction);
		model.addAttribute("paginate","primary");
		model.addAttribute("AccNO", TransactionAccNo);
		return "viewTransactionDetails";
	}
	
	@GetMapping("/accountTransactionDetail")
	public String getCurrentAccountTrans(Model model,@RequestParam("accType") String accType,HttpServletRequest request) {
		Customer cust = customerService.getCustomerBySessionVar(request);
		int TransactionAccNo = 0;
		String returnResult = null;
		if(accType.equalsIgnoreCase("primary")) {
			TransactionAccNo = cust.getCurrentAccount().getCurrentActNo();
			returnResult = paginateViewByCurrentAccNo(1,model,TransactionAccNo);
		}else if(accType.equalsIgnoreCase("savings")) {
			TransactionAccNo = cust.getSavingsAccount().getSavingActNo();
			returnResult = paginateViewBySavingsAccNo(1,model,TransactionAccNo);
		}
		return returnResult;		
	}
}
