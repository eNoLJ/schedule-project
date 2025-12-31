package com.enolj.scheduleproject.service;

import com.enolj.scheduleproject.dto.request.CreateCommentRequest;
import com.enolj.scheduleproject.dto.response.CreateCommentResponse;
import com.enolj.scheduleproject.entity.Comment;
import com.enolj.scheduleproject.repository.CommentRepository;
import com.enolj.scheduleproject.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    // 일정 id와 댓글 내용을 입력 받아 해당 일정에 댓글을 저장하는 메서드
    @Transactional
    public CreateCommentResponse save(Long scheduleId, CreateCommentRequest request) {
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new IllegalArgumentException("해당 일정이 없습니다.");
        }

        // 일정에 댓글이 10개 이상이면 더 이상 댓글을 달 수 없도록 제한
        List<Comment> commentList = commentRepository.findAllByScheduleId(scheduleId);
        if (commentList.size() >= 10) {
            throw new IllegalArgumentException("더 이상 댓글을 작성할 수 없습니다.");
        }

        Comment comment = Comment.from(scheduleId, request);
        commentRepository.save(comment);
        return CreateCommentResponse.from(comment);
    }
}
