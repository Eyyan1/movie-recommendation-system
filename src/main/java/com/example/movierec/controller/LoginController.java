package com.example.movierec.controller;

import com.example.movierec.dto.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(
            @RequestParam(name = "error", required = false) String error,
            @RequestParam(name = "logout", required = false) String logout,
            Model model
    ) {
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("loginError", error != null);
        model.addAttribute("logoutSuccess", logout != null);
        return "auth/login";
    }
}
