package com.example.movierec.service;

import com.example.movierec.entity.Genre;
import com.example.movierec.entity.Movie;
import com.example.movierec.repository.GenreRepository;
import com.example.movierec.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Movie> browseMovies(String query, String genreName) {
        String normalizedQuery = normalize(query);
        String normalizedGenre = normalize(genreName);

        if (normalizedQuery != null && normalizedGenre != null) {
            return movieRepository.findDistinctByTitleContainingIgnoreCaseAndGenres_NameIgnoreCaseOrderByTitleAsc(
                    normalizedQuery,
                    normalizedGenre
            );
        }

        if (normalizedQuery != null) {
            return movieRepository.findByTitleContainingIgnoreCaseOrderByTitleAsc(normalizedQuery);
        }

        if (normalizedGenre != null) {
            return movieRepository.findDistinctByGenres_NameIgnoreCaseOrderByTitleAsc(normalizedGenre);
        }

        return movieRepository.findAllByOrderByTitleAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Movie getMovieById(Long id) {
        return movieRepository.findWithGenresById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAllGenres() {
        return genreRepository.findAllByOrderByNameAsc();
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
