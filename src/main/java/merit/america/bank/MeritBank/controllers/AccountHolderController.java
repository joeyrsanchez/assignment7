package merit.america.bank.MeritBank.controllers;

import javax.validation.Valid;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.IntegerDeserializer;

import merit.america.bank.MeritBank.models.AccountHolder;
import merit.america.bank.MeritBank.models.CDAccount;
import merit.america.bank.MeritBank.models.CheckingAccount;
import merit.america.bank.MeritBank.models.ExceedsCombinedBalanceLimitException;
import merit.america.bank.MeritBank.models.ExceedsFraudSuspicionLimitException;
import merit.america.bank.MeritBank.models.MeritBank;
import merit.america.bank.MeritBank.models.NegativeAmountException;
import merit.america.bank.MeritBank.models.SavingsAccount;
import merit.america.bank.MeritBank.models.CDOffering;

import org.springframework.http.HttpStatus;

@RestController
public class AccountHolderController {
	
	@GetMapping("/AccountHolders")
	public static AccountHolder[] getAllAccountHolders(){
		
		return MeritBank.getAccountHolders();
	}
	
	@PostMapping(value = "/AccountHolders")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder ach) {
		MeritBank.addAccountHolder(ach);
		return ach;	
	} 
	
	@GetMapping(value = "/AccountHolders/{id}")
	public AccountHolder getAccountHolder(@PathVariable("id")long id) {
		AccountHolder holder = MeritBank.getAccountHolder(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		return holder;
	}
	
	@GetMapping(value = "/AccountHolders/{id}/CheckingAccounts")
	public CheckingAccount[] getAccountHolderCheckingAccounts(@PathVariable("id")long id) {
		AccountHolder holder = MeritBank.getAccountHolder(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		return holder.getCheckingAccounts();
	}
	
	@PostMapping(value = "/AccountHolders/{id}/CheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount addAccountHolderCheckingAccounts(@PathVariable("id")long id, @RequestBody @Valid CheckingAccount checkingAccount) {
		AccountHolder holder = MeritBank.getAccountHolder(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		try {
			checkingAccount = holder.addCheckingAccount(checkingAccount.getBalance());
		} catch (NegativeAmountException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insuffecient Funds");
		}catch (ExceedsCombinedBalanceLimitException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount Exceeds Combined Balance Limit");
		} 
		
		return checkingAccount;
	}
	
	@GetMapping(value = "/AccountHolders/{id}/SavingsAccounts")
	public SavingsAccount[] getAccountHolderSavingsAccounts(@PathVariable("id")long id) {
		AccountHolder holder = MeritBank.getAccountHolder(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		return holder.getSavingsAccounts();
	}
	
	@PostMapping(value = "/AccountHolders/{id}/SavingsAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount addAccountHolderCheckingAccounts(@PathVariable("id")long id, @RequestBody @Valid SavingsAccount savingsAccount) {
		AccountHolder holder = MeritBank.getAccountHolder(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		try {
			savingsAccount = holder.addSavingsAccount(savingsAccount.getBalance());
		} catch (NegativeAmountException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insuffecient Funds");
		}catch (ExceedsCombinedBalanceLimitException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount Exceeds Combined Balance Limit");
		} 
		
		return savingsAccount;
	}
	
	@GetMapping(value = "/AccountHolders/{id}/CDAccounts")
	public CDAccount[] getAccountHolderCDAccounts(@PathVariable("id")long id) {
		AccountHolder holder = MeritBank.getAccountHolder(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		return holder.getCDAccounts();
	}
	
	@PostMapping(value = "/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addAccountHolderCDAccounts(@PathVariable("id")long id, @RequestBody @Valid CDAccount cdAccount) {
		
		
		CDOffering cdo = MeritBank.getCDOffering(cdAccount.getCdOffering().getId());
		
		
		AccountHolder holder = MeritBank.getAccountHolder(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		try {
			cdAccount = new CDAccount(cdo, cdAccount.getBalance());
			cdAccount = holder.addCDAccount(cdAccount);
			return cdAccount;
		} catch (ExceedsFraudSuspicionLimitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}
	
	
}
