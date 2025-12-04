package com.example.controller;

import com.example.dto.request.CreateScheduleRequest;
import com.example.dto.request.UrlRequest;
import com.example.dto.response.CertificateResultResponse;
import com.example.dto.response.CreateScheduleResponse;
import com.example.dto.response.DeleteScheduleResponse;
import com.example.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления заданиями проверки сертификатов.
 *
 * Предоставляет API для создания расписаний, удаления расписаний и получения
 * результатов проверок SSL-сертификатов. Все операции делегируются сервису
 * {@link ScheduleService}.
 */
@Validated
@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * Получает список всех результатов проверок SSL-сертификатов.
     *
     * @return список результатов проверок в формате {@link CertificateResultResponse}.
     */
    @GetMapping("/result/all")
    public ResponseEntity<List<CertificateResultResponse>> getResults() {
        return ResponseEntity.ok(scheduleService.getResults());
    }

    /**
     * Создаёт новое расписание проверки SSL-сертификата.
     *
     * Расписание включает URL, интервал проверки и дополнительные параметры,
     * необходимые для автоматического запуска задач.
     *
     * @param request данные для создания расписания ({@link CreateScheduleRequest})
     * @return информация о созданном расписании ({@link CreateScheduleResponse})
     */
    @PostMapping
    public ResponseEntity<CreateScheduleResponse> createSchedule(@Valid @RequestBody CreateScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.createSchedule(request));
    }

    /**
     * Удаляет существующее расписание проверки сертификата по указанному URL.
     *
     * @param request объект с URL, для которого нужно удалить расписание ({@link UrlRequest})
     * @return результат операции удаления ({@link DeleteScheduleResponse})
     */
    @DeleteMapping
    public ResponseEntity<DeleteScheduleResponse> deleteSchedule(@Valid @RequestBody UrlRequest request) {
        return ResponseEntity.ok(scheduleService.delete(request));
    }
}