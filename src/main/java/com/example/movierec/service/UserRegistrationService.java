package com.example.movierec.service;

import com.example.movierec.dto.RegistrationForm;
import com.example.movierec.entity.User;
import com.example.movierec.entity.UserRole;
import com.example.movierec.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationService implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User register(RegistrationForm form) {
        String normalizedUsername = form.getUsername().trim();
        String normalizedEmail = form.getEmail().trim().toLowerCase();

        if (userRepository.findByUsername(normalizedUsername).isPresent()) {
            throw new IllegalArgumentException("Username is already taken");
        }

        if (userRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = new User();
        user.setUsername(normalizedUsername);
        user.setEmail(normalizedEmail);
        user.setPasswordHash(passwordEncoder.encode(form.getPassword()));
        user.setRole(UserRole.USER);

        return userRepository.save(user);
    }
}
