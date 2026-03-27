package com.example.movierec.controller;

import com.example.movierec.service.TmdbImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/tmdb")
@RequiredArgsConstructor
public class TmdbImportController {

    private final TmdbImportService tmdbImportService;

    @GetMapping("/import/popular")
    public ResponseEntity<TmdbImportService.ImportSummary> importPopularMovies(
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        return ResponseEntity.ok(tmdbImportService.importPopularMovies(page));
    }
}
