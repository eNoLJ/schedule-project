package com.enolj.scheduleproject.dto.response;

import com.enolj.scheduleproject.entity.Schedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetOneScheduleResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final String author;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<GetCommentResponse> comments;

    public static GetOneScheduleResponse from(Schedule schedule, List<GetCommentResponse> comments) {
        return GetOneScheduleResponse.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .description(schedule.getDescription())
                .author(schedule.getAuthor())
                .createdAt(schedule.getCreatedAt())
                .modifiedAt(schedule.getModifiedAt())
                .comments(comments)
                .build();
    }
}
