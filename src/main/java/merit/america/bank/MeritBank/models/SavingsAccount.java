package merit.america.bank.MeritBank.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SavingsAccount extends BankAccount {
	SavingsAccount(double openingBalance) {
		this.setBalance(openingBalance);
		this.setInterestRate(1.0 / 100);
		this.addTransaction(new DepositTransaction(this, openingBalance));
	}

	SavingsAccount(long accNum, double balance, double interestRate, Date openDate) {
		this.setInterestRate(interestRate);
		this.setOpeningDate(openDate);
		this.setBalance(balance);
		this.setAccountNumber(accNum);
		this.addTransaction(new DepositTransaction(this, balance));
	}

	public Date getOpenedOn() {
		return this.getOpeningDate();
	}

	// Interest getter
	public double getInterestRate() {
		return super.getInterestRate();
	}

	public static SavingsAccount readFromString(String accountData) {
		try {
			int firstCh = 0;
			int lastCh = accountData.indexOf(",");
			long accNum = Integer.parseInt(accountData.substring(firstCh, lastCh));

			firstCh = lastCh + 1;
			lastCh = accountData.indexOf(",", firstCh);
			double balance = Double.parseDouble(accountData.substring(firstCh, lastCh));

			firstCh = lastCh + 1;
			lastCh = accountData.indexOf(",", firstCh);
			double iRate = Double.parseDouble(accountData.substring(firstCh, lastCh));

			firstCh = lastCh + 1;
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date openDate = df.parse(accountData.substring(firstCh));

			SavingsAccount savingsAccount = new SavingsAccount(accNum, balance, iRate, openDate);

			return savingsAccount;
		} catch (Exception e) {
			throw new NumberFormatException();
		}
	}

	// Outputs account info
	public String toString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String saveAccInfo = getAccountNumber() + "," + getBalance() + "," + getInterestRate() + ","
				+ df.format(this.getOpeningDate());
		return saveAccInfo;
	}

	@Override
	public void process()
			throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
		// TODO Auto-generated method stub

	}
}
