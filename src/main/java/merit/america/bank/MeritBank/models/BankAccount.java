package merit.america.bank.MeritBank.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;

public abstract class BankAccount extends Transaction {
	
	//Instance Variables
	private double interestRate;
	private long accountNumber;
	@Min(value = 0)
	private double balance;
	private CDOffering offering;
	private Date openingDate;
	private ArrayList< String > transactionStrings = new ArrayList<>();
	private ArrayList< Transaction > transactions = new ArrayList<>();

	//Default Constructor
	public BankAccount(){}

	public BankAccount(long accountNumber){
		setAccountNumber( accountNumber );
	}

	public BankAccount(double balance){
		setBalance( balance );
	}

	public BankAccount(CDOffering offering, double balance){
		this( balance );
		setOffering( offering );
	}

	public BankAccount(double balance, double interestRate){
		this( balance );
		setInterestRate( interestRate );
	}

	public BankAccount(long accountNumber, double balance, double interestRate){
		this( balance, interestRate );
		setAccountNumber( accountNumber );
	}

	public BankAccount(double balance, double interestRate, Date accountOpenedOn){
		this( balance, interestRate );
		setOpeningDate( accountOpenedOn );
	}

	//Getter and Setter Methods
	public void setInterestRate(double interestRate){this.interestRate = interestRate;}
	public Date getOpeningDate(){return openingDate;}
	public void setOpeningDate(Date openDate){this.openingDate = openDate;}
	public void setAccountNumber(long accountNumber){this.accountNumber = accountNumber;}
	public void setBalance(double balance){this.balance = balance;}
	public CDOffering getOffering(){return offering;}
	public void setOffering(CDOffering offering){this.offering = offering;}

	public void transactionStringAdd(String s){
		transactionStrings.add( s );
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

	public void addTransaction(Transaction transaction){
		transactions.add( transaction );
	}

	public List< Transaction > getTransactions(){
		return transactions;
	}

	public ArrayList< String > getTransactionStrings(){
		return transactionStrings;
	}
}
