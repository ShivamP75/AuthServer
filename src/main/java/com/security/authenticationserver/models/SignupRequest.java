package com.security.authenticationserver.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    @Size(min = 5, max = 20, message = "Username must be at least 5 and at most 20 character long")
    private String username;

    @Column(nullable = false)
    @Size(min = 8, message = "Password must be at least 8 character long")
    private String password;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "userId"))
    private List<Authority> authorities;
}
