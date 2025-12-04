package com.example.mapper;

import com.example.dto.request.CreateScheduleRequest;
import com.example.model.entity.CheckSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Mapper для преобразования DTO {@link CreateScheduleRequest} в сущность {@link CheckSchedule}.
 *
 * Используется для конвертации входных данных от клиента (создание нового расписания)
 * в объект сущности, который можно сохранять в базу данных.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ScheduleMapper {

    CheckSchedule toCheckSchedule(CreateScheduleRequest request);
}
