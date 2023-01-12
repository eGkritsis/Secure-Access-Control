package com.aueb.springloginsecurity;

import org.apache.juli.logging.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoggingRepository extends JpaRepository<Logging, Long> {
    List<Logging> findByUsername(String username);
}
