package com.example.movierec.repository;

import com.example.movierec.entity.Genre;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByName(String name);

    java.util.List<Genre> findAllByOrderByNameAsc();
}
