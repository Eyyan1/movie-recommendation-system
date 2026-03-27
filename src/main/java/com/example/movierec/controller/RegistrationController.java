package com.example.movierec.controller;

import com.example.movierec.dto.RegistrationForm;
import com.example.movierec.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("registrationForm")) {
            model.addAttribute("registrationForm", new RegistrationForm());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            registrationService.register(registrationForm);
        } catch (IllegalArgumentException exception) {
            if (exception.getMessage().toLowerCase().contains("username")) {
                bindingResult.rejectValue("username", "duplicate", exception.getMessage());
            } else if (exception.getMessage().toLowerCase().contains("email")) {
                bindingResult.rejectValue("email", "duplicate", exception.getMessage());
            } else {
                model.addAttribute("registrationError", exception.getMessage());
            }
            return "auth/register";
        }

        redirectAttributes.addFlashAttribute("registrationSuccess", "Registration successful. Please log in.");
        return "redirect:/login";
    }
}
