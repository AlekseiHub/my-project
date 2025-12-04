package com.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

/**
 * Дто входящего запроса на удаление расписания.
 *
 * Содержит указанный URL.
 */
@Builder
public record UrlRequest(
        @NotBlank(message = "URL не должен быть пустым")
        @Pattern(
                regexp = "^(https://).+",
                message = "URL должен начинаться с https://"
        )
        String url
) {
}