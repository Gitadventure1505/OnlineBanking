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
	
	
	private long balance;
	
	
	private boolean isChequeBookAvailable;
	
	private boolean isRequested;
	
	
	private long chequeBookNumber;
	
	


	public AccountWrapper() {
		super();
	}





	public AccountWrapper(Integer id, String account_type, String customer_mail, long account_no, long balance,
			boolean isChequeBookAvailable, boolean isRequested, long chequeBookNumber) {
		super();
		this.id = id;
		this.account_type = account_type;
		this.customer_mail = customer_mail;
		this.account_no = account_no;
		this.balance = balance;
		this.isChequeBookAvailable = isChequeBookAvailable;
		this.isRequested = isRequested;
		this.chequeBookNumber = chequeBookNumber;
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




	public long getBalance() {
		return balance;
	}




	public void setBalance(long balance) {
		this.balance = balance;
	}








	public boolean getIsChequeBookAvailable() {
		return isChequeBookAvailable;
	}








	public void setIsChequeBookAvailable(boolean isChequeBookAvailable) {
		this.isChequeBookAvailable = isChequeBookAvailable;
	}





	public boolean isRequested() {
		return isRequested;
	}





	public void setRequested(boolean isRequested) {
		this.isRequested = isRequested;
	}





	public long getChequeBookNumber() {
		return chequeBookNumber;
	}





	public void setChequeBookNumber(long chequeBookNumber) {
		this.chequeBookNumber = chequeBookNumber;
	}

	
	

	
}


