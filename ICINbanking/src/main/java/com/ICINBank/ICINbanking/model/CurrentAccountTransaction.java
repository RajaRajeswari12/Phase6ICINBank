package com.ICINBank.ICINbanking.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.Data;

@Entity
@Data
@NamedQueries(value= {
@NamedQuery(name="CurrentAccountTransaction.getByCurrentActNo",query="select crt from CurrentAccountTransaction crt where crt.currentAcc.currentActNo=?1")})
public class CurrentAccountTransaction {
	
	@Id
	@GeneratedValue
	private int transactionId;
	
	@ManyToOne
	private CurrentAccount currentAcc;
	private int fromAccount;	
	private int toAccount;
	private Date date;
	private Double prevBalance;
	
	@Column(name="new_balance")
	private Double availableBalance;
	private String transactionType;
	
	public CurrentAccountTransaction() {
		
	}
	public CurrentAccountTransaction(CurrentAccount currentAcc, int fromAccount, int toAccount, Date date,
			Double prevBalance, Double availableBalance, String transactionType) {
		super();
		this.currentAcc = currentAcc;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.date = date;
		this.prevBalance = prevBalance;
		this.availableBalance = availableBalance;
		this.transactionType = transactionType;
	}

	

}
