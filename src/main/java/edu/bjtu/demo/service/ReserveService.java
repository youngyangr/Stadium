package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.UserCoach;


public interface ReserveService {
    UserCoach reserve(UserCoach usercoach);
    void cancel(UserCoach usercoach);
}
