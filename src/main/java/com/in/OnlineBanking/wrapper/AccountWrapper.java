package com.in.OnlineBanking.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountWrapper {
	

	private Integer id;
	

	private String account_type;
	

	private String customer_mail;
	
	
	private long account_no;
	
	
	


	public AccountWrapper() {
		super();
	}


	public AccountWrapper(Integer id, String account_type, String customer_mail, long account_no) {
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


