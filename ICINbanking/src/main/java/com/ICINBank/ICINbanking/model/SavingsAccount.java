package com.ICINBank.ICINbanking.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
public class SavingsAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "savingActNo_generator")
	@SequenceGenerator(name="savingActNo_generator", sequenceName = "savingActNo_seq", initialValue = 923)
	private Integer savingActNo;
	

	@Column(columnDefinition = "int default 0",nullable = true)
	private double amount;
	
	@OneToMany(mappedBy = "savingsAcc")
	private List<SavingsAccountTransaction> transactionList;
	
	private String accountType;

}
