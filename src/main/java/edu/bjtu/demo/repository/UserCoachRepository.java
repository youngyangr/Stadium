package edu.bjtu.demo.repository;

import edu.bjtu.demo.domain.UserCoach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCoachRepository extends JpaRepository<UserCoach, Integer> {
}
