package merit.america.bank.MeritBank.models;

public class ExceedsFraudSuspicionLimitException extends Exception {
	ExceedsFraudSuspicionLimitException(Transaction t) {
		MeritBank.getFraudQueue().addTransaction(t);
	}

	private static final long serialVersionUID = 1L;
}
