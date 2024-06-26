package com.security.authenticationserver.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class ResponseToken {
    private String accessToken;
    private String tokenType;
    private String expiresAt;
}
