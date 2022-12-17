package com.in.OnlineBanking.rest;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.in.OnlineBanking.pojo.Account;
import com.in.OnlineBanking.pojo.PrimaryAccount;
import com.in.OnlineBanking.pojo.SavingsAccount;
import com.in.OnlineBanking.wrapper.AccountWrapper;




@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/account")
public interface AccountRest {
	
	
	
	@PostMapping(path = "/addaccount")
	public ResponseEntity<String> addaccount(@RequestBody(required = true)Map<String, String> requestMap);
	
	
	@GetMapping(path = "/getAccountsByMail")
	public ResponseEntity<List<Account>> getAccountsByMail(@RequestParam("customer_mail") String customer_mail, @RequestParam("account_type") String account_type );
	
	@GetMapping(path = "/getAccountsByMailAndType")
	public ResponseEntity<Account> getAccountsByMailAndType(@RequestParam("customer_mail") String customer_mail, @RequestParam("account_type") String account_type );
	
	@PostMapping(path = "/primaryAccountTransaction")
	public ResponseEntity<String> primaryAccountTransaction(@RequestBody(required = true)PrimaryAccount primaryAccount);
	

	@PostMapping(path = "/savingsAccountTransaction")
	public ResponseEntity<String> savingsAccountTransaction(@RequestBody(required = true)SavingsAccount savingsAccount);
	
	
	//primaryTansactions
	@GetMapping(path = "/getTransactions")
	public ResponseEntity<List<PrimaryAccount>> getTransactions(@RequestParam("accountNum") long accountNum, @RequestParam("accountType") String accountType);
	
	//savingsTransactions
	@GetMapping(path = "/getSavingsTransactions")
	public ResponseEntity<List<SavingsAccount>> getSavingsTransactions(@RequestParam("accountNum") long accountNum);
	
	
	//@GetMapping(path = "/getAccountsByMailAndTypeAndIsCheque")
	//public ResponseEntity<Account> getAccountsByMailAndTypeAndIsCheque(@RequestParam("customer_mail") String customer_mail, @RequestParam("account_type") String account_type, @RequestParam("i") String customer_mail );
	
	
	//Get Accounts requested for chequebooks
	@GetMapping(path = "/getAccountsByIsRequested")
	public ResponseEntity<List<Account>> getAccountsByIsRequested();
	
	
	//Generate new Chequebook
	@GetMapping(path = "/generateChequeBookForAccount")
	public ResponseEntity<String> generateChequeBookForAccount(@RequestParam("accountNum")long accountNum);
	
	
	//Request Cheque boook from user
	@GetMapping(path = "/requestForChequeBook")
	public ResponseEntity<String> requestForChequeBook(@RequestParam("accountNum")long accountNum);
	
	
	
	
	
	
	
	
	

	
	
	

	
	
	
}
