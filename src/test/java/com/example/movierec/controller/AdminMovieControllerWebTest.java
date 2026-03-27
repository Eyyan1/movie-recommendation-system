package com.example.movierec.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.movierec.config.SecurityConfig;
import com.example.movierec.security.CustomUserDetailsService;
import com.example.movierec.service.AdminMovieService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminMovieController.class)
@Import(SecurityConfig.class)
class AdminMovieControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminMovieService adminMovieService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void adminMoviesShouldRedirectAnonymousUsersToLogin() throws Exception {
        mockMvc.perform(get("/admin/movies"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void adminMoviesShouldDenyRegularUsers() throws Exception {
        mockMvc.perform(get("/admin/movies")
                        .with(user(securityUser("alice", "USER"))))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/access-denied"));
    }

    @Test
    void adminMoviesShouldRenderForAdmins() throws Exception {
        when(adminMovieService.getAllMovies()).thenReturn(List.of());

        mockMvc.perform(get("/admin/movies")
                        .with(user(securityUser("admin", "ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/movies/list"));
    }

    private UserDetails securityUser(String username, String role) {
        return User.withUsername(username).password("password").roles(role).build();
    }
}
