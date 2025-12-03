package com.example.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "certificate_check_result")
@Entity
public class CertificateCheckResult {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "schedule_id", nullable = false)
    private Long scheduleId;

    @Column(name = "check_time", nullable = false)
    private LocalDateTime checkTime;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "issuer", nullable = false)
    private String issuer;

    @Column(name = "not_before", nullable = false)
    private LocalDateTime notBefore;

    @Column(name = "not_after", nullable = false)
    private LocalDateTime notAfter;

    @Column(name = "valid", nullable = false)
    private Boolean valid;

    @Column(name = "raw_json", columnDefinition = "TEXT")
    private String rawJson;
}