package com.enolj.scheduleproject.service;

import com.enolj.scheduleproject.dto.request.CreateScheduleRequest;
import com.enolj.scheduleproject.dto.request.DeleteScheduleRequest;
import com.enolj.scheduleproject.dto.request.UpdateScheduleRequest;
import com.enolj.scheduleproject.dto.response.*;
import com.enolj.scheduleproject.entity.Schedule;
import com.enolj.scheduleproject.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

    // request를 받아 일정을 저장하는 메서드
    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = Schedule.from(request);
        scheduleRepository.save(schedule);
        return CreateScheduleResponse.from(schedule);
    }

    // 작성자를 입력 받아 해당 작성자의 일정들을 보여주는 메서드, 작성자가 null일 경우 모든 일정 조회
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

    // 일정 단건 조회 하는 메서드, 일정에 달려있는 댓글들 포함하여 조회
    @Transactional(readOnly = true)
    public GetOneScheduleResponse getOne(Long scheduleId) {
        Schedule schedule = findById(scheduleId);
        List<GetCommentResponse> comments = commentRepository.findAllByScheduleId(scheduleId).stream()
                .map(GetCommentResponse::from)
                .toList();
        return GetOneScheduleResponse.from(schedule, comments);
    }

    // 일정 수정하는 메서드, 트랜잭션 환경에서는 수정일이 바로 변경이 안되기 떄문에 flush 로직 추가
    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = findById(scheduleId);
        matchPassword(schedule, request.getPassword());
        schedule.update(request);

        // 수정일 반영을 하기 위한 flush()
        scheduleRepository.flush();
        return UpdateScheduleResponse.from(schedule);
    }

    // 일정 id와 패스워드를 입력받아 일정을 삭제하는 메서드
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
