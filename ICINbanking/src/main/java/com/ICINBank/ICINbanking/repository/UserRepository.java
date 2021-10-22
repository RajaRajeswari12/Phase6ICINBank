package com.ICINBank.ICINbanking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ICINBank.ICINbanking.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	public User findByUserName(String userName);
	

}
