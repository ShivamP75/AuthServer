package com.security.authenticationserver.models;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class Authority {
    private String authority;

    // Constructors, getters, and setters
    public Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }

}
