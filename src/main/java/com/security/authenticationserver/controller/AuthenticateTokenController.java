package com.security.authenticationserver.controller;

import com.security.authenticationserver.models.ValidInvalidTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class AuthenticateTokenController {

    Logger logger = Logger.getLogger(AuthenticateTokenController.class.getName());

    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            logger.info("Valid Token for user - " + authentication.getName());
            return ResponseEntity.ok().body(new ValidInvalidTokenResponse("Valid Token",
                    authentication.getName(), authentication.isAuthenticated()));
        }
        else {
            return ResponseEntity.status(401).body(new ValidInvalidTokenResponse("Invalid Token",
                    authentication.getName(), authentication.isAuthenticated()));
        }
    }
}
