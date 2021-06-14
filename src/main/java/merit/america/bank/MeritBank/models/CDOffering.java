package merit.america.bank.MeritBank.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class CDOffering {
	@Min (value = 0)
	@Max (value = 1)
	private double interestRate;
	@Min (value = 1)
	private int term;
	private long id;
	
	CDOffering() {}
	
	public CDOffering(int term, double interestRate) {
		this.term = term;
		this.interestRate = interestRate;
	}

	public static CDOffering readFromString(String cdOfferingDataString) {
		String[] sArray = cdOfferingDataString.split(",");
		if (sArray.length < 2)
			throw new NumberFormatException();

		byte term;
		try {
			term = Byte.parseByte(sArray[0]);
		} catch (NumberFormatException e) {
			throw e;
		}

		double interestRate;
		try {
			interestRate = Double.parseDouble(sArray[1]);
		} catch (NumberFormatException e) {
			throw e;
		}

		CDOffering offering = new CDOffering(term, interestRate);
		return offering;
	}

	public int getTerm() {
		return term;
	}

	public double getInterestRate() {
		return interestRate;
	}

//	// Outputs account info
	public String toString() {
		String info = getTerm() + "," + getInterestRate() + "\n";

		return info;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
