package com.in.OnlineBanking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in.OnlineBanking.pojo.SavingsAccount;

public interface SavingsAccountDao extends JpaRepository<SavingsAccount, Integer> {

	List<SavingsAccount> findAllByAccountnum(long accountnum);
}
