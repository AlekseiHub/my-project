package com.example.service;

import com.example.dto.request.CreateScheduleRequest;
import com.example.dto.request.UrlRequest;
import com.example.dto.response.CertificateResultResponse;
import com.example.dto.response.CreateScheduleResponse;
import com.example.dto.response.DeleteScheduleResponse;
import com.example.exception.AlreadyExistsException;
import com.example.exception.NotFoundCertificateResultException;
import com.example.mapper.CertificateResultMapper;
import com.example.mapper.ScheduleMapper;
import com.example.model.entity.CertificateCheckResult;
import com.example.model.entity.CheckSchedule;
import com.example.model.repository.CertificateResultRepository;
import com.example.model.repository.CheckScheduleRepository;
import com.example.schedule.ScheduleRunner;
import com.example.service.impl.ScheduleServiceImpl;
import com.example.util.TestConstant;
import com.example.util.TestGeneratorClasses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

    @Mock
    private CheckScheduleRepository checkScheduleRepository;

    @Mock
    private CertificateResultRepository certificateResultRepository;

    @Mock
    private ScheduleMapper scheduleMapper;

    @Mock
    private CertificateResultMapper certificateResultMapper;

    @Mock
    private ScheduleRunner scheduleRunner;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private CreateScheduleRequest createScheduleRequest;
    private String testUrl;
    private UrlRequest urlRequest;

    @BeforeEach
    void setUp() {
        createScheduleRequest = TestGeneratorClasses.createScheduleRequest();
        urlRequest = TestGeneratorClasses.createUrlRequest();
        testUrl = TestConstant.TEST_URL;
    }

    @Test
    void testGetResults_Success() {
        List<CertificateCheckResult> results = new ArrayList<>();
        CertificateCheckResult result = new CertificateCheckResult();
        results.add(result);

        List<CertificateResultResponse> responseList = List.of(mock(CertificateResultResponse.class));

        when(certificateResultRepository.findAllBy()).thenReturn(results);
        when(certificateResultMapper.toListCertificateResultResponse(results)).thenReturn(responseList);

        List<CertificateResultResponse> resultList = scheduleService.getResults();

        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        verify(certificateResultRepository, times(1)).findAllBy();
        verify(certificateResultMapper, times(1)).toListCertificateResultResponse(results);
    }

    @Test
    void testGetResults_EmptyList_ThrowsException() {

        when(certificateResultRepository.findAllBy()).thenReturn(new ArrayList<>());

        assertThrows(NotFoundCertificateResultException.class, () -> scheduleService.getResults());
        verify(certificateResultRepository, times(1)).findAllBy();
        verifyNoInteractions(certificateResultMapper);
    }

    @Test
    void testDelete_Success() {

        when(checkScheduleRepository.deleteByUrl(testUrl)).thenReturn(1L);

        DeleteScheduleResponse response = scheduleService.delete(urlRequest);

        assertNotNull(response);
        assertEquals("Расписание по данному url удалено", response.message());
        verify(checkScheduleRepository, times(1)).deleteByUrl(testUrl);
    }

    @Test
    void testDelete_NotFound_ThrowsException() {

        when(checkScheduleRepository.deleteByUrl(testUrl)).thenReturn(0L);

        assertThrows(NotFoundCertificateResultException.class, () -> scheduleService.delete(urlRequest));
        verify(checkScheduleRepository, times(1)).deleteByUrl(testUrl);
    }

    @Test
    void testCreateSchedule_Success() {

        CheckSchedule mappedSchedule = mock(CheckSchedule.class);

        when(checkScheduleRepository.findByUrl(testUrl)).thenReturn(Optional.empty());
        when(scheduleMapper.toCheckSchedule(createScheduleRequest)).thenReturn(mappedSchedule);
        when(checkScheduleRepository.save(mappedSchedule)).thenReturn(mappedSchedule);

        CreateScheduleResponse response = scheduleService.createSchedule(createScheduleRequest);

        assertNotNull(response);
        assertEquals("Расписание добавлено", response.message());
        verify(scheduleMapper, times(1)).toCheckSchedule(createScheduleRequest);
        verify(checkScheduleRepository, times(1)).save(mappedSchedule);
        verify(scheduleRunner, times(1)).startSchedule(mappedSchedule);
    }

    @Test
    void testCreateSchedule_AlreadyExists_ThrowsException() {

        CheckSchedule existingSchedule = mock(CheckSchedule.class);

        when(checkScheduleRepository.findByUrl(testUrl)).thenReturn(Optional.of(existingSchedule));

        assertThrows(AlreadyExistsException.class, () -> scheduleService.createSchedule(createScheduleRequest));
        verify(checkScheduleRepository, never()).save(any());
        verify(scheduleRunner, never()).startSchedule(any());
    }
}