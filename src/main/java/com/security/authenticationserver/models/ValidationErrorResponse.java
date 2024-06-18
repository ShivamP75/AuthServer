package com.security.authenticationserver.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidationErrorResponse {
    // Getters and setters
    private String field;
    private String message;

    public ValidationErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }

}

