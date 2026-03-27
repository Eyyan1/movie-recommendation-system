package com.example.movierec.repository;

import com.example.movierec.entity.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @EntityGraph(attributePaths = "genres")
    List<Movie> findAll();

    @EntityGraph(attributePaths = "genres")
    List<Movie> findAllByOrderByTitleAsc();

    @EntityGraph(attributePaths = "genres")
    List<Movie> findAllByOrderByAverageRatingDescPopularityDesc();

    @EntityGraph(attributePaths = "genres")
    List<Movie> findByTitleContainingIgnoreCase(String title);

    Optional<Movie> findByTmdbId(Long tmdbId);

    @EntityGraph(attributePaths = "genres")
    List<Movie> findByGenres_Name(String name);

    @EntityGraph(attributePaths = "genres")
    List<Movie> findByTitleContainingIgnoreCaseOrderByTitleAsc(String title);

    @EntityGraph(attributePaths = "genres")
    List<Movie> findDistinctByGenres_NameIgnoreCaseOrderByTitleAsc(String genreName);

    @EntityGraph(attributePaths = "genres")
    List<Movie> findDistinctByTitleContainingIgnoreCaseAndGenres_NameIgnoreCaseOrderByTitleAsc(String title, String genreName);

    @EntityGraph(attributePaths = "genres")
    Optional<Movie> findWithGenresById(Long id);
}
