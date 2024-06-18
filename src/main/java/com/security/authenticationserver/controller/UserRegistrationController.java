package com.security.authenticationserver.controller;

import com.security.authenticationserver.exception.CustomException;
import com.security.authenticationserver.models.Authority;
import com.security.authenticationserver.models.SignupRequest;
import com.security.authenticationserver.repository.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class UserRegistrationController {

    Logger logger = Logger.getLogger(UserRegistrationController.class.getName());

    private final UserRepo userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRegistrationController(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            signupRequest.setAuthorities(List.of(new Authority("USER")));
            userRepository.save(signupRequest);
            logger.info("User registered successfully");
            return ResponseEntity.ok().body("User registered successfully");
        }
        catch (DataIntegrityViolationException ex) {
            // Throw custom exception if a unique constraint is violated

            if (ex.getCause() != null && ex.getCause().getMessage().contains("Unique index or primary key violation")) {
                logger.warning("User already exists");
                throw new CustomException("A user with this username or email already exists.");
            }
            throw ex;
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
