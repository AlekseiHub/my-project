package com.example.controller.impl;

import com.example.controller.ScheduleController;
import com.example.dto.request.CreateScheduleRequest;
import com.example.dto.response.CertificateResultDto;
import com.example.dto.response.CreateScheduleResponse;
import com.example.dto.response.DeleteScheduleResponse;
import com.example.service.ScheduleService;
import com.example.service.impl.ScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schecules")
@RequiredArgsConstructor
public class ScheduleControllerImpl implements ScheduleController {

    private final ScheduleService scheduleService;

    @Override
    @GetMapping("/{id}/results")
    public ResponseEntity<CertificateResultDto> getResults(Long id) {
        return scheduleService.getResults(id);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<CreateScheduleResponse> createSchedule(@RequestBody CreateScheduleRequest request) {
        return scheduleService.createSchedule(request);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteScheduleResponse> deleteSchedule(Long id) {
        return scheduleService.delete(id);
    }

}
