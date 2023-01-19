package com.SpringBootSecureAccessControl.config;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    public int findRemainingAttemptsByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE Users u SET u.login_attempts = ?1 WHERE u.username = ?2")
    public void updateLoginAttempts(int login_attempts, String username);
}
