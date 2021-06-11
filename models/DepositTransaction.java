package merit.america.bank.MeritBank.models;

public class DepositTransaction extends Transaction {
	DepositTransaction(BankAccount targetAccount, double amount) {
		this.setTargetAccount(targetAccount);
		this.setAmount(amount);
	}

	@Override
	public void process()
			throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
		double amount = this.getAmount();

		if (amount > 1000)
			throw new ExceedsFraudSuspicionLimitException(this);

		if (amount < 0)
			throw new NegativeAmountException();

		this.getTargetAccount().deposit(amount);
	}
}

