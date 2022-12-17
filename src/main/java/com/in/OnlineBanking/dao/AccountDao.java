package com.in.OnlineBanking.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.in.OnlineBanking.pojo.Account;
import com.in.OnlineBanking.pojo.User;
import com.in.OnlineBanking.wrapper.AccountWrapper;

public interface AccountDao extends JpaRepository<Account, Integer> 
{
	
	Account findAllByCustomerMailAndAccountType(@Param("customer_mail") String customer_mail, @Param("account_type") String  account_type);
	
	
	//List<Account> findBycustomer_mailAndaccount_type(@Param("customer_mail") String customer_mail, @Param("account_type") String  account_type);
	
	
	Account findByAccountNo(@Param("accountNo") long accountNo);
	
	
	Account findAllByCustomerMailAndAccountTypeAndBalance(@Param("customer_mail") String customer_mail, @Param("account_type") String  account_type, @Param("customer_mail") long balance);
	
	
	List<Account> findAllByIsChequeBookAvailableAndIsRequested(boolean isChequeBookAvailable, boolean isRequested);
	
	
}
