package com.enolj.scheduleproject.dto.response;

import com.enolj.scheduleproject.entity.Schedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UpdateScheduleResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final String author;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static UpdateScheduleResponse from(Schedule schedule) {
        return UpdateScheduleResponse.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .description(schedule.getDescription())
                .author(schedule.getAuthor())
                .createdAt(schedule.getCreatedAt())
                .modifiedAt(schedule.getModifiedAt())
                .build();
    }
}
