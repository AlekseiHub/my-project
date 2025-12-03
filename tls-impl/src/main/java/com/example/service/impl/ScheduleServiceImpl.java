package com.example.service.impl;

import com.example.dto.request.CreateScheduleRequest;
import com.example.dto.request.UrlRequest;
import com.example.dto.response.*;
import com.example.exceptions.AlreadyExistsException;
import com.example.exceptions.NotFoundCertificateResultException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final CheckScheduleRepository checkScheduleRepository;
    private final CertificateResultRepository checkCertificateResultRepository;
    private final ScheduleMapper scheduleMapper;
    private final CertificateResultMapper certificateResultMapper;
    private final ScheduleRunner scheduleRunner;

    @Override
    public ResponseEntity<List<CertificateResultResponse>> getResults() {

        List<CertificateCheckResult> certificateRep = checkCertificateResultRepository.findAllBy();
        if (certificateRep.isEmpty()) {
            throw new NotFoundCertificateResultException();
        }

        return ResponseEntity.ok(certificateResultMapper.toListCertificateResultResponse(certificateRep));
    }

    @Transactional
    @Override
    public ResponseEntity<DeleteScheduleResponse> delete(UrlRequest request) {

        Optional<CheckSchedule> schedule = checkScheduleRepository.findByUrl(request.url());
        log.info("Запрос на удаление расписания по URL: {}", request.url());

        if (schedule.isEmpty()) {
            log.warn("Удаление не выполнено — расписание с URL '{}' не найдено", request.url());
            throw new NotFoundCertificateResultException();
        }

        checkScheduleRepository.deleteByUrl(request.url());
        log.info("Расписание по URL '{}' успешно удалено", request.url());

        return ResponseEntity.ok(new DeleteScheduleResponse("Расписание по данному url удалено "));
    }

    @Transactional
    @Override
    public ResponseEntity<CreateScheduleResponse> createSchedule(CreateScheduleRequest request) {

        Optional<CheckSchedule> copyUrl = checkScheduleRepository.findByUrl(request.url());
        if(!copyUrl.isEmpty()) {
            throw new AlreadyExistsException();
        }

        CheckSchedule save =  checkScheduleRepository.save(scheduleMapper.toCheckSchedule(request));
        scheduleRunner.startSchedule(save);
        log.info("Рассписание по URL '{}' успешно добавлено", request.url());

        return ResponseEntity.ok(new CreateScheduleResponse("Расписание добавлено"));
    }
}