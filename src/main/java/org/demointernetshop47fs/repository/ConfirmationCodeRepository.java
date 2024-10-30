package org.demointernetshop47fs.repository;

import org.demointernetshop47fs.entity.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {
    Optional<ConfirmationCode> findByCodeAndExpiredDateTimeAfter(String code, LocalDateTime currentTime);
}
