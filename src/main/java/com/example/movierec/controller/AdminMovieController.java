package com.example.movierec.controller;

import com.example.movierec.dto.AdminMovieForm;
import com.example.movierec.service.AdminMovieService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/movies")
@RequiredArgsConstructor
public class AdminMovieController {

    private final AdminMovieService adminMovieService;

    @GetMapping
    public String listMovies(Model model) {
        model.addAttribute("movies", adminMovieService.getAllMovies());
        return "admin/movies/list";
    }

    @GetMapping("/new")
    public String newMovieForm(Model model) {
        populateFormModel(model, new AdminMovieForm(), true, null);
        return "admin/movies/form";
    }

    @PostMapping
    public String createMovie(
            @Valid @ModelAttribute("movieForm") AdminMovieForm movieForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            populateFormModel(model, movieForm, true, null);
            return "admin/movies/form";
        }

        adminMovieService.createMovie(movieForm);
        redirectAttributes.addFlashAttribute("successMessage", "Movie created successfully.");
        return "redirect:/admin/movies";
    }

    @GetMapping("/{id}/edit")
    public String editMovieForm(@PathVariable Long id, Model model) {
        try {
            populateFormModel(model, adminMovieService.buildForm(adminMovieService.getMovieById(id)), false, id);
            return "admin/movies/form";
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping("/{id}")
    public String updateMovie(
            @PathVariable Long id,
            @Valid @ModelAttribute("movieForm") AdminMovieForm movieForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            populateFormModel(model, movieForm, false, id);
            return "admin/movies/form";
        }

        try {
            adminMovieService.updateMovie(id, movieForm);
            redirectAttributes.addFlashAttribute("successMessage", "Movie updated successfully.");
            return "redirect:/admin/movies";
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteMovie(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminMovieService.deleteMovie(id);
            redirectAttributes.addFlashAttribute("successMessage", "Movie deleted successfully.");
            return "redirect:/admin/movies";
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    private void populateFormModel(Model model, AdminMovieForm movieForm, boolean createMode, Long movieId) {
        model.addAttribute("movieForm", movieForm);
        model.addAttribute("genres", adminMovieService.getAllGenres());
        model.addAttribute("createMode", createMode);
        model.addAttribute("movieId", movieId);
    }
}
