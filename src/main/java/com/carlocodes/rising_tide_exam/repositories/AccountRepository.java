package com.carlocodes.rising_tide_exam.repositories;

import com.carlocodes.rising_tide_exam.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
