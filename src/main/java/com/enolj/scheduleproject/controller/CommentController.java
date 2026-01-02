package com.enolj.scheduleproject.controller;

import com.enolj.scheduleproject.dto.request.CreateCommentRequest;
import com.enolj.scheduleproject.dto.response.CreateCommentResponse;
import com.enolj.scheduleproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 일정 ID를 받아 해당 일정에 댓글을 생성하는 API
    @PostMapping("schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(@PathVariable Long scheduleId, @RequestBody CreateCommentRequest request) {
        CreateCommentResponse response = commentService.save(scheduleId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
