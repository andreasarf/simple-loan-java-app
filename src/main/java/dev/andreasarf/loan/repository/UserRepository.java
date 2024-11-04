package dev.andreasarf.loan.repository;

import dev.andreasarf.loan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    @Query(value = "SELECT * from USER where username ?1", nativeQuery = true)
    Optional<User> findByUsername(String username);
}
