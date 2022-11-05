package com.in.OnlineBanking.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.in.OnlineBanking.dao.UserDao;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {

	@Autowired
	UserDao userdao;
	
	private com.in.OnlineBanking.pojo.User userDetail;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("Inside loadUserByUsername{}", username);
		userDetail = userdao.findByEmailId(username);
		if(!Objects.isNull(userDetail))
		{
			return new User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
		}
		else
		{
			throw new UsernameNotFoundException("User Not Found");
		}
		
	}
	
	public com.in.OnlineBanking.pojo.User getUserDetail()
	{
		return userDetail;
	}
}
