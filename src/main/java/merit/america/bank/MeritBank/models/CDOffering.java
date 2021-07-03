package merit.america.bank.MeritBank.models;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "cd_offerings")
public class CDOffering {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cdoffering_id")
	private long id;
	
	@Min (value = 0)
	@Max (value = 1)
	private double interestRate;
	@Min (value = 1)
	private int term;
	
	@OneToMany(
			mappedBy ="cdOffering",
			cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CDAccount> cdAccount;
	
	public CDOffering setCdAccount(List<CDAccount> cdAccount) {
		this.cdAccount = cdAccount;
		return this;
	}
	
	CDOffering() {}
	
	public CDOffering(int term, double interestRate) {
		this.term = term;
		this.interestRate = interestRate;
	}

	public int getTerm() {
		return term;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public long getId() {
		return id;
	}
}
