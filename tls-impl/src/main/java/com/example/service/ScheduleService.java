package com.example.service;

import com.example.dto.request.CreateScheduleRequest;
import com.example.dto.request.UrlRequest;
import com.example.dto.response.CertificateResultResponse;
import com.example.dto.response.CreateScheduleResponse;
import com.example.dto.response.DeleteScheduleResponse;

import java.util.List;

/**
 * Сервис для управления расписаниями проверки SSL-сертификатов.
 *
 * Предоставляет методы для:
 * lСоздания новых расписаний проверки сертификатов
 * Удаления существующих расписаний
 * Получения результатов всех проверок сертификатов
 */
public interface ScheduleService {

    /**
     * Получает список всех результатов проверок SSL-сертификатов.
     *
     * @return список {@link CertificateResultResponse} с данными о проверках
     */
    List<CertificateResultResponse> getResults();

    /**
     * Удаляет расписание проверки сертификата по указанному URL.
     *
     * @param request объект {@link UrlRequest}, содержащий URL для удаления
     * @return {@link DeleteScheduleResponse} с информацией об успешном удалении
     */
    DeleteScheduleResponse delete(UrlRequest request);

    /**
     * Создаёт новое расписание проверки сертификата.
     *
     * @param request объект {@link CreateScheduleRequest} с данными для создания расписания
     * @return {@link CreateScheduleResponse} с информацией об успешном добавлении
     */
    CreateScheduleResponse createSchedule(CreateScheduleRequest request);
}
