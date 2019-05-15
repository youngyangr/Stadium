package edu.bjtu.demo.controller;

import edu.bjtu.demo.domain.UserCoach;
import edu.bjtu.demo.service.CoachService;
import edu.bjtu.demo.service.ReserveService;
import edu.bjtu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class RestfulController {

    private ReserveService reserveService;
    private UserService userService;
    private CoachService coachService;

    @Autowired
    public void setReserveService(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCoachService(CoachService coachService) {
        this.coachService = coachService;
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST, produces = "application/text")
    public Object reserve(@RequestBody UserCoach usercoach) {
        this.reserveService.reserve(usercoach);
        return new ResponseEntity<>("RESERVED", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/order", method = RequestMethod.DELETE, produces = "application/text")
    public Object cancel(@RequestBody UserCoach usercoach) {
        this.reserveService.cancel(usercoach);
        return new ResponseEntity<>("CANCELED", HttpStatus.OK);
    }

    @RequestMapping(value = "/orders/{id}/{subject}")
    public ResponseEntity<Object> getBySubject(@PathVariable("id") Integer id, @PathVariable("subject") String subject) {
        return new ResponseEntity<>(this.userService.getBySubject(id,subject), HttpStatus.OK);
    }

    @RequestMapping(value = "/orders/{id}")
    public ResponseEntity<Object> getAllCoach(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(this.userService.getAllCoaches(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/coach/{subject}")
    public ResponseEntity<Object> findBySubject(@PathVariable("subject") String subject) {
        return new ResponseEntity<>(this.coachService.findCoachBySubject(subject), HttpStatus.OK);
    }

    @RequestMapping(value = "/coach")
    public ResponseEntity<Object> findAllCoach() {
        return new ResponseEntity<>(this.coachService.findAllCoaches(), HttpStatus.OK);
    }

}
