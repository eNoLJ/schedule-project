package com.enolj.scheduleproject.repository;

import com.enolj.scheduleproject.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
