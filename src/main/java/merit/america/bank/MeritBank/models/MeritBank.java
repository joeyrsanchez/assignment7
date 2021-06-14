package merit.america.bank.MeritBank.models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MeritBank {
	public static final double FRAUD_LIMIT = 1000;

	public static final double COMBINED_BALANCE_MAX = 250000;

	public static HashMap<Long, BankAccount> accounts = new HashMap<>();

	public static long accountNumber = 1;
	public static long accountHolderNumber = 1;
	public static long cdoNumber = 1;
	public static AccountHolder[] accountHolders = new AccountHolder[0];
	public static CDOffering[] cdOfferings = new CDOffering[0];
	public static CDOffering offering;

	private static FraudQueue fraudQ = new FraudQueue();

	private static ArrayList<String> fraudQueueStrings = new ArrayList<>();

	public static double recursivePower(double base, int exponent) {
		if (exponent == 0)
			return 1;
		else
			return base * recursivePower(base, (exponent - 1));
	}


	public static double recursiveFutureValue(double amount, int years, double interestRate) {
		return amount * recursivePower((1 + interestRate), years);
	}


	public static boolean processTransaction(Transaction transaction)
			throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
		transaction.process();
		return true;
	}

	public static FraudQueue getFraudQueue() {
		return fraudQ;
	}

	public static BankAccount getBankAccount(long accountID) // i. Return null if account not found
	{
		if (accounts.containsKey(accountID))
			return accounts.get(accountID);

		return null;
	}



	static boolean readFromFile(String fileName) {
		accountHolders = new AccountHolder[1];
		cdOfferings = new CDOffering[0];
		try (BufferedReader rd = new BufferedReader(new FileReader(fileName))) {
			MeritBank.setNextAccountNumber(Long.parseLong(rd.readLine()));

// Read CDOffers
			int numOfCDOfferings = Integer.parseInt(rd.readLine());
			int n = 0;
			cdOfferings = new CDOffering[numOfCDOfferings];

			while (numOfCDOfferings > 0) {
				cdOfferings[n] = CDOffering.readFromString(rd.readLine());
				n++;
				numOfCDOfferings--;
			}

//Read Account Holders
			int numOfAccountHolders = Integer.parseInt(rd.readLine());
			AccountHolder ah;
			while (numOfAccountHolders > 0) {
				ah = AccountHolder.readFromString(rd.readLine());
				addAccountHolder(ah);
				readCheckingAccounts(rd, ah);
				readSavingsAccounts(rd, ah);
				readCDAccounts(rd, ah);
				numOfAccountHolders--;
			} // Read Account Holders

			// Fraud:
			byte ts = Byte.parseByte(rd.readLine());
			for (byte b = 0; b < ts; b++)
				fraudQueueStrings.add(rd.readLine());

			rd.close();

//			for( AccountHolder ah0: accountHolders )
//				for( CheckingAccount ca: ah0.getCheckingAccounts() )
//					ca.process();

			sortAccountHolders();

			System.out.println(AccountHolder.writeToString());

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} // catch
	}// readFromFile

	private static void readCDAccounts(BufferedReader rd, AccountHolder ac) throws IOException {
		int transactionCount;
		int numOfCDAcc = Integer.parseInt(rd.readLine());
		CDAccount cda;
		while (numOfCDAcc > 0) {
			cda = CDAccount.readFromString(rd.readLine());
			ac.addCDAccount(cda);
			transactionCount = Integer.parseInt(rd.readLine());
			for (int t = 0; t < transactionCount; t++)
				cda.transactionStringAdd(rd.readLine());

			numOfCDAcc--;
		}
	}

	private static void readSavingsAccounts(BufferedReader rd, AccountHolder ac) throws IOException, ParseException {
		int transactionCount;
		int numOfSaveAcc = Integer.parseInt(rd.readLine());
		SavingsAccount sa;
		while (numOfSaveAcc > 0) {
			sa = SavingsAccount.readFromString(rd.readLine());
			try {
				ac.addSavingsAccount(sa);
			} catch (ExceedsCombinedBalanceLimitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NegativeAmountException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// savings transactions:
			transactionCount = Integer.parseInt(rd.readLine());
			for (int t = 0; t < transactionCount; t++)
				sa.transactionStringAdd(rd.readLine());

			numOfSaveAcc--;
		}
	}

	private static void readCheckingAccounts(BufferedReader rd, AccountHolder ac) throws IOException, ParseException {
		int transactionCount;
		int numOfCheckAcc = Integer.parseInt(rd.readLine());
		CheckingAccount ca;
		while (numOfCheckAcc > 0) {
			ca = CheckingAccount.readFromString(rd.readLine());
			try {
				ac.addCheckingAccount(ca);
			} catch (ExceedsCombinedBalanceLimitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NegativeAmountException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			accounts.put(ca.getAccountNumber(), ca);
			// read checking account transactions:
			transactionCount = Integer.parseInt(rd.readLine());
			for (int t = 0; t < transactionCount; t++)
				ca.transactionStringAdd(rd.readLine());

			numOfCheckAcc--;
		}
	}

//b. static boolean writeToFile(String fileName)
//i. Should also write BankAccount transactions and the FraudQueue
// Write DATABASE to FILE
	static boolean writeToFile(String fileName) {
		String outp = getNextAccountNumber() + "\n";

		for (int i = 0; i < cdOfferings.length; i++)
			if (cdOfferings[i] != null)
				outp += cdOfferings[i].toString();

		outp += accountHolders.length + "\n";
		for (int i = 0; i < accountHolders.length; i++)
			if (accountHolders[i] != null)
				outp += accountHolders[i].toStringForFile();

		System.out.println(outp);
		PrintWriter out;
		try {
			out = new PrintWriter(fileName);
			out.println(outp);
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	static AccountHolder[] sortAccountHolders() {
		int l = 0;
		while (accountHolders[l] != null) {
			l++;
		}
		Arrays.sort(accountHolders, 0, l);

		return accountHolders;
	}

	static void setNextAccountNumber(long nextAccountNumber) {
		accountNumber = nextAccountNumber;
	}

	public static long getNextAccountNumber() {
		return accountNumber++;
	}
	
	public static long getNextAccountHolderNumber() {
		return accountHolderNumber++;
	}
	
	public static long getNextcdoNumber() {
		return cdoNumber++;
	}
	
	public static AccountHolder getAccountHolder(long id) {
		for (AccountHolder ach : accountHolders) {
			if (id == ach.getId())
				return ach;		
		}
		return null;
	}

	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	 public static void addAccountHolder(AccountHolder accountHolder) {
		 accountHolder.setId(getNextAccountHolderNumber());
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

	public static double totalBalances() {
		double total = 0.0;
		for (int i = 0; i < accountHolders.length; i++) {
			if (accountHolders[i] != null)
				total += accountHolders[i].getCombinedBalance();
			else
				break;
		}

		return total;
	}
	
	public static void addCDO(CDOffering cdOffering) {
		cdOffering.setId(getNextcdoNumber());
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
