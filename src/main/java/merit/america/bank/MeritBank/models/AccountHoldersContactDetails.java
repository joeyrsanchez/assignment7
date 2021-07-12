package merit.america.bank.MeritBank.models;

import javax.persistence.*;

@Entity
@Table(name = "contact_details")
public class AccountHoldersContactDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "contactdetails_id")
	private long id;
	private String address;
	private String phone;
	
	@OneToOne (cascade = CascadeType.ALL)
	@JoinColumn(name ="accountholder_id", referencedColumnName = "accountholder_id")
	private AccountHolder accountHolder;
	
	
	// Setters and Getters
	public long getId() {return id;}
	public String getAddress() {return address;}
	public AccountHoldersContactDetails setAddress(String address) {
		this.address = address;
		return this;
	}

	public String getPhoneNumber() {return phone;}
	public AccountHoldersContactDetails setPhoneNumber(String phoneNumber) {
		this.phone = phoneNumber;
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
