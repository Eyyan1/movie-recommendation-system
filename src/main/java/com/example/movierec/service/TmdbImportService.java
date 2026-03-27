package com.example.movierec.service;

public interface TmdbImportService {

    ImportSummary importPopularMovies(int page);

    record ImportSummary(int requestedPage, int fetchedMovies, int createdMovies, int updatedMovies) {
    }
}
