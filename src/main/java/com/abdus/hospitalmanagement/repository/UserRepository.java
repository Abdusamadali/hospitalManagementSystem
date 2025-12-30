package com.abdus.hospitalmanagement.repository;

import com.abdus.hospitalmanagement.entity.User;
import com.abdus.hospitalmanagement.entity.type.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User>findByUsername(String username);

    boolean existsByUsernameIgnoreCase(String attr0);

    Optional<User> findByProviderIdAndProviderType(String providerId, AuthProviderType providerType);
}
