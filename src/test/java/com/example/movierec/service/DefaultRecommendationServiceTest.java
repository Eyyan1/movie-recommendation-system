package com.example.movierec.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.movierec.dto.RecommendationView;
import com.example.movierec.entity.Genre;
import com.example.movierec.entity.Movie;
import com.example.movierec.entity.Rating;
import com.example.movierec.entity.User;
import com.example.movierec.repository.MovieRepository;
import com.example.movierec.repository.RatingRepository;
import com.example.movierec.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultRecommendationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private DefaultRecommendationService recommendationService;

    @Test
    void shouldRecommendUnratedMoviesFromFavoriteGenres() {
        User user = new User();
        user.setId(1L);

        Genre action = new Genre();
        action.setId(1L);
        action.setName("Action");

        Movie ratedMovie = movie(1L, "Rated Action", 4.50, 80.0, action);
        Movie recommendedMovie = movie(2L, "Action Pick", 4.20, 70.0, action);
        Movie unrelatedMovie = movie(3L, "Drama Pick", 4.90, 90.0, genre("Drama"));

        Rating highRating = new Rating();
        highRating.setMovie(ratedMovie);
        highRating.setRatingValue(5);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(ratingRepository.findByUser(user)).thenReturn(List.of(highRating));
        when(movieRepository.findAll()).thenReturn(List.of(ratedMovie, recommendedMovie, unrelatedMovie));

        RecommendationService.RecommendationResult result = recommendationService.getRecommendationsForUser(1L, 10);

        assertThat(result.coldStart()).isFalse();
        assertThat(result.recommendations()).extracting(RecommendationView::movie).extracting(Movie::getTitle)
                .containsExactly("Action Pick");
    }

    @Test
    void shouldUseFallbackRecommendationsForColdStartUsers() {
        User user = new User();
        user.setId(1L);

        Movie topMovie = movie(10L, "Top Movie", 4.80, 95.0, genre("Sci-Fi"));
        Movie secondMovie = movie(11L, "Second Movie", 4.20, 60.0, genre("Action"));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(ratingRepository.findByUser(user)).thenReturn(List.of());
        when(movieRepository.findAllByOrderByAverageRatingDescPopularityDesc()).thenReturn(List.of(topMovie, secondMovie));

        RecommendationService.RecommendationResult result = recommendationService.getRecommendationsForUser(1L, 10);

        assertThat(result.coldStart()).isTrue();
        assertThat(result.hasAnyRatings()).isFalse();
        assertThat(result.recommendations()).extracting(view -> view.movie().getTitle())
                .containsExactly("Top Movie", "Second Movie");
    }

    private Movie movie(Long id, String title, double averageRating, double popularity, Genre... genres) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setAverageRating(BigDecimal.valueOf(averageRating));
        movie.setPopularity(BigDecimal.valueOf(popularity));
        movie.setGenres(Set.of(genres));
        return movie;
    }

    private Genre genre(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        return genre;
    }
}
