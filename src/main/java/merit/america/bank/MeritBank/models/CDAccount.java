package merit.america.bank.MeritBank.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CDAccount extends BankAccount {
	
	@ManyToOne
	@JoinColumn(name ="cdOffering_id", referencedColumnName = "cdOffering_id")
	private CDOffering cdOffering;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "account_id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id", referencedColumnName = "id")
	private AccountHolder accountHolder;
	
	public CDAccount() {}
	
	public CDAccount (CDOffering offering, double balance)  {
		super(balance, offering.getInterestRate());
		this.cdOffering = offering;
	}

	public boolean withdraw(double amount) {
		return false;
	}

	public boolean deposit(double amount) {
		return false;
	}

	public CDOffering getCdOffering() {
		return cdOffering;
	}
}
