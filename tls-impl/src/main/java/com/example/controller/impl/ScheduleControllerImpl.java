package com.example.controller.impl;

import com.example.controller.ScheduleController;
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
@Validated
@RestController
@RequestMapping("/api/schecules")
@RequiredArgsConstructor
public class ScheduleControllerImpl implements ScheduleController {

    private final ScheduleService scheduleService;

    @Override
    @GetMapping("/results")
    public ResponseEntity<List<CertificateResultResponse>> getResults() {
        return scheduleService.getResults();
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<CreateScheduleResponse> createSchedule(@Valid @RequestBody CreateScheduleRequest request) {
        return scheduleService.createSchedule(request);
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<DeleteScheduleResponse> deleteSchedule(@Valid @RequestBody UrlRequest request) {
        return scheduleService.delete(request);
    }
}