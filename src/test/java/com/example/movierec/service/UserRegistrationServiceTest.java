package com.example.movierec.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.movierec.dto.RegistrationForm;
import com.example.movierec.entity.User;
import com.example.movierec.entity.UserRole;
import com.example.movierec.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserRegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserRegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new UserRegistrationService(userRepository, passwordEncoder);
    }

    @Test
    void registerShouldHashPasswordAndAssignUserRole() {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("alice");
        form.setEmail("alice@example.com");
        form.setPassword("password123");

        when(userRepository.findByUsername("alice")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("alice@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = registrationService.register(form);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User persistedUser = captor.getValue();

        assertThat(savedUser.getRole()).isEqualTo(UserRole.USER);
        assertThat(persistedUser.getPasswordHash()).isNotEqualTo("password123");
        assertThat(passwordEncoder.matches("password123", persistedUser.getPasswordHash())).isTrue();
    }

    @Test
    void registerShouldRejectDuplicateUsername() {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("alice");
        form.setEmail("alice@example.com");
        form.setPassword("password123");

        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> registrationService.register(form))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Username");
    }
}
