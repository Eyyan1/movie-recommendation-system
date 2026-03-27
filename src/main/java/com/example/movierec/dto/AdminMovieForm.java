package com.example.movierec.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
public class AdminMovieForm {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be at most 255 characters")
    private String title;

    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;

    @Size(max = 500, message = "Poster URL must be at most 500 characters")
    private String posterUrl;

    @DecimalMin(value = "0.0", inclusive = true, message = "Average rating must be 0 or greater")
    private BigDecimal averageRating;

    @DecimalMin(value = "0.0", inclusive = true, message = "Popularity must be 0 or greater")
    private BigDecimal popularity;

    private List<Long> genreIds = new ArrayList<>();
}
