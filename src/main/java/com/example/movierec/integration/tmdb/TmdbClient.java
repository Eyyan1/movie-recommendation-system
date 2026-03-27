package com.example.movierec.integration.tmdb;

import com.example.movierec.config.TmdbProperties;
import com.example.movierec.integration.tmdb.dto.TmdbGenreListResponseDto;
import com.example.movierec.integration.tmdb.dto.TmdbPagedMovieResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class TmdbClient {

    private final TmdbProperties tmdbProperties;
    private final RestTemplateBuilder restTemplateBuilder;

    private RestClient restClient() {
        Assert.hasText(tmdbProperties.readAccessToken(), "TMDb read access token must be configured");

        return RestClient.builder(restTemplateBuilder.build())
                .baseUrl(tmdbProperties.apiBasePath())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + tmdbProperties.readAccessToken())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public TmdbPagedMovieResponseDto fetchPopularMovies(int page) {
        return restClient()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/popular")
                        .queryParam("language", tmdbProperties.language())
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .body(TmdbPagedMovieResponseDto.class);
    }

    public TmdbGenreListResponseDto fetchMovieGenres() {
        return restClient()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/genre/movie/list")
                        .queryParam("language", tmdbProperties.language())
                        .build())
                .retrieve()
                .body(TmdbGenreListResponseDto.class);
    }
}
