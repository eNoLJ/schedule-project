package com.enolj.scheduleproject.controller;

import com.enolj.scheduleproject.dto.request.CreateScheduleRequest;
import com.enolj.scheduleproject.dto.response.CreateScheduleResponse;
import com.enolj.scheduleproject.dto.response.GetScheduleResponse;
import com.enolj.scheduleproject.dto.response.UpdateScheduleResponse;
import com.enolj.scheduleproject.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(@RequestBody CreateScheduleRequest request) {
        CreateScheduleResponse response = scheduleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getSchedule(@PathVariable Long scheduleId) {
        GetScheduleResponse response = scheduleService.findById(scheduleId);
        return ResponseEntity.ok(response);
    }
}
