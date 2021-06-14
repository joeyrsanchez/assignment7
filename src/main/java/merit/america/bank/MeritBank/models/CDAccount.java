package merit.america.bank.MeritBank.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CDAccount extends BankAccount {
	private int term;
	private CDOffering cdOffering;
	public CDAccount (CDOffering offering, double balance) 
			throws ExceedsFraudSuspicionLimitException {
		this.setOffering( offering );
		this.setBalance( balance );
		this.setTerm( offering.getTerm() );
		this.setAccountNumber( MeritBank.getNextAccountNumber() );
		this.addTransaction( new DepositTransaction( this, balance ) );
		if( balance > MeritBank.FRAUD_LIMIT )
			throw new ExceedsFraudSuspicionLimitException( this );
	}

	public CDAccount( long accNum, double balance, 
			CDOffering offering, Date date){
		this.setOffering( offering );
		this.setBalance( balance );
		this.setTerm( offering.getTerm() );
		this.setAccountNumber( accNum );
		this.setOpeningDate( date );
		this.addTransaction( new DepositTransaction( this, balance ) );
		if( balance > MeritBank.FRAUD_LIMIT )
			MeritBank.getFraudQueue().addTransaction( this );
	}

	public void setTerm( int term) {
		this.term = term;
	}

	public int getTerm(){
		return term;
	}

	public double getInterestRate() {
		return this.getOffering().getInterestRate();
	}

	public Date getStartDate() {
		//Date date = new Date();
		//		return date;
		return this.getOpeningDate();
	}

	public Date getOpenedOn() {
		return this.getOpeningDate();
	}

	public boolean withdraw(double amount) {
		return false;
	}

	public boolean deposit(double amount) {
		return false;
	}

	public static CDAccount readFromString(String accountData) {
		int firstCh = 0;
		int lastCh = accountData.indexOf( "," );
		long accNum = Integer.parseInt( accountData.substring( firstCh, lastCh ));

		firstCh = lastCh + 1;
		lastCh = accountData.indexOf( ",", firstCh );
		double balance = Double.parseDouble( accountData.substring( firstCh, lastCh ));

		firstCh = lastCh + 1;
		lastCh = accountData.indexOf( ",", firstCh );
		double iRate = Double.parseDouble( accountData.substring( firstCh, lastCh ));

		firstCh = lastCh + 1;
		lastCh = accountData.indexOf( ",", firstCh );
		DateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );
		Date openDate = null;
		try {
			openDate = df.parse( accountData.substring( firstCh, lastCh ));
		}
		catch( ParseException e1 ) {
			e1.printStackTrace();
		}

		firstCh = lastCh + 1;
		byte term = Byte.parseByte( accountData.substring( firstCh ) );

		// CDOffering of = new CDOffering( term, iRate );

		//CDAccount cdAccount = new CDAccount( accNum, balance, new CDOffering( term,
		// iRate ), openDate );

		CDAccount cdAccount = null;
		cdAccount = new CDAccount( accNum, balance, new CDOffering( term, iRate ), openDate );

		return cdAccount;
	}

	public double futureValue(){
		return MeritBank.recursiveFutureValue( getBalance(), getTerm(), getInterestRate());
	}

	// Outputs account info
	public String toString(){
		DateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );
		String cdAccInfo = getAccountNumber() + "," + getBalance() + "," + getInterestRate() + "," + df.format( this.getStartDate() ) + ","
				+ getTerm();

		return cdAccInfo;
	}

	@Override public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, 
	ExceedsFraudSuspicionLimitException{
		// TODO Auto-generated method stub
	}

	public CDOffering getCdOffering() {
		return cdOffering;
	}
}
