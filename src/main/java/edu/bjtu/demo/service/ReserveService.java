package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.Coach;
import edu.bjtu.demo.domain.UserCoach;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReserveService {
    UserCoach reserve(UserCoach usercoach);
    void cancel(UserCoach usercoach);
    Page<Coach> findBySubject(String subject,  Integer evalPageSize, Integer evalPage);
    Page<Coach> findAllCoaches(Integer evalPageSize, Integer evalPage);
}
