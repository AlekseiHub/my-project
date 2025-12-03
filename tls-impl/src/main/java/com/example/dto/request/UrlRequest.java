package com.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UrlRequest(

        @NotBlank(message = "URL не должен быть пустым")
        @Pattern(
                regexp = "^(https://).+",
                message = "URL должен начинаться с https://"
        )
        String url
) {
}