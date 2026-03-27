package com.example.movierec.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.movierec.entity.Movie;
import com.example.movierec.entity.Rating;
import com.example.movierec.entity.User;
import com.example.movierec.repository.MovieRepository;
import com.example.movierec.repository.RatingRepository;
import com.example.movierec.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultRatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private DefaultRatingService ratingService;

    @Test
    void rateMovieShouldCreateNewRatingAndRecalculateAverage() {
        User user = new User();
        user.setId(1L);

        Movie movie = new Movie();
        movie.setId(10L);

        Rating savedRating = new Rating();
        savedRating.setUser(user);
        savedRating.setMovie(movie);
        savedRating.setRatingValue(5);

        Rating secondRating = new Rating();
        secondRating.setRatingValue(3);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(movieRepository.findById(10L)).thenReturn(Optional.of(movie));
        when(ratingRepository.findByUserAndMovie(user, movie)).thenReturn(Optional.empty());
        when(ratingRepository.save(org.mockito.ArgumentMatchers.any(Rating.class))).thenReturn(savedRating);
        when(ratingRepository.findByMovie(movie)).thenReturn(List.of(savedRating, secondRating));

        Rating result = ratingService.rateMovie(1L, 10L, 5);

        assertThat(result.getRatingValue()).isEqualTo(5);
        assertThat(movie.getAverageRating()).isEqualByComparingTo(new BigDecimal("4.00"));
        verify(movieRepository).save(movie);
    }

    @Test
    void rateMovieShouldUpdateExistingRatingInsteadOfCreatingDuplicate() {
        User user = new User();
        user.setId(1L);

        Movie movie = new Movie();
        movie.setId(10L);

        Rating existingRating = new Rating();
        existingRating.setUser(user);
        existingRating.setMovie(movie);
        existingRating.setRatingValue(2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(movieRepository.findById(10L)).thenReturn(Optional.of(movie));
        when(ratingRepository.findByUserAndMovie(user, movie)).thenReturn(Optional.of(existingRating));
        when(ratingRepository.save(existingRating)).thenReturn(existingRating);
        when(ratingRepository.findByMovie(movie)).thenReturn(List.of(existingRating));

        ratingService.rateMovie(1L, 10L, 4);

        assertThat(existingRating.getRatingValue()).isEqualTo(4);
        assertThat(movie.getAverageRating()).isEqualByComparingTo(new BigDecimal("4.00"));
    }
}
