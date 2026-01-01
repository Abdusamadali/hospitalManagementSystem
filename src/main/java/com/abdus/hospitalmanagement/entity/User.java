package com.abdus.hospitalmanagement.entity;


import com.abdus.hospitalmanagement.entity.type.AuthProviderType;
import com.abdus.hospitalmanagement.entity.type.Role;
import com.abdus.hospitalmanagement.security.RolePermissionMapping;
import jakarta.persistence.*;
import jdk.jfr.StackTrace;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;

import java.security.Permission;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "users")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority("USER_"+role.name()))
//                .collect(Collectors.toSet());
        Set<SimpleGrantedAuthority>authorities = new HashSet<>();
        roles.forEach(
                role -> {
                    Set<SimpleGrantedAuthority> permission = RolePermissionMapping.getAuthoritiesForRole(role );
                    authorities.addAll(permission);
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
                }
        );

        return authorities;
    }

}
