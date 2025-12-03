package com.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateScheduleRequest(

        @NotBlank(message = "URL не должен быть пустым")
        @Pattern(
                regexp = "^(https://).+",
                message = "URL должен начинаться с https://"
        )
        String url,

        @NotBlank(message = "Cron-выражение не должно быть пустым")
        @Pattern(
                regexp = "^([0-5]?\\d)\\s([0-5]?\\d)\\s([0-1]?\\d|2[0-3])\\s([1-9]|[12]\\d|3[01]|\\*)\\s([1-9]|1[0-2]|\\*)\\s([0-7]|\\*)$",
                message = "Cron-выражение должно быть в формате: 'секунды минуты часы день_месяца месяц день_недели'"
        )
        String cronExpression
) {
}