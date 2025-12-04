package com.example.model.repository;

import com.example.model.entity.CertificateCheckResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link CertificateCheckResult}.
 *
 * Предоставляет стандартные CRUD операции через {@link JpaRepository},
 * а также кастомный метод для получения всех записей о проверках сертификатов.
 */
public interface CertificateResultRepository extends JpaRepository<CertificateCheckResult,Long> {

    /**
     * Получает список всех результатов проверок сертификатов.
     *
     * @return список {@link CertificateCheckResult} с данными о проверках
     */
    List<CertificateCheckResult> findAllBy();
}