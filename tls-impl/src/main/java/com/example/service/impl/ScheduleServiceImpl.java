package com.example.service.impl;

import com.example.dto.request.CreateScheduleRequest;
import com.example.dto.request.UrlRequest;
import com.example.dto.response.*;
import com.example.exception.AlreadyExistsException;
import com.example.exception.NotFoundCertificateResultException;
import com.example.mapper.CertificateResultMapper;
import com.example.mapper.ScheduleMapper;
import com.example.model.entity.CertificateCheckResult;
import com.example.model.entity.CheckSchedule;
import com.example.model.repository.CertificateResultRepository;
import com.example.model.repository.CheckScheduleRepository;
import com.example.schedule.ScheduleRunner;
import com.example.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления расписаниями проверки SSL-сертификатов.
 *
 * Реализует интерфейс {@link ScheduleService} и предоставляет
 * функционал для создания, удаления расписаний и получения результатов проверок.
 * Основные функции:
 *
 * Создание нового расписания проверки сертификата
 * Удаление существующего расписания
 * Получение результатов всех проверок сертификатов
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final CheckScheduleRepository checkScheduleRepository;

    private final CertificateResultRepository checkCertificateResultRepository;

    private final ScheduleMapper scheduleMapper;

    private final CertificateResultMapper certificateResultMapper;

    private final ScheduleRunner scheduleRunner;

    /**
     * Получает список всех результатов проверок SSL-сертификатов.
     *
     * Если результатов нет, выбрасывает {@link NotFoundCertificateResultException}.
     *
     * @return список {@link CertificateResultResponse} с данными о проверках
     * @throws NotFoundCertificateResultException если список результатов пуст
     */
    @Transactional
    @Override
    public List<CertificateResultResponse> getResults() {

        List<CertificateCheckResult> certificateRep = checkCertificateResultRepository.findAllBy();
        if (certificateRep.isEmpty()) {
            log.info("Сертификаты не найдены");
            throw new NotFoundCertificateResultException();
        }

        log.info("Вывод списка сертификатов успешно выполнен ");
        return certificateResultMapper.toListCertificateResultResponse(certificateRep);
    }

    /**
     * Удаляет расписание проверки сертификата по указанному URL.
     *
     * Если расписание с указанным URL не найдено, выбрасывает {@link NotFoundCertificateResultException}.
     *
     * @param request объект {@link UrlRequest}, содержащий URL для удаления
     * @return {@link DeleteScheduleResponse} с информацией об удалении
     * @throws NotFoundCertificateResultException если расписание не найдено
     */
    @Transactional
    @Override
    public DeleteScheduleResponse delete(UrlRequest request) {

        Long delete = checkScheduleRepository.deleteByUrl(request.url());

        if (delete == null|| delete == 0L) {
            log.warn("Удаление не выполнено — расписание с URL '{}' не найдено", request.url());
            throw new NotFoundCertificateResultException();
        }

        log.info("Расписание по URL '{}' успешно удалено", request.url());
        return new DeleteScheduleResponse("Расписание по данному url удалено");
    }

    /**
     * Создаёт новое расписание проверки сертификата.
     *
     * Если расписание с указанным URL уже существует, выбрасывает {@link AlreadyExistsException}.
     * После сохранения нового расписания задача запускается в {@link ScheduleRunner}.
     *
     * @param request объект {@link CreateScheduleRequest} с данными для создания расписания
     * @return {@link CreateScheduleResponse} с информацией об успешном добавлении
     * @throws AlreadyExistsException если расписание с указанным URL уже существует
     */
    @Transactional
    @Override
    public CreateScheduleResponse createSchedule(CreateScheduleRequest request) {

        Optional<CheckSchedule> copyUrl = checkScheduleRepository.findByUrl(request.url());
        if(!copyUrl.isEmpty()) {
            throw new AlreadyExistsException();
        }

        CheckSchedule save =  checkScheduleRepository.save(scheduleMapper.toCheckSchedule(request));
        scheduleRunner.startSchedule(save);
        log.info("Рассписание по URL '{}' успешно добавлено", request.url());

        return new CreateScheduleResponse("Расписание добавлено");
    }
}