package com.in.OnlineBanking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in.OnlineBanking.pojo.PrimaryAccount;

public interface PrimaryAccountDao extends JpaRepository<PrimaryAccount, Integer> {

	List<PrimaryAccount> findAllByAccountnum(long accountnum);
	
	

}
