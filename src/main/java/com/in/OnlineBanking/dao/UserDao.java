package com.in.OnlineBanking.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.in.OnlineBanking.pojo.User;
import com.in.OnlineBanking.wrapper.UserWrapper;



public interface UserDao extends JpaRepository<User, Integer> 
{
	@Query
	//User findByEmail(@Param("email") String email);
	User findByEmailId(@Param("email") String email);
	
	@Query
	List<UserWrapper> getAllUser();
	
	@Query
	List<String> getAllAdmin();
	
	@Query
	List<UserWrapper> getNewUsers();
	
	@Query
	List<UserWrapper> getExistingUsers();
	
	
	@Query
	@Transactional
	@Modifying
	Integer updateStatus(@Param("status") String status, @Param("id") Integer id);
	
	
	User findByEmail(String email);
	
}