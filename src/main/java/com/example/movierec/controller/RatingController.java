package com.example.movierec.controller;

import com.example.movierec.dto.RatingForm;
import com.example.movierec.security.CustomUserDetails;
import com.example.movierec.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/movies/{movieId}/ratings")
    public String submitRating(
            @PathVariable Long movieId,
            @Valid RatingForm ratingForm,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails currentUser,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("ratingError", "Please select a rating between 1 and 5.");
            return "redirect:/movies/" + movieId;
        }

        ratingService.rateMovie(currentUser.getId(), movieId, ratingForm.getRatingValue());
        redirectAttributes.addFlashAttribute("ratingSuccess", "Your rating has been saved.");
        return "redirect:/movies/" + movieId;
    }

    @GetMapping("/my-ratings")
    public String myRatings(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            org.springframework.ui.Model model
    ) {
        model.addAttribute("ratings", ratingService.getRatingsForUser(currentUser.getId()));
        return "ratings/list";
    }
}
