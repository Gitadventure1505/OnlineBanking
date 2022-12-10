package com.in.OnlineBanking.service;


import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.in.OnlineBanking.pojo.Account;
import com.in.OnlineBanking.wrapper.AccountWrapper;




public interface AccountService {
	
	ResponseEntity<String> addaccount(Map<String, String> requestMap);
	
	ResponseEntity<List<Account>> getAccountsByMail(String customer_mail);
	
	

}
