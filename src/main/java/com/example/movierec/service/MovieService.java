package com.example.movierec.service;

import com.example.movierec.entity.Genre;
import com.example.movierec.entity.Movie;
import java.util.List;

public interface MovieService {

    List<Movie> browseMovies(String query, String genreName);

    Movie getMovieById(Long id);

    List<Genre> getAllGenres();
}
