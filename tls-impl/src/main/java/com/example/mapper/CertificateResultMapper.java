package com.example.mapper;

import com.example.dto.response.CertificateResultResponse;
import com.example.model.entity.CertificateCheckResult;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

/**
 * Mapper для преобразования сущностей {@link CertificateCheckResult} в DTO {@link CertificateResultResponse}.
 *
 * Используется для подготовки данных о результатах проверки сертификатов
 * к возвращению клиенту через REST API.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CertificateResultMapper {

    List<CertificateResultResponse> toListCertificateResultResponse(List<CertificateCheckResult> certificateCheckResult);
}