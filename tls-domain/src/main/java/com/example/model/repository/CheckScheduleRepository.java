package com.example.model.repository;

import com.example.model.entity.CheckSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link CheckSchedule}.
 *
 * Предоставляет стандартные CRUD операции через {@link JpaRepository},
 * а также кастомные методы для работы с расписаниями проверок сертификатов.
 */
public interface CheckScheduleRepository extends JpaRepository<CheckSchedule, Long> {

    /**
     * Находит расписание по URL.
     *
     * @param url URL веб-ресурса
     * @return {@link Optional} с {@link CheckSchedule}, если запись найдена, иначе пустой
     */
    Optional<CheckSchedule> findByUrl(String url);

    /**
     * Удаляет расписание по URL.
     *
     * возвращает количество удалённых строк.
     * @param url URL веб-ресурса
     * @return количество удалённых записей
     */
    Long deleteByUrl(String url);

    /**
     * Находит все активные расписания.
     *
     * @return список {@link CheckSchedule} с активными расписаниями
     */
    List<CheckSchedule> findAllByActiveTrue();
}