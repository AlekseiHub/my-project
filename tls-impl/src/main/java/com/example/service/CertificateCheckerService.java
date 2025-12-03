package com.example.service;

import com.example.dto.response.CertificateResultResponse;
import com.example.model.entity.CertificateCheckResult;
import com.example.model.entity.CheckSchedule;

public interface CertificateCheckerService {

    void checkAndSave(CheckSchedule schedule);

    CertificateCheckResult check(CheckSchedule checkSchedule);
}
