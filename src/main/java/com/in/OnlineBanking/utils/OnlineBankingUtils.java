package com.in.OnlineBanking.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class OnlineBankingUtils {

	private OnlineBankingUtils()
	{
			
	}

	
	public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpstatus)
	{
		return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}", httpstatus);
		
	}
	
	
	public static ResponseEntity<Map<String, String>> getResponseEntity_L(String responseMessage, HttpStatus httpstatus)
	{
		Map<String, String> loginMap = new HashMap<>();
		loginMap.put("message", responseMessage);
		return new ResponseEntity<Map<String, String>>(loginMap, httpstatus);
		
	}
	
	
	
}
