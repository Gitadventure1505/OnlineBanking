package com.in.OnlineBanking.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.in.OnlineBanking.constants.OnlineBankingConstants;
import com.in.OnlineBanking.dao.AccountDao;
import com.in.OnlineBanking.dao.LastUsedAccountNumDao;
import com.in.OnlineBanking.dao.PrimaryAccountDao;
import com.in.OnlineBanking.dao.SavingsAccountDao;
import com.in.OnlineBanking.jwt.CustomerUserDetailsService;
import com.in.OnlineBanking.jwt.JwtFilter;
import com.in.OnlineBanking.jwt.JwtUtil;
import com.in.OnlineBanking.pojo.Account;
import com.in.OnlineBanking.pojo.LastUsedAccountNum;
import com.in.OnlineBanking.pojo.PrimaryAccount;
import com.in.OnlineBanking.pojo.SavingsAccount;
import com.in.OnlineBanking.pojo.User;
import com.in.OnlineBanking.service.AccountService;
import com.in.OnlineBanking.utils.OnlineBankingUtils;
import com.in.OnlineBanking.wrapper.AccountWrapper;
import com.in.OnlineBanking.wrapper.UserWrapper;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AccountDao accountDao;
	
	@Autowired
	LastUsedAccountNumDao lastUsedAccountNumDao;
	
	@Autowired
	JwtFilter jwtFilter;
	
	@Autowired
	PrimaryAccountDao primaryAccountDao;
	

	@Autowired
	SavingsAccountDao savingsAccountDao;
	
	

	
	
	@Autowired
	CustomerUserDetailsService customerUserDetailsService;
	
	
	
	@Override
	public ResponseEntity<String> addaccount(Map<String, String> requestMap) {
         log.info("Inside addaccount{}", requestMap);
		
		try
		{
			Account	account = new Account();
			account.setAccountType(requestMap.get("accountType")); 
			account.setCustomerMail(requestMap.get("customerMail")); 
			account.setBalance(1000l); 
			
			
			LastUsedAccountNum lastAccountNum = lastUsedAccountNumDao.findById();
			long value = lastAccountNum.getLastUsedValue();
			value = value+1;
			System.out.println(value);
			
			lastAccountNum.setLastUsedValue(value);
			lastUsedAccountNumDao.save(lastAccountNum);
			
			
			account.setAccountNo(value);
			accountDao.save(account);
			return OnlineBankingUtils.getResponseEntity("Successfully Account added", HttpStatus.OK);				
 
			
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return OnlineBankingUtils.getResponseEntity(OnlineBankingConstants.Something_Went_Wrong, HttpStatus.BAD_REQUEST);
		
	}


	
	@Override
	public ResponseEntity<List<Account>> getAccountsByMail(String customer_mail,String account_type) {try
	{
		if(jwtFilter.isAdmin())
		{
			//accountDao.findAllBycustomer_mailAndaccount_type(customer_mail,account_type);
			System.out.println(customer_mail+account_type);
			//return new ResponseEntity<List<Account>>(accountDao.findBycustomer_mailAndaccount_type(customer_mail, account_type), HttpStatus.OK);
			//return new ResponseEntity<List<Account>>(accountDao.findAllBycustomer_mailAndaccount_type(customer_mail,account_type), HttpStatus.OK);
			//accountDao.findAllByCustomerMailAndAccountType(customer_mail, account_type);
			//return new ResponseEntity<List<Account>>(accountDao.findAllByCustomerMailAndAccountType(customer_mail, account_type), HttpStatus.OK);
			
			
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
	public ResponseEntity<Account> getAccountsByMailAndType(String customer_mail, String account_type) {
		try
		{
			String Username =  customerUserDetailsService.loadUserByUsername(customer_mail).getUsername();
			String CurrentUser = jwtFilter.getCurrentUser();
			
			System.out.println(Username+CurrentUser);
			
			log.info("Inside getAccountsByMailAndType()");
			
			if(Username.equalsIgnoreCase(CurrentUser) || jwtFilter.isAdmin())
			{
				
				System.out.println(customer_mail+account_type);
			
				
				Account account = accountDao.findAllByCustomerMailAndAccountType(customer_mail, account_type);
				if(Objects.isNull(account))
				{
					Account empaccount = new Account();
					empaccount.setId(0);
					
					return new ResponseEntity<Account>(empaccount, HttpStatus.OK);
				}
				else
				{
					return new ResponseEntity<Account>(account, HttpStatus.OK);
				}
				
				
				
				
			}
			else
			{
				return new ResponseEntity<>(new Account(), HttpStatus.UNAUTHORIZED);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(new Account(), HttpStatus.INTERNAL_SERVER_ERROR);
		}



	@Override
	public ResponseEntity<String> primaryAccountTransaction(PrimaryAccount primaryAccount) {
		try {
			
			PrimaryAccount saveTransaction = new PrimaryAccount();
			saveTransaction.setAccountnum(primaryAccount.getAccountnum());
			saveTransaction.setCustomermail(primaryAccount.getCustomermail());
			saveTransaction.setFromaccount(primaryAccount.getFromaccount());
			saveTransaction.setToaccount(primaryAccount.getToaccount());
			saveTransaction.setTransactiontype(primaryAccount.getTransactiontype());
			saveTransaction.setTransferamount(primaryAccount.getTransferamount());
			
			if(primaryAccount.getTransactiontype().equalsIgnoreCase("transfer"))
			{
				//getting current balance from primary account 
				Account currentAccount =  accountDao.findAllByCustomerMailAndAccountType(primaryAccount.getCustomermail(), "primary");
				long currentBalance = currentAccount.getBalance();
				if(currentBalance>=primaryAccount.getTransferamount())
				{
				
				//getting current balance from  Receivers account
				Account receiverAccount = accountDao.findByAccountNo(primaryAccount.getToaccount());
				long receiverBalance =  receiverAccount.getBalance();
				System.out.println("orignal balance" + receiverBalance );
				
				
				
				
			
				//Total balance in primary ACCOUNT AFTER transaction
				currentBalance = currentBalance-primaryAccount.getTransferamount();
				
				//Total balance in RECEIVER ACCOUNT AFTER TRANSACTION
				receiverBalance = receiverBalance+primaryAccount.getTransferamount();
				System.out.println("after transaction balance" + receiverBalance );
				
			
				
				
		
				//setting from & to account
				//saveTransaction.setFromaccount(primaryAccount.getFromaccount());
				//saveTransaction.setToaccount(primaryAccount.getToaccount());
				
	
				
				
				
				//Setting balance in primaryAccountTransaction and CURRENTAccount 
				saveTransaction.setBalance(currentBalance);
				currentAccount.setBalance(currentBalance);
									
				//setting balance in Receiver Account
				receiverAccount.setBalance(receiverBalance);
				
				
				
				
				
				
				//saving entities related to primary account and its primarytransactionHistory, receiveraccount
				primaryAccountDao.save(saveTransaction);
				accountDao.save(currentAccount);
				accountDao.save(receiverAccount);
				
				
				
				
				
				//creating transaction in receivers history
				String receiverAccountType = receiverAccount.getAccountType();
				if(receiverAccountType.equalsIgnoreCase("primary"))
				{
					PrimaryAccount receiverPrimaryAccount = new PrimaryAccount();
					//receiverPrimaryAccount = saveTransaction;				
					receiverPrimaryAccount.setAccountnum(receiverAccount.getAccountNo());
					receiverPrimaryAccount.setCustomermail(receiverAccount.getCustomerMail());
					receiverPrimaryAccount.setFromaccount(saveTransaction.getFromaccount());
					receiverPrimaryAccount.setToaccount(saveTransaction.getToaccount());
					receiverPrimaryAccount.setTransactiontype("credited from AccountNum "+ saveTransaction.getFromaccount() +"and his/her Mail id is "+saveTransaction.getCustomermail());
					receiverPrimaryAccount.setBalance(receiverBalance);
					receiverPrimaryAccount.setTransferamount(saveTransaction.getTransferamount());
					primaryAccountDao.save(receiverPrimaryAccount);
					
					
				}
				else 
					if(receiverAccountType.equalsIgnoreCase("savings"))
					{
						SavingsAccount receiverSavingsAccount = new SavingsAccount();
						receiverSavingsAccount.setAccountnum(receiverAccount.getAccountNo());
						receiverSavingsAccount.setCustomermail(receiverAccount.getCustomerMail());
						receiverSavingsAccount.setFromaccount(saveTransaction.getFromaccount());
						receiverSavingsAccount.setToaccount(saveTransaction.getToaccount());
						receiverSavingsAccount.setTransactiontype("credited from AccountNum "+ saveTransaction.getFromaccount() +"and his/her Mail id is "+saveTransaction.getCustomermail() );
						receiverSavingsAccount.setTransferamount(saveTransaction.getTransferamount());
						receiverSavingsAccount.setBalance(receiverBalance);
						savingsAccountDao.save(receiverSavingsAccount);
						
					}
					else
					{
					
						return OnlineBankingUtils.getResponseEntity("Failure in adding the transaction history row in receivers account", HttpStatus.EXPECTATION_FAILED);
						
					}
				
				
				
				
				String responseMessage = "Cash Transferred from your account to "+ receiverAccount.getCustomerMail() +" successfully, Your current balance is : " + currentBalance;
			
				return OnlineBankingUtils.getResponseEntity(responseMessage, HttpStatus.OK);
			
			}
			else
			{
				return OnlineBankingUtils.getResponseEntity("Insufficient balance to make this transaction", HttpStatus.EXPECTATION_FAILED);
			}
				
			}else if(primaryAccount.getTransactiontype().equalsIgnoreCase("withdraw"))
			{
				//getting current balance from primary account
				Account currentAccount =  accountDao.findAllByCustomerMailAndAccountType(primaryAccount.getCustomermail(), "primary");
				long currentBalance = currentAccount.getBalance();
				
				if(currentBalance>=primaryAccount.getTransferamount()) {
					//Total balance
					currentBalance = currentBalance-primaryAccount.getTransferamount();
					
					//Setting balance in primaryAccountTransaction and Account
					saveTransaction.setBalance(currentBalance);
					currentAccount.setBalance(currentBalance);
					
					//saving entities
					primaryAccountDao.save(saveTransaction);
					accountDao.save(currentAccount);
					
					String responseMessage = "Cash withdrawn from your account successfully, Your current balance is : " + currentBalance;
					return OnlineBankingUtils.getResponseEntity(responseMessage, HttpStatus.OK);

				}
				else
				{
					return OnlineBankingUtils.getResponseEntity("Insufficient balance to make this transaction", HttpStatus.EXPECTATION_FAILED);
				}
				
			}else
				if(primaryAccount.getTransactiontype().equalsIgnoreCase("deposit"))
				{
					//getting current balance from primary account
					Account currentAccount =  accountDao.findAllByCustomerMailAndAccountType(primaryAccount.getCustomermail(), "primary");
					long currentBalance = currentAccount.getBalance();
					
					//Total balance
					currentBalance = currentBalance+primaryAccount.getTransferamount();
					
					//Setting balance in primary and Account
					saveTransaction.setBalance(currentBalance);
					currentAccount.setBalance(currentBalance);
					
					//saving entities
					primaryAccountDao.save(saveTransaction);
					accountDao.save(currentAccount);
					
					String responseMessage = "Cash Deposit to your account successful, Your current balance is : " + currentBalance;
					return OnlineBankingUtils.getResponseEntity(responseMessage, HttpStatus.OK);
					
				}else 
					if(primaryAccount.getTransactiontype().equalsIgnoreCase("creation"))
					{
						saveTransaction.setBalance(1000);
						primaryAccountDao.save(saveTransaction);
						
						return new ResponseEntity("Account creation is successful", HttpStatus.OK);
					}else
					{
						return OnlineBankingUtils.getResponseEntity("No exception, something went wrong,Transaction is not successful, contact neary bank", HttpStatus.BAD_REQUEST);
					}
		
			
		
	}
	catch(Exception e)
	{
		log.info("inside primaryAccountTransaction()");
		e.printStackTrace();
	}
	
	return new ResponseEntity("Transaction is not successful", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
	
	
	
	
	@Override
	public ResponseEntity<String> savingsAccountTransaction(SavingsAccount savingsAccount) {
		try {
			
			SavingsAccount saveTransaction = new SavingsAccount();
			saveTransaction.setAccountnum(savingsAccount.getAccountnum());
			saveTransaction.setCustomermail(savingsAccount.getCustomermail());
			saveTransaction.setFromaccount(savingsAccount.getFromaccount());
			saveTransaction.setToaccount(savingsAccount.getToaccount());
			saveTransaction.setTransactiontype(savingsAccount.getTransactiontype());
			saveTransaction.setTransferamount(savingsAccount.getTransferamount());
			
			if(savingsAccount.getTransactiontype().equalsIgnoreCase("transfer"))
			{
				//getting current balance from savingsAccount  
				Account currentAccount =  accountDao.findAllByCustomerMailAndAccountType(savingsAccount.getCustomermail(), "savings");
				long currentBalance = currentAccount.getBalance();
				if(currentBalance>=savingsAccount.getTransferamount())
				{
				
				//getting current balance from  Receivers account
				Account receiverAccount = accountDao.findByAccountNo(savingsAccount.getToaccount());
				long receiverBalance =  receiverAccount.getBalance();
				System.out.println("orignal balance" + receiverBalance );
				
				
				
				
			
				//Total balance in savingsAccount  AFTER transaction
				currentBalance = currentBalance-savingsAccount.getTransferamount();
				
				//Total balance in RECEIVER ACCOUNT AFTER TRANSACTION
				receiverBalance = receiverBalance+savingsAccount.getTransferamount();
				System.out.println("after transaction balance" + receiverBalance );
				
			

				
				//Setting balance in savingsAccount Transaction and CURRENTAccount 
				saveTransaction.setBalance(currentBalance);
				currentAccount.setBalance(currentBalance);
									
				//setting balance in Receiver Account
				receiverAccount.setBalance(receiverBalance);
				
				
				
				
				
				
				//saving entities related to savingsAccount  and its savingsAccount History, receiveraccount
				savingsAccountDao.save(saveTransaction);
				accountDao.save(currentAccount);
				accountDao.save(receiverAccount);
				
				
				
				//
				
				//creating transaction in receivers history
				String receiverAccountType = receiverAccount.getAccountType();
				if(receiverAccountType.equalsIgnoreCase("savings"))
				{
					SavingsAccount receiverSavingsAccount = new SavingsAccount();
					//receiverPrimaryAccount = saveTransaction;				
					receiverSavingsAccount.setAccountnum(receiverAccount.getAccountNo());
					receiverSavingsAccount.setCustomermail(receiverAccount.getCustomerMail());
					receiverSavingsAccount.setFromaccount(saveTransaction.getFromaccount());
					receiverSavingsAccount.setToaccount(saveTransaction.getToaccount());
					receiverSavingsAccount.setTransactiontype("credited from AccountNum "+ saveTransaction.getFromaccount() +"and his/her Mail id is "+saveTransaction.getCustomermail());
					receiverSavingsAccount.setBalance(receiverBalance);
					receiverSavingsAccount.setTransferamount(saveTransaction.getTransferamount());
					savingsAccountDao.save(receiverSavingsAccount);
					
					
				}
				else 
					if(receiverAccountType.equalsIgnoreCase("primary"))
					{
						PrimaryAccount receiverprimaryAccount = new PrimaryAccount();
						receiverprimaryAccount.setAccountnum(receiverAccount.getAccountNo());
						receiverprimaryAccount.setCustomermail(receiverAccount.getCustomerMail());
						receiverprimaryAccount.setFromaccount(saveTransaction.getFromaccount());
						receiverprimaryAccount.setToaccount(saveTransaction.getToaccount());
						receiverprimaryAccount.setTransactiontype("credited from AccountNum "+ saveTransaction.getFromaccount() +"and his/her Mail id is "+saveTransaction.getCustomermail() );
						receiverprimaryAccount.setTransferamount(saveTransaction.getTransferamount());
						receiverprimaryAccount.setBalance(receiverBalance);
						primaryAccountDao.save(receiverprimaryAccount);
						
					}
					else
					{
					
						return OnlineBankingUtils.getResponseEntity("Failure in adding the transaction history row in receivers account", HttpStatus.EXPECTATION_FAILED);
						
					}
				
				
				
				
				String responseMessage = "Cash Transferred from your account to "+ receiverAccount.getCustomerMail() +" successfully, Your current balance is : " + currentBalance;
			
				return OnlineBankingUtils.getResponseEntity(responseMessage, HttpStatus.OK);
			
			}
			else
			{
				return OnlineBankingUtils.getResponseEntity("Insufficient balance to make this transaction", HttpStatus.EXPECTATION_FAILED);
			}
				
			}else if(savingsAccount.getTransactiontype().equalsIgnoreCase("withdraw"))
			{
				//getting current balance from savingsAccount 
				Account currentAccount =  accountDao.findAllByCustomerMailAndAccountType(savingsAccount.getCustomermail(), "savings");
				long currentBalance = currentAccount.getBalance();
		
				if(currentBalance>=savingsAccount.getTransferamount()) {
				
				//Total balance
				currentBalance = currentBalance-savingsAccount.getTransferamount();
				
				//Setting balance in savingsAccountTransaction and Account
				saveTransaction.setBalance(currentBalance);
				currentAccount.setBalance(currentBalance);
				
				//saving entities
				savingsAccountDao.save(saveTransaction);
				accountDao.save(currentAccount);
				
				String responseMessage = "Cash withdrawn from your account successfully, Your current balance is : " + currentBalance;
				return OnlineBankingUtils.getResponseEntity(responseMessage, HttpStatus.OK);
				}
				else
				{
					return OnlineBankingUtils.getResponseEntity("Insufficient balance to make this transaction", HttpStatus.EXPECTATION_FAILED);
				}
				
			}else
				if(savingsAccount.getTransactiontype().equalsIgnoreCase("deposit"))
				{
					//getting current balance from savingsAccount 
					Account currentAccount =  accountDao.findAllByCustomerMailAndAccountType(savingsAccount.getCustomermail(), "savings");
					long currentBalance = currentAccount.getBalance();
					
					//Total balance
					currentBalance = currentBalance+savingsAccount.getTransferamount();
					
					//Setting balance in savingsAccount and Account
					saveTransaction.setBalance(currentBalance);
					currentAccount.setBalance(currentBalance);
					
					//saving entities
					savingsAccountDao.save(saveTransaction);
					accountDao.save(currentAccount);
					
					String responseMessage = "Cash Deposit to your account successful, Your current balance is : " + currentBalance;
					return OnlineBankingUtils.getResponseEntity(responseMessage, HttpStatus.OK);
					
				}else 
					if(savingsAccount.getTransactiontype().equalsIgnoreCase("creation"))
					{
						saveTransaction.setBalance(1000);
						savingsAccountDao.save(saveTransaction);
						
						return new ResponseEntity("Account creation is successful", HttpStatus.OK);
					}else
					{
						return OnlineBankingUtils.getResponseEntity("No exception, something went wrong,Transaction is not successful, contact neary bank", HttpStatus.BAD_REQUEST);
					}
		
			
		
	}
	catch(Exception e)
	{
		log.info("inside savingsAccountTransaction()");
		e.printStackTrace();
	}
	
	return new ResponseEntity("Transaction is not successful", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	



	@Override
	public ResponseEntity<List<PrimaryAccount>> getTransactions(long accountNum, String accountType) {
		try
		{
			
			return new ResponseEntity<List<PrimaryAccount>>(primaryAccountDao.findAllByAccountnum(accountNum), HttpStatus.OK);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return new ResponseEntity<>(new ArrayList(), HttpStatus.INTERNAL_SERVER_ERROR);
	}



	@Override
	public ResponseEntity<List<SavingsAccount>> getSavingsTransactions(long accountNum) {
		try
		{
			
			return new ResponseEntity<List<SavingsAccount>>(savingsAccountDao.findAllByAccountnum(accountNum), HttpStatus.OK);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return new ResponseEntity<>(new ArrayList(), HttpStatus.INTERNAL_SERVER_ERROR);
	}



	@Override
	public ResponseEntity<List<Account>> getAccountsByIsRequested() {
		
		try {
			  List<Account> AccountsRequested =   accountDao.findAllByisRequested(true);
			  return new ResponseEntity<>(new ArrayList(), HttpStatus.OK);
		}
		catch(Exception e)
		{
			log.info("Inside catch block of getAccountsByIsRequested()");
			System.out.println(e);
		}
		
		return new ResponseEntity<>(new ArrayList(), HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
	}


