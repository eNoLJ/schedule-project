package com.enolj.scheduleproject.dto.response;

import com.enolj.scheduleproject.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetCommentResponse {

    private final Long id;
    private final String description;
    private final String author;
    private final Long scheduleId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static GetCommentResponse from(Comment comment) {
        return GetCommentResponse.builder()
                .id(comment.getId())
                .description(comment.getDescription())
                .author(comment.getAuthor())
                .scheduleId(comment.getScheduleId())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
