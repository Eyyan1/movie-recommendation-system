package com.example.movierec.integration.tmdb.dto;

import java.util.List;

public record TmdbPagedMovieResponseDto(
        Integer page,
        List<TmdbMovieDto> results,
        Integer totalPages,
        Integer totalResults
) {
}
