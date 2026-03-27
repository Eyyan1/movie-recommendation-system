package com.example.movierec.service;

import com.example.movierec.dto.RecommendationView;
import com.example.movierec.entity.Genre;
import com.example.movierec.entity.Movie;
import com.example.movierec.entity.Rating;
import com.example.movierec.entity.User;
import com.example.movierec.repository.MovieRepository;
import com.example.movierec.repository.RatingRepository;
import com.example.movierec.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultRecommendationService implements RecommendationService {

    private static final int HIGH_RATING_THRESHOLD = 4;

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;

    @Override
    @Transactional(readOnly = true)
    public RecommendationResult getRecommendationsForUser(Long userId, int limit) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        List<Rating> userRatings = ratingRepository.findByUser(user);
        boolean hasAnyRatings = !userRatings.isEmpty();

        Set<Long> ratedMovieIds = userRatings.stream()
                .map(rating -> rating.getMovie().getId())
                .collect(Collectors.toSet());

        List<Rating> favoriteRatings = userRatings.stream()
                .filter(rating -> rating.getRatingValue() >= HIGH_RATING_THRESHOLD)
                .toList();

        if (favoriteRatings.isEmpty()) {
            return new RecommendationResult(buildColdStartRecommendations(ratedMovieIds, limit), true, hasAnyRatings);
        }

        Map<String, Integer> genrePreferenceStrength = computeGenrePreferenceStrength(favoriteRatings);

        List<RecommendationView> recommendations = movieRepository.findAll().stream()
                .filter(movie -> !ratedMovieIds.contains(movie.getId()))
                .map(movie -> buildPersonalizedRecommendation(movie, genrePreferenceStrength))
                .filter(java.util.Objects::nonNull)
                .sorted(Comparator.comparingDouble(RecommendationView::score).reversed()
                        .thenComparing(recommendation -> nullSafeDouble(recommendation.movie().getAverageRating()), Comparator.reverseOrder())
                        .thenComparing(recommendation -> nullSafeDouble(recommendation.movie().getPopularity()), Comparator.reverseOrder())
                        .thenComparing(recommendation -> recommendation.movie().getTitle(), String.CASE_INSENSITIVE_ORDER))
                .limit(limit)
                .toList();

        return new RecommendationResult(recommendations, false, hasAnyRatings);
    }

    private Map<String, Integer> computeGenrePreferenceStrength(List<Rating> favoriteRatings) {
        Map<String, Integer> genrePreferenceStrength = new HashMap<>();

        for (Rating rating : favoriteRatings) {
            int weight = rating.getRatingValue();
            for (Genre genre : rating.getMovie().getGenres()) {
                genrePreferenceStrength.merge(genre.getName(), weight, Integer::sum);
            }
        }

        return genrePreferenceStrength;
    }

    private double scoreMovie(Movie movie, Map<String, Integer> genrePreferenceStrength) {
        int genreScore = movie.getGenres().stream()
                .mapToInt(genre -> genrePreferenceStrength.getOrDefault(genre.getName(), 0))
                .sum();

        double averageRatingScore = nullSafeDouble(movie.getAverageRating()) * 10.0;
        double popularityScore = Math.min(nullSafeDouble(movie.getPopularity()), 100.0);

        return (genreScore * 100.0) + averageRatingScore + popularityScore;
    }

    private RecommendationView buildPersonalizedRecommendation(Movie movie, Map<String, Integer> genrePreferenceStrength) {
        int genreScore = movie.getGenres().stream()
                .mapToInt(genre -> genrePreferenceStrength.getOrDefault(genre.getName(), 0))
                .sum();

        if (genreScore <= 0) {
            return null;
        }

        return new RecommendationView(movie, scoreMovie(movie, genrePreferenceStrength));
    }

    private List<RecommendationView> buildColdStartRecommendations(Set<Long> ratedMovieIds, int limit) {
        return movieRepository.findAllByOrderByAverageRatingDescPopularityDesc().stream()
                .filter(movie -> !ratedMovieIds.contains(movie.getId()))
                .map(movie -> new RecommendationView(movie, fallbackScore(movie)))
                .limit(limit)
                .toList();
    }

    private double fallbackScore(Movie movie) {
        return (nullSafeDouble(movie.getAverageRating()) * 10.0) + Math.min(nullSafeDouble(movie.getPopularity()), 100.0);
    }

    private double nullSafeDouble(BigDecimal value) {
        return value == null ? 0.0 : value.doubleValue();
    }
}
