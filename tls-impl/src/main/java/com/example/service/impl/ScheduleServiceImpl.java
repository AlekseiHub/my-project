package com.example.service.impl;

import com.example.dto.request.CreateScheduleRequest;
import com.example.dto.response.CertificateResultDto;
import com.example.dto.response.CreateScheduleResponse;
import com.example.dto.response.DeleteScheduleResponse;
import com.example.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    @Override
    public ResponseEntity<CertificateResultDto> getResults(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<DeleteScheduleResponse> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CreateScheduleResponse> createSchedule(CreateScheduleRequest request) {
        return null;
    }
}
