package com.security.authenticationserver.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ValidInvalidTokenResponse {

    private String message;
    private String user;
    private boolean isAuthenticated;
}
