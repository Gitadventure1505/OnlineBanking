package com.in.OnlineBanking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.in.OnlineBanking.pojo.Account;
import com.in.OnlineBanking.pojo.User;
import com.in.OnlineBanking.wrapper.AccountWrapper;

public interface AccountDao extends JpaRepository<Account, Integer> 
{
	@Query
	List<Account> findBycustomerMail(@Param("customer_mail") String customer_mail);
	
	
	
	
	
	
}
