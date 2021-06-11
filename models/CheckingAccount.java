package merit.america.bank.MeritBank.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckingAccount extends BankAccount{
	CheckingAccount(double openingBalance){
		this.setBalance( openingBalance );
		// this.setAmount( openingBalance );
		//DepositTransaction t = new DepositTransaction( this, openingBalance );
		this.addTransaction( new DepositTransaction( this, openingBalance ) );
	}

	CheckingAccount(long accNum, double balance, double interestRate, Date openDate){
		this.setInterestRate( interestRate );
		this.setOpeningDate( openDate );
		this.setBalance( balance );
		this.setAccountNumber( accNum );
		this.addTransaction( new DepositTransaction( this, balance ) );
	}

	public Date getOpenedOn(){
		return this.getOpeningDate();
	}

	public double getInterestRate(){
		return super.getInterestRate();
	}

	public static CheckingAccount readFromString(String accountData){
		String[] s = accountData.split( "," );

		if( s.length < 4 )
			throw new NumberFormatException();
		else { 
			// 1st column: account number:
			long accNum = Long.parseLong( s[ 0 ]);

			// 2nd column: balance
			double balance = Double.parseDouble( s[ 1 ]);

			// 3rd column: interest rate:
			double iRate = Double.parseDouble( s[ 2 ]);

			// 4th column: opening date:
			DateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );
			Date openDate = null;
			try {
				openDate = df.parse( s[ 3 ] );
			}
			catch( ParseException e ){
				e.printStackTrace();
			}

			CheckingAccount checkingAccount = new CheckingAccount( accNum, balance, iRate, openDate );

			return checkingAccount;
		}
	}

	// Outputs account info
	public String toString(){
		DateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );
		String checkAccInfo = getAccountNumber() + "," + getBalance() + "," + String.format( "%.4f", getInterestRate() ) + ","
				+ df.format( this.getOpeningDate() );
		return checkAccInfo;
	}

	@Override public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, 
	ExceedsFraudSuspicionLimitException {
		for( String s: this.getTransactionStrings())
			createTransactionFromString(s);

		for( Transaction t: this.getTransactions())
			if( t.getAmount() > MeritBank.FRAUD_LIMIT)
				throw new ExceedsFraudSuspicionLimitException(t);
	}

	public void createTransactionFromString(String transactionString){
		String[] sArray = transactionString.split( "," );

		// 1st column: Source account number:
		this.setAccountNumberSource( Long.parseLong( sArray[ 0 ] ) );
		if( this.getAccountNumberSource() < 0 )
			this.setSourceAccount( this );

		// 2nd column: Target account number:
		this.setAccountNumberSource( Long.parseLong( sArray[ 1 ] ) );
		this.setTargetAccount( MeritBank.accounts.get( this.getAccountNumber() ) );

		// 3rd column: Amount:
		this.setAmount( Double.parseDouble( sArray[ 2 ] ) );

		// 4th column: Date
		DateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );
		try{
			this.setTransactionDate( df.parse( sArray[ 3 ] ) );
		}
		catch( ParseException e ){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addTransaction( this );
	}
}
