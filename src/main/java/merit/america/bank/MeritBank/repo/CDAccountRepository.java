package merit.america.bank.MeritBank.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import merit.america.bank.MeritBank.models.CDAccount;

public interface CDAccountRepository extends JpaRepository<CDAccount, Integer>{
	Optional<CDAccount> findById(Integer id);

}
