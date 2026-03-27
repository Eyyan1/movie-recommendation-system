package com.example.movierec.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tmdb")
public record TmdbProperties(
        String baseUrl,
        String apiVersion,
        String readAccessToken,
        String imageBaseUrl,
        String posterSize,
        String language
) {

    public String apiBasePath() {
        return "%s/%s".formatted(baseUrl, apiVersion);
    }

    public String buildPosterUrl(String posterPath) {
        if (posterPath == null || posterPath.isBlank()) {
            return null;
        }
        return "%s/%s%s".formatted(imageBaseUrl, posterSize, posterPath);
    }
}
