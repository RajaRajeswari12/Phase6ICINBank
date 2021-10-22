package com.ICINBank.ICINbanking.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data

@NamedQueries(value= {


@NamedQuery(name="SavingsAccountTransaction.findByAccountNum",query="select srt from SavingsAccountTransaction srt where srt.savingsAcc.savingActNo = ?1")})
public class SavingsAccountTransaction {
	
	@Id
	@GeneratedValue
	private int transactionId;
	
	@ManyToOne
	private SavingsAccount savingsAcc;
	
	private int fromAccount;	
	private int toAccount;
	private Date date;
	private Double prevBalance;
	private Double availableBalance;
	private String transactionType;
	
	public SavingsAccountTransaction() {
		
	}
	public SavingsAccountTransaction(SavingsAccount savingsAcc, int fromAccount, int toAccount, Date date,
			Double prevBalance, Double availableBalance, String transactionType) {
		super();
		this.savingsAcc = savingsAcc;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.date = date;
		this.prevBalance = prevBalance;
		this.availableBalance = availableBalance;
		this.transactionType = transactionType;
	}
	
	

}
