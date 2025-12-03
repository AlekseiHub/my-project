package com.example.model.repository;

import com.example.model.entity.CheckSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheckScheduleRepository extends JpaRepository<CheckSchedule, Long> {

    Optional<CheckSchedule> findByUrl(String url);

    void deleteByUrl(String url);

    List<CheckSchedule> findAllByActiveTrue();

}
