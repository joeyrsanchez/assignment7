package merit.america.bank.MeritBank.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import merit.america.bank.MeritBank.exceptions.ExceedsCombinedBalanceLimitException;
import merit.america.bank.MeritBank.models.*;
import merit.america.bank.MeritBank.repo.AccountHolderRepository;
import merit.america.bank.MeritBank.repo.AccountHoldersContactDetailsRepository;
import merit.america.bank.MeritBank.repo.CDOfferingRepository;
import merit.america.bank.MeritBank.repo.BankAccountRepository;

import org.springframework.http.HttpStatus;

@RestController
public class AccountHolderController {
	
private AccountHolderRepository accountHolderRepository;
@SuppressWarnings("unused")
private AccountHoldersContactDetailsRepository accountHoldersContactDetailsRepository;
private BankAccountRepository bankAccountRepository;
private CDOfferingRepository cdOfferingRepository;
	
	private AccountHolderController(
			AccountHolderRepository accountHolderRepository, 
			AccountHoldersContactDetailsRepository accountHoldersContactDetailsRepository,
			CDOfferingRepository cdOfferingRepository,
			BankAccountRepository bankAccountRepository) {
		
	    this.accountHolderRepository = accountHolderRepository;
	    this.accountHoldersContactDetailsRepository = accountHoldersContactDetailsRepository;
	    this.cdOfferingRepository = cdOfferingRepository;
	    this.bankAccountRepository = bankAccountRepository;
	}
	
	
	
	
	@GetMapping("/AccountHolders")
	public List<AccountHolder> getAllAccountHolders(){
		
		return accountHolderRepository.findAll();
	}
	
	@PostMapping(value = "/AccountHolders")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder ach) {
		accountHolderRepository.save(ach);
		return ach;	
	} 
	
	@GetMapping(value = "/AccountHolders/{id}")
	public AccountHolder getAccountHolder(@PathVariable("id")long id) {
		AccountHolder holder = accountHolderRepository.findById(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		return holder;
	}
	
	@GetMapping(value = "/AccountHolders/{id}/CheckingAccounts")
	public List<CheckingAccount> getAccountHolderCheckingAccounts(@PathVariable("id")long id) {
		AccountHolder holder = accountHolderRepository.findById(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		return holder.getCheckingAccounts();
	}
	
	@PostMapping(value = "/AccountHolders/{id}/CheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount addAccountHolderCheckingAccounts(@PathVariable("id")long id, 
			@RequestBody @Valid CheckingAccount checkingAccount) {
		AccountHolder holder = accountHolderRepository.findById(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		try {
			checkingAccount = holder.addCheckingAccount(checkingAccount.getBalance());
		} catch (ExceedsCombinedBalanceLimitException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount Exceeds Combined Balance Limit");
		} 
		accountHolderRepository.save(holder);
		bankAccountRepository.save(checkingAccount);
		return checkingAccount;
	}
	
	@GetMapping(value = "/AccountHolders/{id}/SavingsAccounts")
	public List<SavingsAccount> getAccountHolderSavingsAccounts(@PathVariable("id")long id) {
		AccountHolder holder = accountHolderRepository.findById(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		return holder.getSavingsAccounts();
	}
	
	@PostMapping(value = "/AccountHolders/{id}/SavingsAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount addAccountHolderCheckingAccounts(@PathVariable("id")long id, @RequestBody @Valid SavingsAccount savingsAccount) {
		AccountHolder holder = accountHolderRepository.findById(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		try {
			savingsAccount = holder.addSavingsAccount(savingsAccount.getBalance());
		}catch (ExceedsCombinedBalanceLimitException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount Exceeds Combined Balance Limit");
		} 
		
		accountHolderRepository.save(holder);
		bankAccountRepository.save(savingsAccount);
		return savingsAccount;
	}
	
	@GetMapping(value = "/AccountHolders/{id}/CDAccounts")
	public List <CDAccount> getAccountHolderCDAccounts(@PathVariable("id")long id) {
		AccountHolder holder = accountHolderRepository.findById(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		return holder.getCDAccounts();
	}
	
	@PostMapping(value = "/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addAccountHolderCDAccounts(@PathVariable("id")long id, @RequestBody @Valid CDAccountHelper cdAccountHelper) {
		
		CDOffering cdo = cdOfferingRepository.findById(cdAccountHelper.getCdOffering().getID());
		if (cdo == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid CDOffering");
		
		AccountHolder holder = accountHolderRepository.findById(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		
		CDAccount cdAccount = new CDAccount(cdo, cdAccountHelper.getBalance());
		cdAccount = holder.addCDAccount(cdAccount);
		
		accountHolderRepository.save(holder);
		bankAccountRepository.save(cdAccount);
		return cdAccount;

	}
	
	
}
