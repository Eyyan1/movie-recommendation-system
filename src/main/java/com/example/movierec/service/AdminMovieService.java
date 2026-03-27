package com.example.movierec.service;

import com.example.movierec.dto.AdminMovieForm;
import com.example.movierec.entity.Genre;
import com.example.movierec.entity.Movie;
import java.util.List;

public interface AdminMovieService {

    List<Movie> getAllMovies();

    List<Genre> getAllGenres();

    Movie getMovieById(Long id);

    AdminMovieForm buildForm(Movie movie);

    Movie createMovie(AdminMovieForm form);

    Movie updateMovie(Long movieId, AdminMovieForm form);

    void deleteMovie(Long movieId);
}
