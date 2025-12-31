package com.enolj.scheduleproject.repository;

import com.enolj.scheduleproject.dto.response.GetScheduleResponse;
import com.enolj.scheduleproject.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByAuthor(String author);
}
