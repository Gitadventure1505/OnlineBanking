package com.in.OnlineBanking.restimpl;

import java.util.ArrayList;
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
	public ResponseEntity<List<Account>> getAccountsByMail(String customer_mail) {
		
		try
		{
			return accountService.getAccountsByMail(customer_mail);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<Account>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	}

	


