package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.UserCoach;
import edu.bjtu.demo.repository.UserCoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.util.concurrent.RateLimiter;

@Service
public class ReserveServiceImpl implements ReserveService{
    private UserCoachRepository UserCoachRepository;
    private RateLimiter rateLimiter = RateLimiter.create(1); // rate is "10 permits per second"

    @Autowired
    public void setUserCoachRepository(UserCoachRepository UserCoachRepository) {
        this.UserCoachRepository = UserCoachRepository;
    }

    @Override
    public UserCoach reserve(UserCoach usercoach) {
        rateLimiter.acquire();
        return this.UserCoachRepository.save(usercoach);
    }

    @Override
    public void cancel(UserCoach usercoach) {
        rateLimiter.acquire();
        this.UserCoachRepository.delete(usercoach);
    }

    @Override
    public Iterable<UserCoach> listOrders() {
        rateLimiter.acquire();
        return this.UserCoachRepository.findAll();
    }

}
