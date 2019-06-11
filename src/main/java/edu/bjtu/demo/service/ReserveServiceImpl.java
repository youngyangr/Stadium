package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.UserCoach;
import edu.bjtu.demo.domain.UserCoachPK;
import edu.bjtu.demo.repository.UserCoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.util.concurrent.RateLimiter;

import java.util.Optional;

@Service
public class ReserveServiceImpl implements ReserveService{
    private UserCoachRepository UserCoachRepository;
    private RateLimiter rateLimiter = RateLimiter.create(5.0);

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
        UserCoachPK pk = new UserCoachPK();
        pk.setUserId(usercoach.getUserId());
        pk.setCoachId(usercoach.getCoachId());
        Optional<UserCoach> delete = this.UserCoachRepository.findById(pk);
        this.UserCoachRepository.delete(delete.get());
    }

    @Override
    public Iterable<UserCoach> listOrders() {
        rateLimiter.acquire();
        return this.UserCoachRepository.findAll();
    }

}
