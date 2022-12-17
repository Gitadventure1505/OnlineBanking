package com.in.OnlineBanking.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Data
@Entity
@Table(name = "savingsaccount")
@DynamicInsert
@DynamicUpdate

public class SavingsAccount implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	
	@Column(name = "accountnum")
	private long accountnum;
	
	
	@Column(name = "customermail")
	private String customermail;
	
	@Column(name = "transactiontype")
	private String transactiontype;
	
	
	@Column(name = "accountbalance")
	private long balance;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "transactiontime")
	private Date transactiontime;
	
	
	@PrePersist
	private void onCreate()
	{
		transactiontime = new Date();
	}
	
	@Column(name = "transferamount")
	private long transferamount;
	
	
	@Column(name = "fromaccount")
	private long fromaccount;
	
	
	@Column(name = "toaccount")
	private long toaccount;


	public SavingsAccount(Integer id, long accountnum, String customermail, String transactiontype, long balance,
			Date transactiontime, long transferamount, long fromaccount, long toaccount) {
		super();
		this.id = id;
		this.accountnum = accountnum;
		this.customermail = customermail;
		this.transactiontype = transactiontype;
		this.balance = balance;
		this.transactiontime = transactiontime;
		this.transferamount = transferamount;
		this.fromaccount = fromaccount;
		this.toaccount = toaccount;
	}


	public SavingsAccount() {
		super();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public long getAccountnum() {
		return accountnum;
	}


	public void setAccountnum(long accountnum) {
		this.accountnum = accountnum;
	}


	public String getCustomermail() {
		return customermail;
	}


	public void setCustomermail(String customermail) {
		this.customermail = customermail;
	}


	public String getTransactiontype() {
		return transactiontype;
	}


	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}


	public long getBalance() {
		return balance;
	}


	public void setBalance(long balance) {
		this.balance = balance;
	}


	public Date getTransactiontime() {
		return transactiontime;
	}


	public void setTransactiontime(Date transactiontime) {
		this.transactiontime = transactiontime;
	}


	public long getTransferamount() {
		return transferamount;
	}


	public void setTransferamount(long transferamount) {
		this.transferamount = transferamount;
	}


	public long getFromaccount() {
		return fromaccount;
	}


	public void setFromaccount(long fromaccount) {
		this.fromaccount = fromaccount;
	}


	public long getToaccount() {
		return toaccount;
	}


	public void setToaccount(long toaccount) {
		this.toaccount = toaccount;
	}
	
	
	
	
}
