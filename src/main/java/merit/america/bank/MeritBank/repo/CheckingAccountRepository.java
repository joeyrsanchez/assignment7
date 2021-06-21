package merit.america.bank.MeritBank.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import merit.america.bank.MeritBank.models.CheckingAccount;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Integer>{
	Optional<CheckingAccount> findById(Integer id);

}
