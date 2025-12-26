package com.abdus.hospitalmanagement.service;

import com.abdus.hospitalmanagement.entity.User;
import com.abdus.hospitalmanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<String> deleteUser(Long id) {
        try {
            if (!userRepository.existsById(id)) {
                return Optional.of("User not found");
            }

            userRepository.deleteById(id);
            return Optional.of("Deleted successfully");
        } catch (Exception e) {
            return Optional.of("Error: " + e.getMessage());
        }
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }
}
