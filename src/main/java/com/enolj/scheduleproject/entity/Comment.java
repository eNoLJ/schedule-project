package com.enolj.scheduleproject.entity;

import com.enolj.scheduleproject.dto.request.CreateCommentRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String description;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Long scheduleId;

    public static Comment from(Long scheduleId, CreateCommentRequest request) {
        return Comment.builder()
                .description(request.getDescription())
                .author(request.getAuthor())
                .password(request.getPassword())
                .scheduleId(scheduleId)
                .build();
    }
}
