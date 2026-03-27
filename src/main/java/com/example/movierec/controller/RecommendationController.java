package com.example.movierec.controller;

import com.example.movierec.security.CustomUserDetails;
import com.example.movierec.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public String recommendations(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            Model model
    ) {
        RecommendationService.RecommendationResult result =
                recommendationService.getRecommendationsForUser(currentUser.getId(), 10);

        model.addAttribute("recommendations", result.recommendations());
        model.addAttribute("coldStart", result.coldStart());
        model.addAttribute("hasAnyRatings", result.hasAnyRatings());
        return "recommendations/list";
    }
}
