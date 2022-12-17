package com.in.OnlineBanking.pojo;

import java.io.Serializable;

import javax.annotation.Nullable;
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

//@NamedQuery(name = "Account.findBycustomer_mailAndaccount_type" , query = "select a from Account a where a.customer_mail=:customer_mail, a.account_type=:account_type")



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
	private String accountType;
	
	
	@Column(name = "customer_mail")
	private String customerMail;
	
	
	@Column(name = "accountNo")
	private long accountNo;
	
	
	@Column(name = "balance")
	private long balance;
	
	
	@Column(name = "isChequeBookAvailable")
	private boolean isChequeBookAvailable;
	
	
	@Column(name = "isRequested")
	private boolean isRequested;
	
	
	
	@Column(name = "chequebooknumber")
	private long chequeBookNumber;

	
	

	public Account() {
		super();
	}

	



	public Account(Integer id, String accountType, String customerMail, long accountNo, long balance,
			boolean isChequeBookAvailable, boolean isRequested,long chequeBookNumber) {
		super();
		this.id = id;
		this.accountType = accountType;
		this.customerMail = customerMail;
		this.accountNo = accountNo;
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




	public String getAccountType() {
		return accountType;
	}




	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}




	public String getCustomerMail() {
		return customerMail;
	}




	public void setCustomerMail(String customerMail) {
		this.customerMail = customerMail;
	}




	public long getAccountNo() {
		return accountNo;
	}




	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
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





	public boolean getIsRequested() {
		return isRequested;
	}





	public void setIsRequested(boolean isRequested) {
		this.isRequested = isRequested;
	}




	
	public long getChequeBookNumber() {
		return chequeBookNumber;
	}




	
	public void setChequeBookNumber(long chequeBookNumber) {
		this.chequeBookNumber = chequeBookNumber;
	}






	
}
