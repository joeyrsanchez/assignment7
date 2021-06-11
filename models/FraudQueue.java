package merit.america.bank.MeritBank.models;

import java.util.ArrayList;

public class FraudQueue {
	private ArrayList<Transaction> q = new ArrayList<>();

	FraudQueue() {
	}

	public void addTransaction(Transaction transaction) {
		this.q.add(transaction);
	}

	public Transaction getTransaction() {
		for (Transaction t : q)
			if (!t.isProcessedByFraudTeam())
				return t;

		return null;
	}
}
