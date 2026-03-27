package com.example.movierec.controller;

import com.example.movierec.dto.RatingForm;
import com.example.movierec.entity.Movie;
import com.example.movierec.security.CustomUserDetails;
import com.example.movierec.service.MovieService;
import com.example.movierec.service.RatingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final RatingService ratingService;

    @GetMapping
    public String listMovies(
            @RequestParam(name = "q", required = false) String query,
            @RequestParam(name = "genre", required = false) String genre,
            Model model
    ) {
        model.addAttribute("movies", movieService.browseMovies(query, genre));
        model.addAttribute("genres", movieService.getAllGenres());
        model.addAttribute("currentQuery", query == null ? "" : query.trim());
        model.addAttribute("currentGenre", genre == null ? "" : genre.trim());
        return "movies/list";
    }

    @GetMapping("/{id}")
    public String movieDetail(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails currentUser,
            Model model
    ) {
        try {
            Movie movie = movieService.getMovieById(id);
            model.addAttribute("movie", movie);
            model.addAttribute("ratingForm", new RatingForm());

            if (currentUser != null) {
                ratingService.getUserRatingForMovie(currentUser.getId(), id)
                        .ifPresent(rating -> model.addAttribute("currentUserRating", rating));
            }

            return "movies/detail";
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }
}
