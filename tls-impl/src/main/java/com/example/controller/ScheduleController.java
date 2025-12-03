package com.example.controller;


import com.example.dto.request.CreateScheduleRequest;
import com.example.dto.request.UrlRequest;
import com.example.dto.response.CertificateResultResponse;
import com.example.dto.response.CreateScheduleResponse;
import com.example.dto.response.DeleteScheduleResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ScheduleController {

    ResponseEntity<List<CertificateResultResponse>> getResults();

    ResponseEntity<CreateScheduleResponse> createSchedule(@Valid @RequestBody CreateScheduleRequest request);

    ResponseEntity<DeleteScheduleResponse> deleteSchedule(@Valid @RequestBody UrlRequest request);

}
