package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.Coach;
import edu.bjtu.demo.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Iterable<User> getAllUsers();
    User getUserById(Integer id);
    User saveUser(User user);
    void deleteUser(Integer id);
    boolean existUser(User user);
    boolean checkUser(User user);
    Iterable<Coach> getBySubject(Integer id, String subject);
    Iterable<Coach> getAllCoaches(Integer id);
    Page<Coach> getBySubject(String subject, Integer id, Integer evalPageSize, Integer evalPage);
    Page<Coach> getAllCoaches(Integer id, Integer evalPageSize, Integer evalPage);
}
