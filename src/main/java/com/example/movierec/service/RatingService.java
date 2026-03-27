package com.example.movierec.service;

import com.example.movierec.entity.Rating;
import java.util.List;
import java.util.Optional;

public interface RatingService {

    Rating rateMovie(Long userId, Long movieId, int ratingValue);

    Optional<Rating> getUserRatingForMovie(Long userId, Long movieId);

    List<Rating> getRatingsForUser(Long userId);
}
