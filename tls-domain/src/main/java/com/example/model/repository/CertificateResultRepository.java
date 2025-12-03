package com.example.model.repository;

import com.example.model.entity.CertificateCheckResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateResultRepository extends JpaRepository<CertificateCheckResult,Long> {

    List<CertificateCheckResult> findAllBy();


}
