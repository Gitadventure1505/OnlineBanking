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
import com.in.OnlineBanking.wrapper.AccountWrapper;




@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/account")
public interface AccountRest {
	
	@PostMapping(path = "/addaccount")
	public ResponseEntity<String> addaccount(@RequestBody(required = true)Map<String, String> requestMap);
	
	
	@GetMapping(path = "/getAccountsByMail")
	public ResponseEntity<List<Account>> getAccountsByMail(@RequestParam("customer_mail") String customer_mail);

	
	
	
}
