package com.enolj.scheduleproject.service;

import com.enolj.scheduleproject.dto.request.CreateScheduleRequest;
import com.enolj.scheduleproject.dto.response.CreateScheduleResponse;
import com.enolj.scheduleproject.dto.response.GetScheduleResponse;
import com.enolj.scheduleproject.entity.Schedule;
import com.enolj.scheduleproject.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = Schedule.from(request);
        scheduleRepository.save(schedule);
        return CreateScheduleResponse.from(schedule);
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse findById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("해당 일정이 없습니다.")
        );
        return GetScheduleResponse.from(schedule);
    }
}
