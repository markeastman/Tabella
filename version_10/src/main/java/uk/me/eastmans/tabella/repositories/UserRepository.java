package uk.me.eastmans.tabella.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.me.eastmans.tabella.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmail(String email);
}
