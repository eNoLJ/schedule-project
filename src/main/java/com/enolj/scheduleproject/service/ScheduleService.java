package com.enolj.scheduleproject.service;

import com.enolj.scheduleproject.dto.request.CreateScheduleRequest;
import com.enolj.scheduleproject.dto.response.CreateScheduleResponse;
import com.enolj.scheduleproject.entity.Schedule;
import com.enolj.scheduleproject.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
