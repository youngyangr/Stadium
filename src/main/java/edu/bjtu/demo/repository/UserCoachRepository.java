package edu.bjtu.demo.repository;

import edu.bjtu.demo.domain.UserCoach;
import edu.bjtu.demo.domain.UserCoachPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCoachRepository extends JpaRepository<UserCoach, UserCoachPK> {
}
