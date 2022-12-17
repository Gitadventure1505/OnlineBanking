package com.in.OnlineBanking.restimpl;

import java.util.ArrayList;
import com.in.OnlineBanking.pojo.PrimaryAccount;
import com.in.OnlineBanking.pojo.SavingsAccount;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.in.OnlineBanking.utils.OnlineBankingUtils;
import com.in.OnlineBanking.wrapper.AccountWrapper;
import com.in.OnlineBanking.constants.OnlineBankingConstants;
import com.in.OnlineBanking.pojo.Account;
import com.in.OnlineBanking.pojo.PrimaryAccount;
import com.in.OnlineBanking.rest.AccountRest;

import com.in.OnlineBanking.rest.UserRest;
import com.in.OnlineBanking.service.AccountService;


@RestController
public class AccountRestImpl implements AccountRest
{
	
	@Autowired 
	AccountService accountService;
	
	

	@Override
	public ResponseEntity<String> addaccount(Map<String, String> requestMap) {
		
		try
		{
			//return userservice.signUp(requestMap);
			return accountService.addaccount(requestMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return OnlineBankingUtils.getResponseEntity(OnlineBankingConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Account>> getAccountsByMail(String customer_mail, String account_type) {
		
		try
		{
			return accountService.getAccountsByMail(customer_mail, account_type);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<Account>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	@Override
	public ResponseEntity<Account> getAccountsByMailAndType(String customer_mail, String account_type) {
		try
		{
			
			return accountService.getAccountsByMailAndType(customer_mail, account_type);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<Account>(new Account(), HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}

	@Override
	public ResponseEntity<String> primaryAccountTransaction(PrimaryAccount primaryAccount) {
		try
		{
			
			return accountService.primaryAccountTransaction(primaryAccount);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>(OnlineBankingConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);

		
	}

	@Override
	public ResponseEntity<List<PrimaryAccount>> getTransactions(long accountNum, String accountType) {
		
		try
		{
			return accountService.getTransactions(accountNum, accountType);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<SavingsAccount>> getSavingsTransactions(long accountNum) {
		try
		{
			return accountService.getSavingsTransactions(accountNum);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> savingsAccountTransaction(SavingsAccount savingsAccount) {
		try
		{
			
			return accountService.savingsAccountTransaction(savingsAccount);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>(OnlineBankingConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Account>> getAccountsByIsRequested() {
		
		try
		{
			
			return accountService.getAccountsByIsRequested();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	}




	


