package com.enolj.scheduleproject.controller;

import com.enolj.scheduleproject.dto.request.CreateScheduleRequest;
import com.enolj.scheduleproject.dto.request.DeleteScheduleRequest;
import com.enolj.scheduleproject.dto.request.UpdateScheduleRequest;
import com.enolj.scheduleproject.dto.response.CreateScheduleResponse;
import com.enolj.scheduleproject.dto.response.GetOneScheduleResponse;
import com.enolj.scheduleproject.dto.response.GetScheduleResponse;
import com.enolj.scheduleproject.dto.response.UpdateScheduleResponse;
import com.enolj.scheduleproject.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성 API
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(@RequestBody CreateScheduleRequest request) {
        CreateScheduleResponse response = scheduleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 일정 다건 조회 API
    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getAllSchedules(@RequestParam(required = false) String author) {
        List<GetScheduleResponse> response = scheduleService.getAll(author);
        return ResponseEntity.ok(response);
    }

    // 일정 단건 조회 API
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetOneScheduleResponse> getSchedule(@PathVariable Long scheduleId) {
        GetOneScheduleResponse response = scheduleService.getOne(scheduleId);
        return ResponseEntity.ok(response);
    }

    // 일정 수정 API
    @PatchMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(@PathVariable Long scheduleId, @RequestBody UpdateScheduleRequest request) {
        UpdateScheduleResponse response = scheduleService.update(scheduleId, request);
        return ResponseEntity.ok(response);
    }

    // 일정 삭제 API
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId, @RequestBody DeleteScheduleRequest request) {
        scheduleService.delete(scheduleId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
