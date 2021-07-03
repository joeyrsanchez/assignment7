package merit.america.bank.MeritBank.models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
