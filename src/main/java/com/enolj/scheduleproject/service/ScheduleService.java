package com.enolj.scheduleproject.service;

import com.enolj.scheduleproject.dto.request.CreateScheduleRequest;
import com.enolj.scheduleproject.dto.request.DeleteScheduleRequest;
import com.enolj.scheduleproject.dto.request.UpdateScheduleRequest;
import com.enolj.scheduleproject.dto.response.CreateScheduleResponse;
import com.enolj.scheduleproject.dto.response.GetScheduleResponse;
import com.enolj.scheduleproject.dto.response.UpdateScheduleResponse;
import com.enolj.scheduleproject.entity.Schedule;
import com.enolj.scheduleproject.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

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
    public List<GetScheduleResponse> getAll(String author) {
        List<Schedule> schedules = (author == null)
                ?  scheduleRepository.findAll()
                : scheduleRepository.findAllByAuthor(author);

        return schedules.stream()
                .map(GetScheduleResponse::from)
                .sorted(Comparator.comparing(GetScheduleResponse::getModifiedAt).reversed())
                .toList();
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse getOne(Long scheduleId) {
        Schedule schedule = findById(scheduleId);
        return GetScheduleResponse.from(schedule);
    }

    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = findById(scheduleId);
        matchPassword(schedule, request.getPassword());
        schedule.update(request);
        return UpdateScheduleResponse.from(schedule);
    }

    @Transactional
    public void delete(Long scheduleId, DeleteScheduleRequest request) {
        Schedule schedule = findById(scheduleId);
        matchPassword(schedule, request.getPassword());
        scheduleRepository.delete(schedule);
    }

    private Schedule findById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("해당 일정이 없습니다.")
        );
    }

    private void matchPassword(Schedule schedule, String password) {
        if (!schedule.matchPassword(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
