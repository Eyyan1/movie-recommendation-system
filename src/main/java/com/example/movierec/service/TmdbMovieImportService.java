package com.example.movierec.service;

import com.example.movierec.config.TmdbProperties;
import com.example.movierec.entity.Genre;
import com.example.movierec.entity.Movie;
import com.example.movierec.integration.tmdb.TmdbClient;
import com.example.movierec.integration.tmdb.dto.TmdbGenreDto;
import com.example.movierec.integration.tmdb.dto.TmdbMovieDto;
import com.example.movierec.integration.tmdb.dto.TmdbPagedMovieResponseDto;
import com.example.movierec.repository.GenreRepository;
import com.example.movierec.repository.MovieRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TmdbMovieImportService implements TmdbImportService {

    private final TmdbClient tmdbClient;
    private final TmdbProperties tmdbProperties;
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public ImportSummary importPopularMovies(int page) {
        Map<Long, Genre> genresByTmdbId = syncGenres();
        TmdbPagedMovieResponseDto response = tmdbClient.fetchPopularMovies(page);

        if (response == null || response.results() == null || response.results().isEmpty()) {
            return new ImportSummary(page, 0, 0, 0);
        }

        int created = 0;
        int updated = 0;

        for (TmdbMovieDto tmdbMovie : response.results()) {
            boolean exists = movieRepository.findByTmdbId(tmdbMovie.id()).isPresent();
            Movie movie = movieRepository.findByTmdbId(tmdbMovie.id()).orElseGet(Movie::new);

            mapMovieFields(movie, tmdbMovie, genresByTmdbId);
            movieRepository.save(movie);

            if (exists) {
                updated++;
            } else {
                created++;
            }
        }

        return new ImportSummary(page, response.results().size(), created, updated);
    }

    private Map<Long, Genre> syncGenres() {
        var response = tmdbClient.fetchMovieGenres();
        if (response == null || response.genres() == null) {
            return Collections.emptyMap();
        }

        Map<Long, Genre> genresByTmdbId = new HashMap<>();

        for (TmdbGenreDto tmdbGenre : response.genres()) {
            Genre genre = genreRepository.findByName(tmdbGenre.name())
                    .orElseGet(Genre::new);
            genre.setName(tmdbGenre.name());
            Genre savedGenre = genreRepository.save(genre);
            genresByTmdbId.put(tmdbGenre.id(), savedGenre);
        }

        return genresByTmdbId;
    }

    private void mapMovieFields(Movie movie, TmdbMovieDto tmdbMovie, Map<Long, Genre> genresByTmdbId) {
        movie.setTmdbId(tmdbMovie.id());
        movie.setTitle(tmdbMovie.title());
        movie.setDescription(tmdbMovie.description());
        movie.setReleaseDate(parseReleaseDate(tmdbMovie.releaseDate()));
        movie.setPosterUrl(tmdbProperties.buildPosterUrl(tmdbMovie.posterPath()));
        movie.setAverageRating(tmdbMovie.averageRating());
        movie.setPopularity(tmdbMovie.popularity());
        movie.setGenres(resolveGenres(tmdbMovie.genreIds(), genresByTmdbId));
    }

    private LocalDate parseReleaseDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return LocalDate.parse(value);
    }

    private Set<Genre> resolveGenres(java.util.List<Long> genreIds, Map<Long, Genre> genresByTmdbId) {
        if (genreIds == null || genreIds.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Genre> genres = new HashSet<>();
        for (Long genreId : genreIds) {
            Genre genre = genresByTmdbId.get(genreId);
            if (genre != null) {
                genres.add(genre);
            }
        }
        return genres;
    }
}
