package merit.america.bank.MeritBank.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import merit.america.bank.MeritBank.models.SavingsAccount;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Integer>{
	Optional<SavingsAccount> findById(Integer id);

}
