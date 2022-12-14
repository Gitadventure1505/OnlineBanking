package com.in.OnlineBanking.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.in.OnlineBanking.constants.OnlineBankingConstants;
import com.in.OnlineBanking.dao.UserDao;
import com.in.OnlineBanking.jwt.CustomerUserDetailsService;
import com.in.OnlineBanking.jwt.JwtFilter;
import com.in.OnlineBanking.jwt.JwtUtil;
import com.in.OnlineBanking.pojo.User;
import com.in.OnlineBanking.service.UserService;
import com.in.OnlineBanking.utils.OnlineBankingUtils;
import com.in.OnlineBanking.wrapper.UserWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {


	
	@Autowired
	UserDao userdao;
	
	@Autowired
	AuthenticationManager authenticationmanager;
	
	@Autowired
	CustomerUserDetailsService customeruserdetailsservice;
	
	@Autowired
	JwtUtil jwtutil;
	
	@Autowired
	JwtFilter jwtfilter;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		
		
		log.info("Inside Signup{}", requestMap);
		
		try
		{
		if(validateSignupMap(requestMap))
		{
			User user = userdao.findByEmailId(requestMap.get("email"));
			if(Objects.isNull(user))
			{

				userdao.save(getUserFromMap(requestMap));
				return OnlineBankingUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
			}
			else
			{
				return OnlineBankingUtils.getResponseEntity("Email already exists", HttpStatus.BAD_REQUEST);
			}
		}
		else
		{
			return OnlineBankingUtils.getResponseEntity(OnlineBankingConstants.Invalid_Data, HttpStatus.BAD_REQUEST);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return OnlineBankingUtils.getResponseEntity(OnlineBankingConstants.Something_Went_Wrong, HttpStatus.BAD_REQUEST);
		
	}
	
	private boolean validateSignupMap(Map<String, String> requestMap)
	{
		if(requestMap.containsKey("name") && requestMap.containsKey("email") && requestMap.containsKey("contactNumber")
		&& requestMap.containsKey("password"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private User getUserFromMap(Map<String, String> requestmap)
	{
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		String encodedPassword = bcrypt.encode(requestmap.get("password"));
		
		User user = new User();
		user.setEmail(requestmap.get("email"));
		user.setName(requestmap.get("name"));
		user.setContactNumber(requestmap.get("contactNumber"));
		user.setPassword(encodedPassword);
		user.setStatus("false");
		user.setRole("user");
		return user;
		
	}


	
	
	
	
	@Override
	public ResponseEntity<Map<String, String>> login(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		
		log.info("Inside Login");
		
		try
		{
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			User opUser = userdao.findByEmailId(requestMap.get("email"));
			Authentication auth = authenticationmanager.authenticate
													(new UsernamePasswordAuthenticationToken(requestMap.get("email"),opUser.getPassword()));
			
			
			
		if(bcrypt.matches(requestMap.get("password"), opUser.getPassword()))
		{
			log.info("Inside Login authenticated");
			log.info("Inside Login "+ customeruserdetailsservice.getUserDetail().getStatus() );
			
			if(customeruserdetailsservice.getUserDetail().getStatus().equalsIgnoreCase("true"))
			{
				
				
				Map<String, String> loginResponse = new HashMap<>();
				
				String jwtToken = jwtutil.generateToken(customeruserdetailsservice.getUserDetail().getEmail(), 
						customeruserdetailsservice.getUserDetail().getRole());
				String name = customeruserdetailsservice.getUserDetail().getName();
				String mail = customeruserdetailsservice.getUserDetail().getEmail();
				String contactNumber = customeruserdetailsservice.getUserDetail().getContactNumber();
				String role = customeruserdetailsservice.getUserDetail().getRole();
				String isAdmin = (role.equalsIgnoreCase("admin")) ? "True" : "";
	
				System.out.println(isAdmin);
				
				
				
				loginResponse.put("token", jwtToken);
				loginResponse.put("name", name);
				loginResponse.put("mail", mail);
				loginResponse.put("contactNumber", contactNumber);
				loginResponse.put("isAdmin", isAdmin);
				
				return new ResponseEntity<Map<String, String>>(loginResponse, HttpStatus.OK);
		
			}
			else
			{
				
				return OnlineBankingUtils.getResponseEntity_L("wait for admin approval. ", HttpStatus.BAD_REQUEST);
			}
						
		}
		}
		catch (Exception e)
		{
			log.error("{}", e);
		}

		return OnlineBankingUtils.getResponseEntity_L("Bad Credentials", HttpStatus.BAD_REQUEST);
	}
	

	@Override
	public ResponseEntity<List<UserWrapper>> getAllUser() {
		// TODO Auto-generated method stub
		try
		{
			if(jwtfilter.isAdmin())
			{
				System.out.println("Helllll");
				return new ResponseEntity<List<UserWrapper>>(userdao.getAllUser(), HttpStatus.OK);
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
	
	@Override
	public ResponseEntity<List<UserWrapper>> getNewUsers() {
		// TODO Auto-generated method stub
		try
		{
			if(jwtfilter.isAdmin())
			{
				return new ResponseEntity<List<UserWrapper>>(userdao.getNewUsers(), HttpStatus.OK);
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

	@Override
	public ResponseEntity<String> update(Map<String, String> requestmap) {
		// TODO Auto-generated method stub
		try 
		{
			if(jwtfilter.isAdmin())
			{
				Optional<User> optional = userdao.findById(Integer.parseInt(requestmap.get("id")));
				if(!optional.isEmpty())
				{
					userdao.updateStatus(requestmap.get("status"), Integer.parseInt(requestmap.get("id")));
					//sendMailToAllAdmin(requestmap.get("status"), optional.get().getEmail(), userdao.getAllAdmin());
					return OnlineBankingUtils.getResponseEntity("User Status updated succcessfully", HttpStatus.OK);
				}
				else
				{
					return OnlineBankingUtils.getResponseEntity("User ID doesnt exists", HttpStatus.OK);
				}
			}
			else
			{
				return OnlineBankingUtils.getResponseEntity(OnlineBankingConstants.UNAUTHORIZED_ACCESS, HttpStatus.INTERNAL_SERVER_ERROR);
			}
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
		// TODO Auto-generated method stub
		return OnlineBankingUtils.getResponseEntity("true", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> changePassword(Map<String, String> requestmap) {
		
		try
		{
			User userObj = userdao.findByEmail(jwtfilter.getCurrentUser());
			if(!userObj.equals(null))
			{
				if(userObj.getPassword().equals(requestmap.get("oldPassword")))
				{
					userObj.setPassword(requestmap.get("newPassword"));
					userdao.save(userObj);
					return OnlineBankingUtils.getResponseEntity("password changed successfully", HttpStatus.OK);
				}
				return OnlineBankingUtils.getResponseEntity("Incorrect password", HttpStatus.BAD_REQUEST);
			}
			return OnlineBankingUtils.getResponseEntity(OnlineBankingConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return OnlineBankingUtils.getResponseEntity(OnlineBankingConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);
	
	}

	@Override
	public ResponseEntity<List<UserWrapper>> getExistingUsers() {
		try
		{
			if(jwtfilter.isAdmin())
			{
				return new ResponseEntity<List<UserWrapper>>(userdao.getExistingUsers(), HttpStatus.OK);
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
