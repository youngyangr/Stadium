package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.Coach;
import edu.bjtu.demo.domain.User;
import edu.bjtu.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository UserRepository;

    @Autowired
    public void setUserRepository(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }

    @Override
    public Iterable<User> getAllUsers() {
        return this.UserRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return this.UserRepository.findById(id).orElse(null);
    }

    @Override
    public User saveUser(User User) {
        return this.UserRepository.save(User);
    }

    @Override
    public void deleteUser(Integer id) {
        this.UserRepository.deleteById(id);
    }

    @Override
    public boolean existUser(User user){
        List<User> list = this.UserRepository.findByUsername(user.getUsername());
        if(list.size() == 0 || list == null)
            return false;
        return true;
    }

    @Override
    public boolean checkUser(User user){
        if(existUser(user)){
            List<User> list = this.UserRepository.findByUsername(user.getUsername());
            if(list.get(0).getPassword().equals(user.getPassword())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Page<Coach> getBySubject(String subject, Integer id, Integer evalPageSize, Integer evalPage) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(evalPage, evalPageSize, sort);
        return this.UserRepository.getBySubject(subject, id, pageable);
    }

    @Override
    public Page<Coach> getAllCoaches(Integer id, Integer evalPageSize, Integer evalPage) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(evalPage, evalPageSize, sort);
        return this.UserRepository.getAllCoaches(id, pageable);
    }

}
