package dev.overlax.cloudstorage.mycloudstorage.repository;

import dev.overlax.cloudstorage.mycloudstorage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
}
