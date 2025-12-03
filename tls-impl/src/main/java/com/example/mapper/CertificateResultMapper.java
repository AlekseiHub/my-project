package com.example.mapper;

import com.example.dto.response.CertificateResultResponse;
import com.example.model.entity.CertificateCheckResult;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CertificateResultMapper {

    List<CertificateResultResponse> toListCertificateResultResponse(List<CertificateCheckResult> certificateCheckResult);

    CertificateCheckResult toCertificateResult(CertificateResultResponse certificateResultResponse);
}
