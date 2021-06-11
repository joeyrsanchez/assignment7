package merit.america.bank.MeritBank.models;

public class WithdrawTransaction extends Transaction {
	WithdrawTransaction(BankAccount targetAccount, double amount) {
		this.setTargetAccount(targetAccount);
		this.setAmount(amount);
	}

	@Override
	public void process()
			throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
		double amount = this.getAmount();

		BankAccount target = this.getTargetAccount();

		if (amount > 1000)
			throw new ExceedsFraudSuspicionLimitException(this);

		if (amount < 0)
			throw new NegativeAmountException();

		double balanceAfterWithdrawal = target.getBalance() - amount;
		if (balanceAfterWithdrawal < 0)
			throw new ExceedsAvailableBalanceException();

		target.withdraw(amount);
	}
}

