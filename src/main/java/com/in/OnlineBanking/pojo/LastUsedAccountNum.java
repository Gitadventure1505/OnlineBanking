package com.in.OnlineBanking.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;


@NamedQuery(name = "LastUsedAccountNum.findById" , query = "select l from LastUsedAccountNum l where l.id=1")


@Data
@Entity
@Table(name = "lastusedaccountnum")
@DynamicInsert
@DynamicUpdate

public class LastUsedAccountNum implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	

	@Column(name = "lastUsedValue")
	private long lastUsedValue ;




	public LastUsedAccountNum() {
		super();
	}



	public LastUsedAccountNum(long lastUsedValue) {
		super();
		this.lastUsedValue = lastUsedValue;
	}



	public long getLastUsedValue() {
		return lastUsedValue;
	}



	public void setLastUsedValue(long lastUsedValue) {
		this.lastUsedValue = lastUsedValue;
	}
	
	
	
	
	
}
