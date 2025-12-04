package com.example.service;

import com.example.model.entity.CertificateCheckResult;
import com.example.model.entity.CheckSchedule;
import com.example.model.repository.CertificateResultRepository;
import com.example.service.impl.CertificateCheckerServiceImpl;
import com.example.util.TestConstant;
import com.example.util.TestGeneratorClasses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CertificateCheckerServiceImplTest {

    @Mock
    private CertificateResultRepository certificateResultRepository;

    @InjectMocks
    private CertificateCheckerServiceImpl certificateCheckerService;

    @Mock
    private CheckSchedule checkSchedule;

    private CertificateCheckerServiceImpl spyService;

    private CertificateCheckResult certificateCheckResult;

    private String testUrl;
    private String subject;
    private String issuer;

    @BeforeEach
    void setUp() {

        issuer = TestConstant.ISSUER;
        subject = TestConstant.SUBJECT;
        testUrl = TestConstant.TEST_URL;
        certificateCheckResult = TestGeneratorClasses.certificateCheckResult();
        spyService = spy(certificateCheckerService);

        lenient().when(checkSchedule.getId()).thenReturn(1L);
        lenient().when(checkSchedule.getUrl()).thenReturn(testUrl);
    }

    @Test
    void testCheckAndSave_SaveCalled() {

        doReturn(certificateCheckResult).when(spyService).check(checkSchedule);

        spyService.checkAndSave(checkSchedule);

        verify(certificateResultRepository, times(1)).save(certificateCheckResult);
    }

    @Test
    void testCheck_ReturnsValidResult() {

        doReturn(certificateCheckResult).when(spyService).check(checkSchedule);

        CertificateCheckResult result = spyService.check(checkSchedule);

        assertNotNull(result);
        assertEquals(subject, result.getSubject());
        assertEquals(issuer, result.getIssuer());
        assertTrue(result.getValid());
    }

    @Test
    void testCheckAndSave_ExceptionThrown() {

        doThrow(new RuntimeException("Ошибка проверки сертификата"))
                .when(spyService)
                .check(checkSchedule);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> spyService.checkAndSave(checkSchedule));

        assertTrue(ex.getMessage().contains("Ошибка проверки сертификата"));

        verify(certificateResultRepository, never()).save(any());
    }
}