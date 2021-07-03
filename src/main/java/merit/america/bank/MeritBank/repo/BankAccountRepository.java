package merit.america.bank.MeritBank.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import merit.america.bank.MeritBank.models.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer>{
	BankAccount findById(long id);

}
