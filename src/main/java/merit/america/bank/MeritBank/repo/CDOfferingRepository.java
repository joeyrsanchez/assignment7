package merit.america.bank.MeritBank.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import merit.america.bank.MeritBank.models.CDOffering;

public interface CDOfferingRepository extends JpaRepository<CDOffering, Long>{
	Optional<CDOffering> findById(Integer id);

}
