package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.Coach;
import edu.bjtu.demo.domain.UserCoach;
import edu.bjtu.demo.repository.UserCoachRepository;
import edu.bjtu.demo.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReserveServiceImpl implements ReserveService{
    private UserCoachRepository UserCoachRepository;
    private CoachRepository CoachRepository;

    @Autowired
    public void setUserCoachRepository(UserCoachRepository UserCoachRepository) {
        this.UserCoachRepository = UserCoachRepository;
    }

    @Autowired
    public void setUserRepository(CoachRepository CoachRepository) {
        this.CoachRepository = CoachRepository;
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
    public Page<Coach> findBySubject(String subject, Integer evalPageSize, Integer evalPage){
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(evalPage, evalPageSize, sort);
        return this.CoachRepository.findBySubject(subject,pageable);
    }

    @Override
    public Page<Coach> findAllCoaches(Integer evalPageSize, Integer evalPage) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(evalPage, evalPageSize, sort);
        return this.CoachRepository.findAll(pageable);
    }
}
