package com.example.dto.request;

public record CreateScheduleRequest(

        String url,
        String cronExpression

) {
}
