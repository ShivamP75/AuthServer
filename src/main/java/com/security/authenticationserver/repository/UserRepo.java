package com.security.authenticationserver.repository;

import com.security.authenticationserver.models.SignupRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<SignupRequest, Long> {
    SignupRequest findByUsername(String username);
}
