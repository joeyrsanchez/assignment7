package merit.america.bank.MeritBank.models;

import javax.validation.constraints.NotBlank;

public class AccountHolder implements Comparable<AccountHolder> {
	@NotBlank
	private String firstName;
	private String middleName;
	@NotBlank
	private String lastName;
	@NotBlank
	private String ssn;
	private long id;
	private CheckingAccount[] checkingAccounts = new CheckingAccount[0];
	private SavingsAccount[] savingAccounts = new SavingsAccount[0];
	private CDAccount[] cdArray = new CDAccount[0];

	// Constructors

	public AccountHolder() {}

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

//READ ACCOUNT HOLDER INFO from FILE
	public static AccountHolder readFromString(String accountHolderData) throws Exception {
		try {
			int firstCh = 0;
			int lastCh = accountHolderData.indexOf(",");
			// First name
			String fn = accountHolderData.substring(firstCh, lastCh);
			// Mid Name
			firstCh = lastCh + 1;
			lastCh = accountHolderData.indexOf(",", firstCh);
			String mn = accountHolderData.substring(firstCh, lastCh);
			// Last Name
			firstCh = lastCh + 1;
			lastCh = accountHolderData.indexOf(",", firstCh);
			String ln = accountHolderData.substring(firstCh, lastCh);
			// SSN
			firstCh = lastCh + 1;
			String sn = accountHolderData.substring(firstCh);
			AccountHolder accountHolder = new AccountHolder(fn, mn, ln, sn);

			return accountHolder;
			
		} catch (Exception e) {
			throw e;
		}
	}

	public static String writeToString() {
		int l = 0;
		String accountString = "";
		while (MeritBank.getAccountHolders()[l] != null) {
			accountString += MeritBank.getAccountHolders()[l].toString() + "\n\n";
			l++;
		}

		return accountString;
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
			SavingsAccount[] tmp = new SavingsAccount[savingAccounts.length+1];
			for (int i = 0; i<savingAccounts.length; i++) {
				tmp[i] = savingAccounts[i];
			}
			tmp[savingAccounts.length] = savingsAccount;
			savingAccounts = tmp;
			return savingsAccount;
		}
		else throw new ExceedsCombinedBalanceLimitException();
	}

	public SavingsAccount[] getSavingsAccounts() {
		return savingAccounts;
	}

	public int getNumberOfSavingsAccounts() {
		return savingAccounts.length;
	}

	public double getSavingsBalance() {
		double svBalance = 0.0;
		for (int i = 0; i < savingAccounts.length; i++) {
			if (savingAccounts[i] != null)
				svBalance += savingAccounts[i].getBalance();
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
		CDAccount[] tmp = new CDAccount[cdArray.length+1];
		for (int i = 0; i<cdArray.length; i++) {
			tmp[i] = cdArray[i];
		}
		tmp[cdArray.length] = cdAccount;
		cdArray = tmp;
		return cdAccount;
	}

	public CDAccount[] getCDAccounts() {
		return cdArray;
	}

	public int getNumberOfCDAccounts() {
		return cdArray.length;
	}

	public double getCDBalance() {
		double cdBalance = 0.0;
		for (int i = 0; i < cdArray.length; i++) {
			if (cdArray[i] != null)
				cdBalance += cdArray[i].getBalance();
			else
				break;
		}

		return cdBalance;
	}

	public double getCombinedBalance() {
		return getCDBalance() + getSavingsBalance() + getCheckingBalance();
	}

	public String toStringForFile() {
		int n = 0;
		for (int i = 0; i < getCheckingAccounts().length; i++) {
			if (getCheckingAccounts()[i] != null) {
				n++;
			}
		}
		String accountInfo = getFirstName() + "," + getMiddleName() + "," + getLastName() + "," + getSSN() + "\n" + n
				+ "\n";
		for (int i = 0; i < getCheckingAccounts().length; i++) {
			if (getCheckingAccounts()[i] != null) {
				accountInfo += getCheckingAccounts()[i].toString() + "\n";
			}
		}

		n = 0;
		for (int i = 0; i < getSavingsAccounts().length; i++) {
			if (getSavingsAccounts()[i] != null) {
				n++;
			}
		}
		accountInfo += n + "\n";

		for (int i = 0; i < getSavingsAccounts().length; i++) {
			if (getSavingsAccounts()[i] != null) {
				accountInfo += getSavingsAccounts()[i].toString() + "\n";
			}
		}

		n = 0;
		for (int i = 0; i < getCDAccounts().length; i++) {
			if (getCDAccounts()[i] != null) {
				n++;
			}
		}
		accountInfo += n + "\n";

		for (int i = 0; i < getCDAccounts().length; i++) {
			if (getCDAccounts()[i] != null) {
				accountInfo += getCDAccounts()[i].toString() + "\n";
			}
		}

		return accountInfo;
	}

//	 Outputs account info
	public String toString() {
		String accountInfo = "\n" + "Name: " + getFirstName() + " " + getMiddleName() + " " + getLastName() + "\n"
				+ "SSN: " + getSSN() + "\n" + "Combined Balance: $" + getCombinedBalance();

		return accountInfo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}

