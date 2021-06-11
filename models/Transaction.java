package merit.america.bank.MeritBank.models;

import java.util.Date;

public abstract class Transaction {
	
	//Instance Variables
	private Date date;
	private BankAccount targetAccount;
	private BankAccount sourceAccount;
	private double amount;
	private long accountNumberSource;
	private boolean processed;

	public boolean isProcessedByFraudTeam() {
		return this.processed;
	}

	//Getter and Setter Methods
	public void setProcessed(boolean processed) {this.processed = processed;}
	public long getAccountNumberSource() {return accountNumberSource;}
	public void setAccountNumberSource(long accountNumberSource) {this.accountNumberSource = accountNumberSource;}
	public BankAccount getSourceAccount() {return sourceAccount;}
	public void setSourceAccount(BankAccount sourceAccount) {this.sourceAccount = sourceAccount;}
	public BankAccount getTargetAccount() {return targetAccount;}
	public void setTargetAccount(BankAccount targetAccount) {this.targetAccount = targetAccount;}
	public double getAmount() {return amount;}
	public void setAmount(double amount) {this.amount = amount;}
	public Date getTransactionDate() {return date;}
	public void setTransactionDate(Date date) {this.date = date;}

	public String writeToString() {return null;}

	public static Transaction readFromString(String transactionDataString) {
		return null;
	}

	public abstract void process()
			throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException;

	public void setProcessedByFraudTeam(boolean isProcessed) {
	}

	public String getRejectionReason() {
		return null;
	}

	public void setRejectionReason(String reason) {
	}
}
