package com.ICINBank.ICINbanking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.service.CustomerService;

@Controller
public class AdminController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/viewUserDetail")
	public String gotoUserDetail(Model model) {
				
		return paginateViewUserDetail(1,model);
		
	}
	
	@GetMapping("/viewUserDetails/{pageNo}")
	public String paginateViewUserDetail(@PathVariable(value="pageNo") int pageNo,Model model ) 
	{		
		int userCount = 10;
		Page<Customer> page = customerService.findAllCustomer(pageNo, userCount);
		List<Customer> listOfUserDetail = page.getContent();		
		model.addAttribute("activePage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalRecords",page.getTotalElements());
		model.addAttribute("listOfUserDetail",listOfUserDetail);
		
		return "viewUserDetails";
	}
	
	@GetMapping("/userAccountHandler")
	public String enableOrDisableUserAccount(@RequestParam(value="id") int id,@RequestParam(value="pageNo") int pageNo,Model model) {
		
//		log.info("Entered Cheque Book Approval Function");
		
		customerService.enableDisableUserAcc(id);
		
		return paginateViewUserDetail(pageNo,model);
		
		
	}
	
	

}
