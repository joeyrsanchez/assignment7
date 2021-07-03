package merit.america.bank.MeritBank.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.DiscriminatorOptions;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="account_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorOptions(force=true)
public abstract class BankAccount {
	
	//Instance Variables
	@Min(value = 0)
	@Max(value = 1)
	private double interestRate = 0;
	private long accountNumber;
	@Min(value = 0)
	private double balance;
	private Date openingDate = new Date();
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_holder_id", nullable = false)
	@JsonIgnore
	private AccountHolder accountHolder;
	
	
	public BankAccount() {}
	
	public BankAccount(double balance, double interestRate){
		this.balance = balance;
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

	public AccountHolder getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
	}

	public long getId() {
		return id;
	}

}
