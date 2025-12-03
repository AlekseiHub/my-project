package com.example.mapper;

import com.example.dto.request.CreateScheduleRequest;
import com.example.model.entity.CheckSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ScheduleMapper {

    CheckSchedule toCheckSchedule(CreateScheduleRequest request);
}
