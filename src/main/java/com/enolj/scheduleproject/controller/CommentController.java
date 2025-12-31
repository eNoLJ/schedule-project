package com.enolj.scheduleproject.controller;

import com.enolj.scheduleproject.dto.request.CreateCommentRequest;
import com.enolj.scheduleproject.dto.response.CreateCommentResponse;
import com.enolj.scheduleproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(@PathVariable Long scheduleId, @RequestBody CreateCommentRequest request) {
        CreateCommentResponse response = commentService.save(scheduleId, request);
        return ResponseEntity.ok(response);
    }
}
