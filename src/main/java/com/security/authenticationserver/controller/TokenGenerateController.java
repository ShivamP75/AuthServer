package com.security.authenticationserver.controller;

import com.security.authenticationserver.exception.CustomException;
import com.security.authenticationserver.models.LoginRequest;
import com.security.authenticationserver.models.ResponseToken;
import com.security.authenticationserver.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.logging.Logger;

@RestController
public class TokenGenerateController {

    Logger logger = Logger.getLogger(TokenGenerateController.class.getName());

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public TokenGenerateController(JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;

    }

    @PostMapping("/get-token")
    public ResponseEntity<?> getToken(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication;
            authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                String token = jwtUtils.generateToken(loginRequest.getUsername());
                logger.info(loginRequest.getUsername().substring(0,1).toUpperCase()
                        + loginRequest.getUsername().substring(1) + " is authenticated");
                return ResponseEntity.ok(new ResponseToken(token, "JWT",
                        System.currentTimeMillis() + 1800 * 1000));
            }
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
        catch (Exception e) {
            logger.info("Invalid username or password - " + loginRequest.getUsername());
            throw new CustomException("Invalid username or password");
        }

    }
}
