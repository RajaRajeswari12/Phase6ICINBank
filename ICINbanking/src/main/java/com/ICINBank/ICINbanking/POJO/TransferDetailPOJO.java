package com.ICINBank.ICINbanking.POJO;

import lombok.Data;

@Data
public class TransferDetailPOJO {
	
	private String fromAccType;
	
	private int fromAccNo;
	
	private String toAccType;
	
	private int toAccNo;
	
	private double amountToBeTranfered;
	
	private boolean isCurrentAcc;
	
	private boolean isSavingsAcc;
	
	private String userName;

}
