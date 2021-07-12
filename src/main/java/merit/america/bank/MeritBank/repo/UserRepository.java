package merit.america.bank.MeritBank.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import merit.america.bank.MeritBank.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}