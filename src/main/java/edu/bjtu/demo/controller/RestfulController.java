package edu.bjtu.demo.controller;

import edu.bjtu.demo.domain.Coach;
import edu.bjtu.demo.domain.User;
import edu.bjtu.demo.domain.UserCoach;
import edu.bjtu.demo.service.CoachService;
import edu.bjtu.demo.service.ReserveService;
import edu.bjtu.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@Api(tags = "Restful interface", description = "Provide all restful API")
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

    @ApiOperation("Add new order")
    @RequestMapping(value = "/orders/add", method = RequestMethod.POST)
    public Object reserve(@RequestBody UserCoach usercoach) {
        this.reserveService.reserve(usercoach);
        return new ResponseEntity("RESERVED", HttpStatus.CREATED);
    }

    @ApiOperation("Delete order")
    @RequestMapping(value = "/orders/delete", method = RequestMethod.DELETE)
    public Object cancel(@RequestBody UserCoach usercoach) {
        this.reserveService.cancel(usercoach);
        return new ResponseEntity("CANCELED", HttpStatus.OK);
    }

    @ApiOperation("List all orders")
    @RequestMapping(value = "/orders/list")
    public Object list() {
        return new ResponseEntity(this.reserveService.listOrders(), HttpStatus.OK);
    }

    @ApiOperation("List coach by specific user id and subject")
    @RequestMapping(value = "/orders/{id}/{subject}")
    public ResponseEntity<Object> getBySubject(@PathVariable("id") Integer id, @PathVariable("subject") String subject) {
        return new ResponseEntity<>(this.userService.getBySubject(id,subject), HttpStatus.OK);
    }

    @ApiOperation("List coach by specific user id")
    @RequestMapping(value = "/orders/{id}")
    public ResponseEntity<Object> getAllCoach(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(this.userService.getAllCoaches(id), HttpStatus.OK);
    }

    @ApiOperation("List coach by specific subject")
    @RequestMapping(value = "/coach/{subject}")
    public ResponseEntity<Object> findBySubject(@PathVariable("subject") String subject) {
        return new ResponseEntity<>(this.coachService.findCoachBySubject(subject), HttpStatus.OK);
    }

    @ApiOperation("List all coaches")
    @RequestMapping(value = "/coach/list")
    public ResponseEntity<Object> findAllCoach() {
        return new ResponseEntity<>(this.coachService.findAllCoaches(), HttpStatus.OK);
    }

    @ApiOperation("Add coach")
    @RequestMapping(value = "/coach/add", method = RequestMethod.POST)
    public ResponseEntity<Object> addCoach(@RequestBody Coach coach) {
        this.coachService.saveCoach(coach);
        return new ResponseEntity("coach saved successfully", HttpStatus.OK);
    }

    @ApiOperation("Delete coach by coach id")
    @RequestMapping(value = "/coach/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCoach(@PathVariable Integer id) {
        this.coachService.deleteCoach(id);
        return new ResponseEntity("coach deleted successfully", HttpStatus.OK);
    }

    @ApiOperation("List all users")
    @RequestMapping(value="/user/list", method = RequestMethod.GET)
    public Iterable<User> listUser(){
        return this.userService.getAllUsers();
    }

    @ApiOperation("Add new user")
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public User create(@RequestBody User user){
        return this.userService.saveUser(user);
    }

    @ApiOperation("Delete user by user id")
    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id") Integer id){
        this.userService.deleteUser(id);
        return "success";
    }
}
