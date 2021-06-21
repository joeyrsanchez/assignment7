package merit.america.bank.MeritBank.models;

import java.util.Arrays;
import java.util.HashMap;

public class MeritBank {
	public static final double FRAUD_LIMIT = 1000;

	public static final double COMBINED_BALANCE_MAX = 250000;

	public static HashMap<Long, BankAccount> accounts = new HashMap<>();

	public static AccountHolder[] accountHolders = new AccountHolder[0];
	public static CDOffering[] cdOfferings = new CDOffering[0];
	public static CDOffering offering;


	public static double recursivePower(double base, int exponent) {
		if (exponent == 0)
			return 1;
		else
			return base * recursivePower(base, (exponent - 1));
	}


	public static double recursiveFutureValue(double amount, int years, double interestRate) {
		return amount * recursivePower((1 + interestRate), years);
	}

	public static BankAccount getBankAccount(long accountID) // i. Return null if account not found
	{
		if (accounts.containsKey(accountID))
			return accounts.get(accountID);

		return null;
	}

	static AccountHolder[] sortAccountHolders() {
		int l = 0;
		while (accountHolders[l] != null) {
			l++;
		}
		Arrays.sort(accountHolders, 0, l);

		return accountHolders;
	}
		
	////////////////////////////////////////////////////////////////////////////////////////////////////
	 public static void addAccountHolder(AccountHolder accountHolder) {
	        AccountHolder[] tmp = new AccountHolder[accountHolders.length+1];
	        for (int i = 0; i<accountHolders.length; i++) {
	            tmp[i] = accountHolders[i];
	        }
	        tmp[accountHolders.length] = accountHolder;
	        accountHolders = tmp;
	    }

	public static AccountHolder[] getAccountHolders() {
		return accountHolders;
	}

	public static CDOffering[] getCDOfferings() {
		return cdOfferings;
	}

	public static CDOffering getBestCDOffering(double depositAmount) {
		if (cdOfferings != null) {
			double val = 0.0;
			int j = 0;
			for (int i = 0; i < cdOfferings.length; i++) {
				if ((double) recursiveFutureValue(depositAmount, cdOfferings[i].getTerm(),
						cdOfferings[i].getInterestRate()) > val) {
					val = (double) recursiveFutureValue(depositAmount, cdOfferings[i].getTerm(),
							cdOfferings[i].getInterestRate());
					j = i;
				}
			}
			return cdOfferings[j];
		} else
			return null;
	}

	public static CDOffering getSecondBestCDOffering(double depositAmount) {
		if (cdOfferings != null) {
			double val = 0.0;
			int j = 0;
			int k = 0;
			for (int i = 0; i < cdOfferings.length; i++) {
				if ((double) recursiveFutureValue(depositAmount, cdOfferings[i].getTerm(),
						cdOfferings[i].getInterestRate()) > val) {
					val = (double) recursiveFutureValue(depositAmount, cdOfferings[i].getTerm(),
							cdOfferings[i].getInterestRate());
					j = i;
					k = j;
				}
			}

			return cdOfferings[k];
		} else
			return null;
	}

	public static void clearCDOfferings() {
		cdOfferings = null;
	}

	public static void setCDOfferings(CDOffering[] offerings) {
		cdOfferings = offerings;
	}
	
	public static void addCDO(CDOffering cdOffering) {
		CDOffering[] tmp = new CDOffering[cdOfferings.length+1];
		for(int i = 0; i < cdOfferings.length; i++) {
			tmp[i] = cdOfferings[i];
		}
		tmp[cdOfferings.length] = cdOffering;
		MeritBank.cdOfferings = tmp;
	}
	
	public static CDOffering getCDOffering(long id) {
		for (CDOffering ach : cdOfferings) {
			if (id == ach.getId())
				return ach;		
		}
		return null;
	}
}
