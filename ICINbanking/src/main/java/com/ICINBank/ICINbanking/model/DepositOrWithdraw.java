package com.ICINBank.ICINbanking.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class DepositOrWithdraw {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int AccountNum;
	
	private String customerName;
	
	private Date requestRaisedDate;
	
	private Date requestApprovedDate;
	
	private String actionType;
	
	private String accountType;
	
	private String status;
	
	private Double amount;

}
