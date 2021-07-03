package merit.america.bank.MeritBank.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("CD")
public class CDAccount extends BankAccount {
	
	@JoinColumn(name = "cd_offering_id", nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private CDOffering cdOffering;

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
