package com.example.service;

import com.example.dto.request.CreateScheduleRequest;
import com.example.dto.response.CertificateResultDto;
import com.example.dto.response.CreateScheduleResponse;
import com.example.dto.response.DeleteScheduleResponse;
import org.springframework.http.ResponseEntity;

public interface ScheduleService {

    public ResponseEntity<CertificateResultDto> getResults(Long id);

    public ResponseEntity<DeleteScheduleResponse> delete(Long id);

    public ResponseEntity<CreateScheduleResponse> createSchedule(CreateScheduleRequest request);
}
