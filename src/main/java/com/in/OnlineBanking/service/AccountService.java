package com.in.OnlineBanking.service;


import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.in.OnlineBanking.pojo.Account;
import com.in.OnlineBanking.pojo.PrimaryAccount;
import com.in.OnlineBanking.pojo.SavingsAccount;
import com.in.OnlineBanking.wrapper.AccountWrapper;




public interface AccountService {
	
	ResponseEntity<String> addaccount(Map<String, String> requestMap);
	
	ResponseEntity<List<Account>> getAccountsByMail(String customer_mail, String account_type);

	ResponseEntity<Account> getAccountsByMailAndType(String customer_mail, String account_type);

	ResponseEntity<String> primaryAccountTransaction(PrimaryAccount primaryAccount);

	ResponseEntity<List<PrimaryAccount>> getTransactions(long accountNum, String accountType);
	
	ResponseEntity<List<SavingsAccount>> getSavingsTransactions(long accountNum);
	

	ResponseEntity<String> savingsAccountTransaction(SavingsAccount savingsAccount);
	
	
	ResponseEntity<List<Account>> getAccountsByIsRequested();
	
	ResponseEntity<String> generateChequeBookForAccount(long accountNum);

	ResponseEntity<String> requestForChequeBook(long accountNum);

	
	
	

}
