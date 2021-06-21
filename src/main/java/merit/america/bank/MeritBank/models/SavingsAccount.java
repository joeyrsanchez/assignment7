package merit.america.bank.MeritBank.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "savings_accounts", catalog = "assignment6")
public class SavingsAccount extends BankAccount {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "savingsaccount_id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "accountholder_id", referencedColumnName = "accountholder_id")
	private AccountHolder accountHolder;
	
	SavingsAccount() {}
	
	SavingsAccount(double balance) {
		super(balance, 1.0 / 100);
	}
	
	// Interest getter
	public double getInterestRate() {
		return super.getInterestRate();
	}


}
