package com.in.OnlineBanking.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.in.OnlineBanking.constants.OnlineBankingConstants;
import com.in.OnlineBanking.dao.AccountDao;
import com.in.OnlineBanking.dao.LastUsedAccountNumDao;
import com.in.OnlineBanking.jwt.JwtFilter;
import com.in.OnlineBanking.pojo.Account;
import com.in.OnlineBanking.pojo.LastUsedAccountNum;
import com.in.OnlineBanking.pojo.User;
import com.in.OnlineBanking.service.AccountService;
import com.in.OnlineBanking.utils.OnlineBankingUtils;
import com.in.OnlineBanking.wrapper.AccountWrapper;
import com.in.OnlineBanking.wrapper.UserWrapper;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AccountDao accountDao;
	
	@Autowired
	LastUsedAccountNumDao lastUsedAccountNumDao;
	
	@Autowired
	JwtFilter jwtFilter;
	
	
	
	@Override
	public ResponseEntity<String> addaccount(Map<String, String> requestMap) {
         log.info("Inside addaccount{}", requestMap);
		
		try
		{
			Account	account = new Account();
			account.setAccount_type(requestMap.get("account_type")); 
			account.setCustomer_mail(requestMap.get("customer_mail")); 
			
			LastUsedAccountNum lastAccountNum = lastUsedAccountNumDao.findById();
			long value = lastAccountNum.getLastUsedValue();
			value = value+1;
			System.out.println(value);
			
			lastAccountNum.setLastUsedValue(value);
			lastUsedAccountNumDao.save(lastAccountNum);
			
			
			account.setAccount_no(value);
			accountDao.save(account);
			return OnlineBankingUtils.getResponseEntity("Successfully Account added", HttpStatus.OK);
 
			
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return OnlineBankingUtils.getResponseEntity(OnlineBankingConstants.Something_Went_Wrong, HttpStatus.BAD_REQUEST);
		
	}


	
	@Override
	public ResponseEntity<List<Account>> getAccountsByMail(String customer_mail) {try
	{
		if(jwtFilter.isAdmin())
		{
			return new ResponseEntity<List<Account>>(accountDao.findBycustomerMail(customer_mail), HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
