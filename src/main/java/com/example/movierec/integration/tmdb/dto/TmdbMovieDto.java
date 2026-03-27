package com.example.movierec.integration.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public record TmdbMovieDto(
        Long id,
        String title,
        @JsonProperty("overview")
        String description,
        @JsonProperty("release_date")
        String releaseDate,
        @JsonProperty("poster_path")
        String posterPath,
        @JsonProperty("vote_average")
        BigDecimal averageRating,
        BigDecimal popularity,
        @JsonProperty("genre_ids")
        List<Long> genreIds
) {
}
