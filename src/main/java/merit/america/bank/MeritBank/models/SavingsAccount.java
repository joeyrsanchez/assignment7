package merit.america.bank.MeritBank.models;


public class SavingsAccount extends BankAccount {
	
	SavingsAccount() {}
	
	SavingsAccount(double balance) {
		super(balance, 1.0 / 100);
	}
	
	// Interest getter
	public double getInterestRate() {
		return super.getInterestRate();
	}


}
