package dev.andreasarf.loan.repository;

import dev.andreasarf.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query(value = "SELECT l.* FROM loan l join user u on l.user_id = u.id" +
            " WHERE u.username = ?1 AND l.police_number = ?2", nativeQuery = true)
    Optional<Loan> findByUsernameAndPoliceNumber(String username, String policeNumber);
}
