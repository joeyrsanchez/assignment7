package merit.america.bank.MeritBank.models;

public class CDAccountHelper {
	private double balance;
	private CDOfferHelper cdOffering;
	
	CDAccountHelper() {}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public CDOfferHelper getCdOffering() {
		return cdOffering;
	}

	public void setCdOffering(CDOfferHelper cdOffering) {
		this.cdOffering = cdOffering;
	}

}

