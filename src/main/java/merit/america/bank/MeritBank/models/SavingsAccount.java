package merit.america.bank.MeritBank.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Saving")
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
