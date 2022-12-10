package com.in.OnlineBanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.in.OnlineBanking.pojo.LastUsedAccountNum;

public interface LastUsedAccountNumDao extends JpaRepository<LastUsedAccountNum, Integer> {
	
	@Query
	LastUsedAccountNum findById();
	
	

}
