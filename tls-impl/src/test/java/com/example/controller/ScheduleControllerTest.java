package com.example.controller;

import com.example.dto.request.CreateScheduleRequest;
import com.example.dto.request.UrlRequest;
import com.example.dto.response.CertificateResultResponse;
import com.example.dto.response.CreateScheduleResponse;
import com.example.dto.response.DeleteScheduleResponse;
import com.example.service.ScheduleService;
import com.example.util.TestConstant;
import com.example.util.TestGeneratorClasses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleControllerTest {

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private ScheduleController scheduleController;

    private CreateScheduleRequest createScheduleRequest;
    private UrlRequest urlRequest;
    private CreateScheduleResponse createScheduleResponse;
    private DeleteScheduleResponse deleteScheduleResponse;
    private List<CertificateResultResponse> certificateResultResponses;

    @BeforeEach
    void setUp() {
        createScheduleRequest = TestGeneratorClasses.createScheduleRequest();
        urlRequest = new UrlRequest(TestConstant.TEST_URL);
        createScheduleResponse = TestGeneratorClasses.createScheduleResponse();
        deleteScheduleResponse = TestGeneratorClasses.deleteScheduleResponse();
        certificateResultResponses = List.of(TestGeneratorClasses.certificateResultResponse());
    }

    @Test
    void testGetResults() {
        when(scheduleService.getResults()).thenReturn(certificateResultResponses);

        ResponseEntity<List<CertificateResultResponse>> response = scheduleController.getResults();

        assertNotNull(response);
        assertEquals(200,response.getStatusCode().value());
        assertEquals(certificateResultResponses, response.getBody());
        verify(scheduleService, times(1)).getResults();
    }

    @Test
    void testCreateSchedule() {
        when(scheduleService.createSchedule(createScheduleRequest)).thenReturn(createScheduleResponse);

        ResponseEntity<CreateScheduleResponse> response = scheduleController.createSchedule(createScheduleRequest);

        assertNotNull(response);
        assertEquals(200,response.getStatusCode().value());
        assertEquals(createScheduleResponse, response.getBody());
        verify(scheduleService, times(1)).createSchedule(createScheduleRequest);
    }

    @Test
    void testDeleteSchedule() {
        when(scheduleService.delete(urlRequest)).thenReturn(deleteScheduleResponse);

        ResponseEntity<DeleteScheduleResponse> response = scheduleController.deleteSchedule(urlRequest);

        assertNotNull(response);
        assertEquals(200,response.getStatusCode().value());
        assertEquals(deleteScheduleResponse, response.getBody());
        verify(scheduleService, times(1)).delete(urlRequest);
    }
}