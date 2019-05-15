package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.Coach;
import edu.bjtu.demo.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CoachServiceImpl implements CoachService{

    private CoachRepository CoachRepository;

    @Autowired
    public void setCoachRepository(CoachRepository CoachRepository) {
        this.CoachRepository = CoachRepository;
    }

    @Override
    public Iterable<Coach> findAllCoaches() {
        return this.CoachRepository.findAll();
    }

    @Override
    public Iterable<Coach> findCoachBySubject(String subject) {
        return this.CoachRepository.findBySubject(subject);
    }

    @Override
    public Page<Coach> findCoachBySubject(String subject, Integer evalPageSize, Integer evalPage){
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
