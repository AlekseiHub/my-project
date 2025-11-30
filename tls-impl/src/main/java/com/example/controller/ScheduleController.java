package com.example.controller;


import com.example.dto.request.CreateScheduleRequest;
import com.example.dto.response.CertificateResultDto;
import com.example.dto.response.CreateScheduleResponse;
import com.example.dto.response.DeleteScheduleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ScheduleController {

    public ResponseEntity<CertificateResultDto> getResults(@PathVariable Long id);

    public ResponseEntity<CreateScheduleResponse> createSchedule(@RequestBody CreateScheduleRequest request);

    public ResponseEntity<DeleteScheduleResponse> deleteSchedule(@PathVariable Long id);

}
