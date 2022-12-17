package com.in.OnlineBanking.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.in.OnlineBanking.wrapper.UserWrapper;


@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/user")
public interface UserRest 
{
	@PostMapping(path = "/signup")
	public ResponseEntity<String> signUp(@RequestBody(required = true)Map<String, String> requestMap);
	
	
	@PostMapping(path = "/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody(required = true)Map<String, String> requestMap);
	
	//public ResponseEntity<String> login(@RequestBody(required = true)Map<String, String> requestMap);


	@GetMapping(path = "/get")
	public ResponseEntity<List<UserWrapper>> getAllUser();
	
	@GetMapping(path = "/getNewUsers")
	public ResponseEntity<List<UserWrapper>> getNewUsers();
	
	//get active users including admin
	@GetMapping(path = "/getExistingUsers")
	public ResponseEntity<List<UserWrapper>> getExistingUsers();
	
	@PostMapping(path = "/update")
	public ResponseEntity<String> update(@RequestBody(required = true) Map<String, String> requestmap);
	
	
	@GetMapping(path = "/checkToken")
	public ResponseEntity<String> checkToken();
	
	
	@PostMapping(path = "/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody(required = true) Map<String, String> requestmap);

}  
