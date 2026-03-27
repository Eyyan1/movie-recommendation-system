package com.example.movierec.service;

import com.example.movierec.dto.RecommendationView;
import java.util.List;

public interface RecommendationService {

    RecommendationResult getRecommendationsForUser(Long userId, int limit);

    record RecommendationResult(
            List<RecommendationView> recommendations,
            boolean coldStart,
            boolean hasAnyRatings
    ) {
    }
}
