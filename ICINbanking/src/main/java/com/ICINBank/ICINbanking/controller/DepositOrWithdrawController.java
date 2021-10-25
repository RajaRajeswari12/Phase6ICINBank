package com.ICINBank.ICINbanking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ICINBank.ICINbanking.POJO.DepositOrWithdrawPOJO;
import com.ICINBank.ICINbanking.model.Customer;
import com.ICINBank.ICINbanking.model.DepositOrWithdraw;
import com.ICINBank.ICINbanking.service.CustomerService;
import com.ICINBank.ICINbanking.service.DepositOrWithdrawService;
@Controller
public class DepositOrWithdrawController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private DepositOrWithdrawService depositOrWithdrawService;
	

	
	private Logger log = LoggerFactory.getLogger(DepositOrWithdrawController.class);
	
	@GetMapping("/depositOrWithdrawFund")
	public ModelAndView goToDepositPage(@RequestParam("actionType") String actionType) {
		log.info("Inside GoToDepositPage");
		ModelAndView depositMV = new ModelAndView();
		depositMV.addObject("depositOrWithdrawPOJO", new DepositOrWithdrawPOJO());		
		depositMV.setViewName(actionType);		
		return depositMV;
	}
	
	@PostMapping("/depositOrWithdrawFund")
	public ModelAndView depositPage(@ModelAttribute("depositOrWithdrawPOJO") DepositOrWithdrawPOJO depositOrWithdrawPOJO,BindingResult bindingResult,HttpServletRequest request) {
		log.info("Initiated the Funds Transfer Function" );
		ModelAndView depositMV = new ModelAndView();
		Customer cust = customerService.getCustomerBySessionVar(request);
		depositOrWithdrawPOJO.setCustomerName(cust.getUser().getUserName());
		
		depositOrWithdrawService.validateDepositOrWithdrawPage(depositOrWithdrawPOJO, bindingResult, cust);

		if(bindingResult.hasErrors()) {
			depositMV.setViewName(depositOrWithdrawPOJO.getActionType());
			return depositMV;
		}
				
		depositOrWithdrawService.saveDepositOrWithdraw(depositOrWithdrawPOJO, cust);
		
		depositMV.setViewName("homePage");
		depositMV.addObject("authCustomer", cust);	
		return depositMV;
	}
	
	
	@GetMapping("/viewDepositWithdrawReq")
	public String gotoDepositWithdrawListPg(Model model) {				
		return paginateViewDepositWithdrawReqList(1,model);		
	}
	
	@GetMapping("/viewDepositWithdrawReqLst/{pageNo}")
	public String paginateViewDepositWithdrawReqList(@PathVariable(value="pageNo") int pageNo,Model model ) 
	{		
		log.info("Entered Pagination Function");
		int requestCount = 10;
		Page<DepositOrWithdraw> page = depositOrWithdrawService.findAllDepositOrWithdrawRequest(pageNo, requestCount);
		List<DepositOrWithdraw> listOfDepositOrWithdrawReq = page.getContent();		
		model.addAttribute("activePage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalRecords",page.getTotalElements());
		model.addAttribute("listOfDepositOrWithdrawReq",listOfDepositOrWithdrawReq);
		log.info("paginateViewDepositWithdrawReqList Exit");
		return "viewMoneyTransferRequest";
	}
	
	@GetMapping("/depositOrWithdrawApproval")
	public String depositOrWithdrawAmount(@RequestParam(value="id") int id,@RequestParam(value="pageNo") int pageNo,Model model) {
		
		log.info("Entered depositOrWithdrawAmount Function");		
		depositOrWithdrawService.doDepositOrWithdraw(id);		
		return paginateViewDepositWithdrawReqList(pageNo,model);
				
	}
	
	@GetMapping("/depositOrWithdrawDisapproval")
	public String depositOrWithdrawAmountDisapprove(@RequestParam(value="id") int id,@RequestParam(value="pageNo") int pageNo,Model model) {
		
		log.info("Entered depositOrWithdrawAmountDisapprove Function");		
		depositOrWithdrawService.cancelDepositOrWithdraw(id);		
		return paginateViewDepositWithdrawReqList(pageNo,model);
				
	}

}
