package com.ICINBank.ICINbanking.POJO;

import lombok.Data;

@Data
public class DepositOrWithdrawPOJO {

	private String accountType;
	
	private double amount;
	
	private String actionType;
	
	private String customerName;
	
}
