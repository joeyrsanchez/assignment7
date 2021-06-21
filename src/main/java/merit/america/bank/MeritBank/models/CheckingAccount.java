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
@Table(name = "checking_accounts", catalog = "assignment6")
public class CheckingAccount extends BankAccount{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "checkingaccount_id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "accountholder_id", referencedColumnName = "accountholder_id")
	private AccountHolder accountHolder;

	
	private final static double INTEREST_RATE = 0.0001;
	
	CheckingAccount() {}
	
	CheckingAccount(double balance){
		super(balance, INTEREST_RATE);
	}

	public double getInterestRate(){
		return super.getInterestRate();
	}


}
