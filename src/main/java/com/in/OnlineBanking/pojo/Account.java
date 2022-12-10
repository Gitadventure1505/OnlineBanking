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

@NamedQuery(name = "Account.findBycustomerMail" , query = "select a from Account a where a.customer_mail=:customer_mail")



@Data
@Entity
@Table(name = "account")
@DynamicInsert
@DynamicUpdate

public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	
	@Column(name = "account_type")
	private String account_type;
	
	
	@Column(name = "customer_mail")
	private String customer_mail;
	
	
	@Column(name = "account_no")
	private long account_no;

	
	

	public Account() {
		super();
	}


	public Account(Integer id, String account_type, String customer_mail, long account_no) {
		super();
		this.id = id;
		this.account_type = account_type;
		this.customer_mail = customer_mail;
		this.account_no = account_no;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getAccount_type() {
		return account_type;
	}


	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}


	public String getCustomer_mail() {
		return customer_mail;
	}


	public void setCustomer_mail(String customer_mail) {
		this.customer_mail = customer_mail;
	}


	public long getAccount_no() {
		return account_no;
	}


	public void setAccount_no(long account_no) {
		this.account_no = account_no;
	}
	

	
	
	

	
}
