package com.ICINBank.ICINbanking.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	
	
	@Column(unique = true,nullable = true)
	private String userName;
	
	@Column(nullable = true)
	private String password;
	
	@Column(nullable = true)
	private boolean active;
	
	@Column(nullable = true)
	private String role;
	


}
