package com.example.service;

import com.example.dto.request.CreateScheduleRequest;
import com.example.dto.request.UrlRequest;
import com.example.dto.response.CertificateResultResponse;
import com.example.dto.response.CreateScheduleResponse;
import com.example.dto.response.DeleteScheduleResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ScheduleService {

    ResponseEntity<List<CertificateResultResponse>> getResults();

    ResponseEntity<DeleteScheduleResponse> delete(UrlRequest request);

    ResponseEntity<CreateScheduleResponse> createSchedule(CreateScheduleRequest request);
}
