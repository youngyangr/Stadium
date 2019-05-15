package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.Coach;
import org.springframework.data.domain.Page;

public interface CoachService {
    Iterable<Coach> findAllCoaches();
    Iterable<Coach> findCoachBySubject(String subject);
    Page<Coach> findCoachBySubject(String subject, Integer evalPageSize, Integer evalPage);
    Page<Coach> findAllCoaches(Integer evalPageSize, Integer evalPage);
}
