package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.Coach;
import edu.bjtu.demo.domain.User;
import edu.bjtu.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.UserRepository.findSingleByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

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
    public User saveUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword().trim()));
        return this.UserRepository.save(user);
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
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encode = list.get(0).getPassword();
            String password = user.getPassword();
            if(encoder.matches(password, encode)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterable<Coach> getBySubject(Integer id, String subject) {
        return this.UserRepository.getBySubject(subject, id);
    }

    @Override
    public Iterable<Coach> getAllCoaches(Integer id) {
        return this.UserRepository.getAllCoaches(id);
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
