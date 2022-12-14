package com.in.OnlineBanking.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.in.OnlineBanking.wrapper.UserWrapper;



public interface UserService 
{
		ResponseEntity<String> signUp(Map<String, String> requestMap);

		//ResponseEntity<String> login(Map<String, String> requestMap);
		
		ResponseEntity<Map<String, String>> login(Map<String, String> requestMap);
		
		ResponseEntity<List<UserWrapper>> getAllUser();
		
		ResponseEntity<List<UserWrapper>> getNewUsers(); 
		
		ResponseEntity<List<UserWrapper>> getExistingUsers();

		ResponseEntity<String> update(Map<String, String> requestmap);

		ResponseEntity<String> checkToken();

		ResponseEntity<String> changePassword(Map<String, String> requestmap);
	

}
