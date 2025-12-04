package com.example.dto.response;

import lombok.Builder;

/**
 * Ответ на удаление расписания SSL-сертификата.
 *
 * Содержит текстовое сообщение о результате выполнения операции.
 */
@Builder
public record DeleteScheduleResponse(
        String message
) {
}
