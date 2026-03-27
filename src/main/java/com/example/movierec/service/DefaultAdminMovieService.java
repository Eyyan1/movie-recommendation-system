package com.example.movierec.service;

import com.example.movierec.dto.AdminMovieForm;
import com.example.movierec.entity.Genre;
import com.example.movierec.entity.Movie;
import com.example.movierec.repository.GenreRepository;
import com.example.movierec.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultAdminMovieService implements AdminMovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Movie> getAllMovies() {
        return movieRepository.findAllByOrderByTitleAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAllGenres() {
        return genreRepository.findAllByOrderByNameAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Movie getMovieById(Long id) {
        return movieRepository.findWithGenresById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public AdminMovieForm buildForm(Movie movie) {
        AdminMovieForm form = new AdminMovieForm();
        form.setTitle(movie.getTitle());
        form.setDescription(movie.getDescription());
        form.setReleaseDate(movie.getReleaseDate());
        form.setPosterUrl(movie.getPosterUrl());
        form.setAverageRating(movie.getAverageRating());
        form.setPopularity(movie.getPopularity());
        form.setGenreIds(movie.getGenres().stream().map(Genre::getId).toList());
        return form;
    }

    @Override
    @Transactional
    public Movie createMovie(AdminMovieForm form) {
        Movie movie = new Movie();
        applyForm(movie, form);
        return movieRepository.save(movie);
    }

    @Override
    @Transactional
    public Movie updateMovie(Long movieId, AdminMovieForm form) {
        Movie movie = movieRepository.findWithGenresById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id " + movieId));
        applyForm(movie, form);
        return movieRepository.save(movie);
    }

    @Override
    @Transactional
    public void deleteMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id " + movieId));
        movieRepository.delete(movie);
    }

    private void applyForm(Movie movie, AdminMovieForm form) {
        movie.setTitle(form.getTitle().trim());
        movie.setDescription(normalizeText(form.getDescription()));
        movie.setReleaseDate(form.getReleaseDate());
        movie.setPosterUrl(normalizeText(form.getPosterUrl()));
        movie.setAverageRating(form.getAverageRating());
        movie.setPopularity(form.getPopularity());
        movie.setGenres(resolveGenres(form.getGenreIds()));
    }

    private Set<Genre> resolveGenres(List<Long> genreIds) {
        if (genreIds == null || genreIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(genreRepository.findAllById(genreIds));
    }

    private String normalizeText(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
