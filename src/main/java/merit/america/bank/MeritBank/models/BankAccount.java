package merit.america.bank.MeritBank.models;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public abstract class BankAccount {
	
	//Instance Variables
	@Min(value = 0)
	@Max(value = 1)
	private double interestRate = 0;
	private long accountNumber;
	@Min(value = 0)
	private double balance;
	private Date openingDate = new Date();
	
	public BankAccount() {}
	
	public BankAccount(double balance, double interestRate){
		this.balance = balance;
		accountNumber = MeritBank.getNextAccountNumber();
		setInterestRate( interestRate );
	}

	public BankAccount(long accountNumber, double balance, double interestRate){
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.interestRate = interestRate;
	}

	//Getter and Setter Methods
	public void setInterestRate(double interestRate){this.interestRate = interestRate;}
	public long getOpenedOn(){return openingDate.getTime();}
	public void setOpenedOn(Date openDate){this.openingDate = openDate;}
	public void setAccountNumber(long accountNumber){this.accountNumber = accountNumber;}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}


	//Get accountnumber, balance, and interestrate
	public long getAccountNumber(){return accountNumber;}
	public double getBalance(){return balance;}
	public double getInterestRate(){return interestRate;}

	public boolean withdraw(double amount) {
		//new WithdrawTransaction( this, amount );
		
		double balanceAfterWithdrawal = balance - amount;
		if( amount > 0 && balanceAfterWithdrawal > 0 ){
			balance = balanceAfterWithdrawal;
			return true;
		}
		else{
			System.out.println( "BankAccount.withdraw: Incorrect amount or exceeding balance." );
			return false;
		}
	}

	public boolean deposit(double amount){
		if( amount > 0 ){
			balance += amount;
			return true;
		}
		else{
			System.out.println( "Can't be zero or negative." );
			return false;
		}
	}

	public double futureValue(int years){
		return MeritBank.recursiveFutureValue( getBalance(), years, getInterestRate() );
	}

}
