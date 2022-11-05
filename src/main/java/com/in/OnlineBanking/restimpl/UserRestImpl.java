package com.in.OnlineBanking.restimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.in.OnlineBanking.constants.OnlineBankingConstants;
import com.in.OnlineBanking.rest.UserRest;
import com.in.OnlineBanking.service.UserService;
import com.in.OnlineBanking.utils.OnlineBankingUtils;
import com.in.OnlineBanking.wrapper.UserWrapper;



@RestController
public class UserRestImpl implements UserRest {

	
	@Autowired
	UserService userservice;
	
	
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		try
		{
			return userservice.signUp(requestMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return OnlineBankingUtils.getResponseEntity(OnlineBankingConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@Override
	public ResponseEntity<String> login(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		
		try
		{
			return userservice.login(requestMap);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return OnlineBankingUtils.getResponseEntity(OnlineBankingConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}


	@Override
	public ResponseEntity<List<UserWrapper>> getAllUser() {
		try
		{
			return userservice.getAllUser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@Override
	public ResponseEntity<String> update(Map<String, String> requestmap) {
		try
		{
			return userservice.update(requestmap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return OnlineBankingUtils.getResponseEntity(OnlineBankingConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);

	}


	@Override
	public ResponseEntity<String> checkToken() 
	{
		
		try
		{
			return userservice.checkToken();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return OnlineBankingUtils.getResponseEntity(OnlineBankingConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);

	}


	@Override
	public ResponseEntity<String> changePassword(Map<String, String> requestmap) {
		
		try
		{
			return userservice.changePassword(requestmap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return OnlineBankingUtils.getResponseEntity(OnlineBankingConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
