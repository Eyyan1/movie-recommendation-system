package com.example.movierec.integration.tmdb.dto;

import java.util.List;

public record TmdbGenreListResponseDto(
        List<TmdbGenreDto> genres
) {
}
