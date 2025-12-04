package com.example.util;

import com.example.dto.request.CreateScheduleRequest;
import com.example.dto.request.UrlRequest;
import com.example.dto.response.CertificateResultResponse;
import com.example.dto.response.CreateScheduleResponse;
import com.example.dto.response.DeleteScheduleResponse;
import com.example.model.entity.CertificateCheckResult;

import java.time.LocalDateTime;

public class TestGeneratorClasses {

    public static CreateScheduleRequest createScheduleRequest() {
        return CreateScheduleRequest.builder()
                .url(TestConstant.TEST_URL)
                .cronExpression(TestConstant.CRON)
                .build();
    }

    public static CreateScheduleResponse createScheduleResponse(){
        return CreateScheduleResponse.builder()
                .message("Расписание добавлено")
                .build();
    }

    public static DeleteScheduleResponse deleteScheduleResponse(){
        return DeleteScheduleResponse.builder()
                .message("Расписание по данному url удалено")
                .build();
    }

    public static UrlRequest createUrlRequest() {
        return UrlRequest.builder()
                .url(TestConstant.TEST_URL)
                .build();
    }

    public static CertificateCheckResult certificateCheckResult() {
        return CertificateCheckResult.builder()
                .scheduleId(1L)
                .checkTime(LocalDateTime.now())
                .subject(TestConstant.SUBJECT)
                .issuer(TestConstant.ISSUER)
                .notBefore(LocalDateTime.now().minusDays(1))
                .notAfter(LocalDateTime.now().plusDays(1))
                .valid(true)
                .rawJson("{}")
                .build();
    }

    public static CertificateResultResponse certificateResultResponse(){
        return CertificateResultResponse.builder()
                .rawCertificateJson("{subject:" + TestConstant.SUBJECT + ", issuer:" + TestConstant.ISSUER + "}")
                .subject(TestConstant.SUBJECT)
                .valid(true)
                .checkTime(LocalDateTime.now())
                .notBefore(LocalDateTime.now().minusDays(1))
                .notAfter(LocalDateTime.now().plusDays(365))
                .issuer(TestConstant.ISSUER)
                .build();

    }
}