package com.example.movierec.repository;

import com.example.movierec.entity.Movie;
import com.example.movierec.entity.Rating;
import com.example.movierec.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @EntityGraph(attributePaths = {"movie", "movie.genres"})
    List<Rating> findByUser(User user);

    List<Rating> findByMovie(Movie movie);

    Optional<Rating> findByUserAndMovie(User user, Movie movie);

    @EntityGraph(attributePaths = {"movie", "movie.genres"})
    List<Rating> findByUserOrderByUpdatedAtDesc(User user);
}
