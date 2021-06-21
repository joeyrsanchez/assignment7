package merit.america.bank.MeritBank.models;

import javax.persistence.*;

@Entity
@Table(name = "cd_accounts", catalog = "assignment6")
public class CDAccount extends BankAccount {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "cdaccount_id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name ="cdaccount_id", referencedColumnName = "cdoffering_id")
	private CDOffering cdOffering;

	@ManyToOne
	@JoinColumn(name = "cdaccount_id", referencedColumnName = "accountholder_id")
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
