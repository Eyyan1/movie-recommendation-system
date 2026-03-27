package com.example.movierec;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MovieRecommendationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieRecommendationApplication.class, args);
    }
}
