package edu.bjtu.demo.repository;

import edu.bjtu.demo.domain.Coach;
import edu.bjtu.demo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUsername(String username);

    @Query(value = "SELECT NEW edu.bjtu.demo.domain.Coach(c.id, c.name, c.subject) from User as u, Coach as c, UserCoach as uc"
            + " where u.id = uc.userId and c.id = uc.coachId and c.subject = ?1 and u.id = ?2")
    Page<Coach> getBySubject(String subject, Integer id, Pageable pageable);

    @Query(value = "SELECT NEW edu.bjtu.demo.domain.Coach(c.id,c.name,c.subject) from User as u, Coach as c, UserCoach as uc"
            + " where u.id = uc.userId and c.id = uc.coachId and u.id = ?1")
    Page<Coach> getAllCoaches(Integer id, Pageable pageable);

    @Query(value = "SELECT NEW edu.bjtu.demo.domain.Coach(c.id, c.name, c.subject) from User as u, Coach as c, UserCoach as uc"
            + " where u.id = uc.userId and c.id = uc.coachId and c.subject = ?1 and u.id = ?2")
    Iterable<Coach> getBySubject(String subject, Integer id);

    @Query(value = "SELECT NEW edu.bjtu.demo.domain.Coach(c.id,c.name,c.subject) from User as u, Coach as c, UserCoach as uc"
            + " where u.id = uc.userId and c.id = uc.coachId and u.id = ?1")
    Iterable<Coach> getAllCoaches(Integer id);
}
