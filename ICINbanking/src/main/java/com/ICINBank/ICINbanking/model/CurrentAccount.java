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
public class CurrentAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currentAc_generator")
	@SequenceGenerator(name="currentAc_generator", sequenceName = "currentAc_seq",initialValue = 1923)
	private Integer currentActNo;
	
	private String accountType;
	
	@OneToMany(mappedBy = "currentAcc")
	private List<CurrentAccountTransaction> transactionList;
	

	@Column(columnDefinition = "int default 0",nullable = true)
	private double amount;
	
	

}

