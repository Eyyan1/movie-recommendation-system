package com.example.movierec.service;

import com.example.movierec.entity.Movie;
import com.example.movierec.entity.Rating;
import com.example.movierec.entity.User;
import com.example.movierec.repository.MovieRepository;
import com.example.movierec.repository.RatingRepository;
import com.example.movierec.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultRatingService implements RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Override
    @Transactional
    public Rating rateMovie(Long userId, Long movieId, int ratingValue) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id " + movieId));

        Rating rating = ratingRepository.findByUserAndMovie(user, movie)
                .orElseGet(() -> {
                    Rating newRating = new Rating();
                    newRating.setUser(user);
                    newRating.setMovie(movie);
                    return newRating;
                });

        rating.setRatingValue(ratingValue);
        Rating savedRating = ratingRepository.save(rating);

        recalculateAverageRating(movie);

        return savedRating;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rating> getUserRatingForMovie(Long userId, Long movieId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id " + movieId));

        return ratingRepository.findByUserAndMovie(user, movie);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Rating> getRatingsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        return ratingRepository.findByUserOrderByUpdatedAtDesc(user);
    }

    private void recalculateAverageRating(Movie movie) {
        List<Rating> ratings = ratingRepository.findByMovie(movie);

        if (ratings.isEmpty()) {
            movie.setAverageRating(null);
        } else {
            double average = ratings.stream()
                    .mapToInt(Rating::getRatingValue)
                    .average()
                    .orElse(0.0);
            movie.setAverageRating(BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP));
        }

        movieRepository.save(movie);
    }
}
