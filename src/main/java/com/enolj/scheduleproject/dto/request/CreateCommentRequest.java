package com.enolj.scheduleproject.dto.request;

import lombok.Getter;

@Getter
public class CreateCommentRequest {

    private String description;
    private String author;
    private String password;
}
