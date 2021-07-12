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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class Me {
	
private AccountHolderRepository accountHolderRepository;
@SuppressWarnings("unused")
private AccountHoldersContactDetailsRepository accountHoldersContactDetailsRepository;
private BankAccountRepository bankAccountRepository;
private CDOfferingRepository cdOfferingRepository;
	
	private Me (
			AccountHolderRepository accountHolderRepository, 
			AccountHoldersContactDetailsRepository accountHoldersContactDetailsRepository,
			CDOfferingRepository cdOfferingRepository,
			BankAccountRepository bankAccountRepository) {
		
	    this.accountHolderRepository = accountHolderRepository;
	    this.accountHoldersContactDetailsRepository = accountHoldersContactDetailsRepository;
	    this.cdOfferingRepository = cdOfferingRepository;
	    this.bankAccountRepository = bankAccountRepository;
	}
	

	
	@GetMapping(value = "/Me")
	public AccountHolder getAccountHolder() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)authentication.getPrincipal();
		Long id = currentUser.getAccountHolderId();
		
		AccountHolder holder = accountHolderRepository.findById(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		return holder;
	}
	
	@GetMapping(value = "/Me/CheckingAccounts")
	public List<CheckingAccount> getAccountHolderCheckingAccounts() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)authentication.getPrincipal();
		Long id = currentUser.getAccountHolderId();
		
		AccountHolder holder = accountHolderRepository.findById(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		return holder.getCheckingAccounts();
	}
	
	@PostMapping(value = "/Me/CheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount addAccountHolderCheckingAccounts(@RequestBody @Valid CheckingAccount checkingAccount) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)authentication.getPrincipal();
		Long id = currentUser.getAccountHolderId();
		
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
	
	@GetMapping(value = "/Me/SavingsAccounts")
	public List<SavingsAccount> getAccountHolderSavingsAccounts() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)authentication.getPrincipal();
		Long id = currentUser.getAccountHolderId();
		
		AccountHolder holder = accountHolderRepository.findById(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		return holder.getSavingsAccounts();
	}
	
	@PostMapping(value = "/Me/SavingsAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount addAccountHolderCheckingAccounts(@RequestBody @Valid SavingsAccount savingsAccount) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)authentication.getPrincipal();
		Long id = currentUser.getAccountHolderId();
		
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
	
	@GetMapping(value = "/Me/CDAccounts")
	public List <CDAccount> getAccountHolderCDAccounts() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)authentication.getPrincipal();
		Long id = currentUser.getAccountHolderId();
		
		AccountHolder holder = accountHolderRepository.findById(id);
		if(holder == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
		return holder.getCDAccounts();
	}
	
	@PostMapping(value = "/Me/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addAccountHolderCDAccounts(@RequestBody @Valid CDAccountHelper cdAccountHelper) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)authentication.getPrincipal();
		Long id = currentUser.getAccountHolderId();
		
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
