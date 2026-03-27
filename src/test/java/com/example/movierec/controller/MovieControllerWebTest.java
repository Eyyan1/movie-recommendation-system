package com.example.movierec.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.movierec.config.SecurityConfig;
import com.example.movierec.dto.RatingForm;
import com.example.movierec.entity.Genre;
import com.example.movierec.entity.Movie;
import com.example.movierec.security.CustomUserDetailsService;
import com.example.movierec.service.MovieService;
import com.example.movierec.service.RatingService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MovieController.class)
@Import(SecurityConfig.class)
class MovieControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private RatingService ratingService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void moviesPageShouldRedirectAnonymousUsersToLogin() throws Exception {
        mockMvc.perform(get("/movies"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void moviesPageShouldRenderForAuthenticatedUser() throws Exception {
        when(movieService.browseMovies("batman", "Action")).thenReturn(List.of(sampleMovie()));
        when(movieService.getAllGenres()).thenReturn(List.of(sampleGenre()));

        mockMvc.perform(get("/movies")
                        .param("q", "batman")
                        .param("genre", "Action")
                        .with(user(securityUser("alice", "USER"))))
                .andExpect(status().isOk())
                .andExpect(view().name("movies/list"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attribute("currentQuery", "batman"))
                .andExpect(model().attribute("currentGenre", "Action"));
    }

    @Test
    void movieDetailShouldRenderForAuthenticatedUser() throws Exception {
        when(movieService.getMovieById(1L)).thenReturn(sampleMovie());

        mockMvc.perform(get("/movies/1")
                        .with(user("alice").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("movies/detail"))
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attributeExists("ratingForm"));
    }

    private Movie sampleMovie() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Batman Begins");
        movie.setReleaseDate(LocalDate.of(2005, 6, 15));
        movie.setAverageRating(BigDecimal.valueOf(4.5));
        movie.setGenres(java.util.Set.of(sampleGenre()));
        return movie;
    }

    private Genre sampleGenre() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Action");
        return genre;
    }

    private UserDetails securityUser(String username, String role) {
        return User.withUsername(username).password("password").roles(role).build();
    }
}
