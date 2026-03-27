package com.example.movierec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
