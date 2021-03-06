package edu.bjtu.demo.repository;

import edu.bjtu.demo.domain.Coach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CoachRepository extends JpaRepository<Coach,Integer> {
    Page<Coach> findBySubject(String subject, Pageable pageable);
    Iterable<Coach> findBySubject(String subject);
}
