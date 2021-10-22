package com.ICINBank.ICINbanking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ICINBank.ICINbanking.model.DepositOrWithdraw;

@Repository
public interface DepositOrWithdrawRepository extends JpaRepository<DepositOrWithdraw, Integer> {

}
