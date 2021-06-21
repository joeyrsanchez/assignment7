package merit.america.bank.MeritBank.models;

import javax.persistence.*;


@Table(name = "accountholders_log", catalog = "test")
@Entity
public class AccountHoldersContactDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "contactdetails_id")
	private Integer id;
	private String address;
	private long phoneNumber;
	
	@OneToOne (cascade = CascadeType.ALL)
	@JoinColumn(name ="id", referencedColumnName = "id")
	private AccountHolder accountHolder;
	
	
	// Setters and Getters
	public Integer getId() {return id;}
	public String getAddress() {return address;}
	public AccountHoldersContactDetails setAddress(String address) {
		this.address = address;
		return this;
	}

	public long getPhoneNumber() {return phoneNumber;}
	public AccountHoldersContactDetails setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}
	
	public AccountHolder getAccountHolder() {return accountHolder;}
	public AccountHoldersContactDetails setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
		return this;
	}

	
	//Default Constructor
	public AccountHoldersContactDetails() {
		
	}


	
}
