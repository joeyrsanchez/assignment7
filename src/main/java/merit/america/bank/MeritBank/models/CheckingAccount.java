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
@DiscriminatorValue("Checking")
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
