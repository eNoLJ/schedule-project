package com.enolj.scheduleproject.entity;

import com.enolj.scheduleproject.dto.request.CreateScheduleRequest;
import com.enolj.scheduleproject.dto.request.UpdateScheduleRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "Schedules")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30, nullable = false)
    private String title;
    @Column(length = 200, nullable = false)
    private String description;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String password;

    public static Schedule from(CreateScheduleRequest request) {
        return Schedule.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .author(request.getAuthor())
                .password(request.getPassword())
                .build();
    }

    public void update(UpdateScheduleRequest request) {
        this.title = request.getTitle() == null ? this.title : request.getTitle();
        this.author = request.getAuthor() == null ? this.author : request.getAuthor();
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }
}
