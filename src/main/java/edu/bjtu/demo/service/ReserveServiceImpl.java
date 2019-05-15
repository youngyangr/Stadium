package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.UserCoach;
import edu.bjtu.demo.repository.UserCoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReserveServiceImpl implements ReserveService{
    private UserCoachRepository UserCoachRepository;

    @Autowired
    public void setUserCoachRepository(UserCoachRepository UserCoachRepository) {
        this.UserCoachRepository = UserCoachRepository;
    }

    @Override
    public UserCoach reserve(UserCoach usercoach) {
        return this.UserCoachRepository.save(usercoach);
    }

    @Override
    public void cancel(UserCoach usercoach) {
        this.UserCoachRepository.delete(usercoach);
    }

    @Override
    public Iterable<UserCoach> listOrders() {
        return this.UserCoachRepository.findAll();
    }

}
