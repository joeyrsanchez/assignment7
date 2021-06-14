package merit.america.bank.MeritBank.models;


public class CheckingAccount extends BankAccount{

	private final static double INTEREST_RATE = 0.0001;
	
	CheckingAccount() {}
	
	CheckingAccount(double balance){
		super(balance, INTEREST_RATE);
	}

	public double getInterestRate(){
		return super.getInterestRate();
	}


}
