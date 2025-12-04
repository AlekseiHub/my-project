package com.example.dto.response;

import lombok.Builder;

/**
 * Ответ на создание нового расписания проверки SSL-сертификата.
 *
 * Содержит текстовое сообщение о результате выполнения операции.
 */
@Builder
public record CreateScheduleResponse(
        String message
) {
}