package com.example.movierec.dto;

import com.example.movierec.entity.Movie;

public record RecommendationView(
        Movie movie,
        double score
) {
}
