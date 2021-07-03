package merit.america.bank.MeritBank.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import merit.america.bank.MeritBank.exceptions.ExceedsCombinedBalanceLimitException;
import merit.america.bank.MeritBank.exceptions.ExceedsFraudSuspicionLimitException;

@Entity
@Table(name = "accountholder")
public class AccountHolder implements Comparable<AccountHolder> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "accountholder_id")
	private long id;
	
	@NotBlank
	private String firstName;
	private String middleName;
	@NotBlank
	private String lastName;
	@NotBlank
	private String ssn;
	
	@OrderColumn
	@OneToMany(
			mappedBy = "accountHolder",
			cascade = CascadeType.ALL)
	private CheckingAccount[] checkingAccounts = new CheckingAccount[0];
	
	@OrderColumn
	@OneToMany(
			mappedBy = "accountHolder",
			cascade = CascadeType.ALL)
	private SavingsAccount[] savingsAccounts = new SavingsAccount[0];

	@OrderColumn
	@OneToMany(
			mappedBy = "accountHolder",
			cascade = CascadeType.ALL)
	private List<CDAccount> cdArray;
	
	@OneToOne
	@JoinColumn(name = "contactdetails_id", referencedColumnName = "contactdetails_id")
	private AccountHoldersContactDetails accountHoldersContactDetails;
	

	// Constructors

	public AccountHolder() {
		this.checkingAccounts = new CheckingAccount[0];
		this.savingsAccounts = new SavingsAccount[0];
		this.cdArray = new ArrayList<CDAccount>();
	}

	public AccountHolder(String firstName, String middleName, String lastName, String ssn) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.ssn = ssn;
	}

	public AccountHolder(String firstName, String middleName, String lastName, String ssn,
			double checkingAccountOpeningBalance, double savingsAccountOpeningBalance) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.ssn = ssn;
		new CheckingAccount(checkingAccountOpeningBalance);
		new SavingsAccount(savingsAccountOpeningBalance);
	}

	public int compareTo(AccountHolder otherAccountHolder) {
		double ah1, ah2;
		ah1 = this.getCombinedBalance();
		ah2 = otherAccountHolder.getCombinedBalance();

		return Double.compare(ah1, ah2);
	}

	// Account GETTERS and SETTERS

	// First name setter & getter

	protected void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	// Middle name setter & getter

	protected void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getMiddleName() {
		return middleName;
	}

	// Last name setter & getter

	protected void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	// SSN setter & getter

	protected void setSSN(String ssn) {
		this.ssn = ssn;
	}

	public String getSSN() {
		return ssn;
	}

	public CheckingAccount addCheckingAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException {
		CheckingAccount checkingAccount = new CheckingAccount(openingBalance);
		
		return addCheckingAccount(checkingAccount);
	}

	public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) throws ExceedsCombinedBalanceLimitException {
		if (checkingAccount.getBalance()+this.getCheckingBalance()+this.getSavingsBalance()<MeritBank.COMBINED_BALANCE_MAX && checkingAccount!=null) {
			CheckingAccount[] tmp = new CheckingAccount[checkingAccounts.length+1];
			for (int i = 0; i<checkingAccounts.length; i++) {
				tmp[i] = checkingAccounts[i];
			}
			tmp[checkingAccounts.length] = checkingAccount;
			checkingAccounts = tmp;
			checkingAccount.setAccountHolder(this);
			return checkingAccount;
		}
		else throw new ExceedsCombinedBalanceLimitException();
	}

	public CheckingAccount[] getCheckingAccounts() {
		return checkingAccounts;
	}

	public int getNumberOfCheckingAccounts() {
		return checkingAccounts.length;
	}

	public double getCheckingBalance() {
		double chBalance = 0.0;
		for (int i = 0; i < checkingAccounts.length; i++) {
			if (checkingAccounts[i] != null)
				chBalance += checkingAccounts[i].getBalance();
			else
				break;
		}

		return chBalance;

	}

	public SavingsAccount addSavingsAccount(double openingBalance) 
			throws ExceedsCombinedBalanceLimitException {
		
		SavingsAccount savingsAccount = new SavingsAccount(openingBalance);

		return addSavingsAccount(savingsAccount);
	}

	public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) throws ExceedsCombinedBalanceLimitException {
		if (savingsAccount.getBalance()+this.getCheckingBalance()+this.getSavingsBalance()<MeritBank.COMBINED_BALANCE_MAX && savingsAccount!=null) {
			SavingsAccount[] tmp = new SavingsAccount[savingsAccounts.length+1];
			for (int i = 0; i<savingsAccounts.length; i++) {
				tmp[i] = savingsAccounts[i];
			}
			tmp[savingsAccounts.length] = savingsAccount;
			savingsAccounts = tmp;
			savingsAccount.setAccountHolder(this);
			return savingsAccount;
		}
		else throw new ExceedsCombinedBalanceLimitException();
	}

	public SavingsAccount[] getSavingsAccounts() {
		return savingsAccounts;
	}

	public int getNumberOfSavingsAccounts() {
		return savingsAccounts.length;
	}

	public double getSavingsBalance() {
		double svBalance = 0.0;
		for (int i = 0; i < savingsAccounts.length; i++) {
			if (savingsAccounts[i] != null)
				svBalance += savingsAccounts[i].getBalance();
			else
				break;
		}
		return svBalance;
	}

	public CDAccount addCDAccount(CDOffering offering, double openingBalance)
			throws ExceedsFraudSuspicionLimitException {
		return new CDAccount(offering, openingBalance);
	}

	public CDAccount addCDAccount(CDAccount cdAccount) {
		cdAccount.setAccountHolder(this);
		cdArray.add(cdAccount);
		return cdAccount;
	}

	public List<CDAccount> getCDAccounts() {
		return cdArray;
	}

	public int getNumberOfCDAccounts() {
		return cdArray.size();
	}

	public double getCDBalance() {
		double cdBalance = 0.0;
		for(CDAccount a : cdArray) {
			cdBalance += a.getBalance();
			}

		return cdBalance;
	}

	private double getCombinedBalance() {
		return getCDBalance() + getSavingsBalance() + getCheckingBalance();
	}

}

