package com.abdus.hospitalmanagement.entity;


import com.abdus.hospitalmanagement.entity.type.AuthProviderType;
import jakarta.persistence.*;
import jdk.jfr.StackTrace;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = true)
    private String password;

    private String providerId;

    @Enumerated(EnumType.STRING)
     private AuthProviderType providerType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
