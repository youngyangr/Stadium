package edu.bjtu.demo.controller;

import edu.bjtu.demo.domain.Coach;
import edu.bjtu.demo.domain.PagerModel;
import edu.bjtu.demo.domain.UserCoach;
import edu.bjtu.demo.service.ReserveService;
import edu.bjtu.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping(value = {"/home" })
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private ReserveService reserveService;
    private UserService userService;

    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = { 5, 10};

    @Autowired
    public void setUserService(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = { "", "/"})
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public String reserve(UserCoach usercoach) {
        this.reserveService.reserve(usercoach);
        return "redirect:/home";
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel(UserCoach usercoach) {
        this.reserveService.cancel(usercoach);
        return "redirect:/home";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getCoachBySubject(@RequestParam("pageSize") Optional<Integer> pageSize,
                                    @RequestParam("page") Optional<Integer> page) {
        Integer id =  Integer.valueOf(1);
        String subject = "swim";
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        Page<Coach> coaches = this.userService.getBySubject(subject, id, evalPageSize, evalPage);
        for (Coach coach : coaches) {
            System.out.println(coach);
        }

        return "redirect:/home";
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public String getAllCoach(@RequestParam("pageSize") Optional<Integer> pageSize,
                              @RequestParam("page") Optional<Integer> page) {
        Integer id =  Integer.valueOf(1);
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Coach> coaches = this.userService.getAllCoaches(id, evalPageSize, evalPage);

        for (Coach coach : coaches) {
            System.out.println(coach);
        }

        return "redirect:/home";
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String findBySubject(@RequestParam("pageSize") Optional<Integer> pageSize,
                                @RequestParam("page") Optional<Integer> page) {
        String subject = "football";
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Coach> coaches = this.reserveService.findBySubject(subject, evalPageSize, evalPage);
        cacheCoach(coaches, subject);
        for (Coach coach : coaches) {
            System.out.println(coach);
        }

        return "redirect:/home";
    }

    @Cacheable(value = "find", key = "#subject")
    public Page<Coach> cacheCoach(Page<Coach> coachList, String subject){
        return coachList;
    }

    @CacheEvict(value = "find", key = "#subject")
    @RequestMapping("/find/evict/{subject}")
    public String evict(@PathVariable String subject) {
        log.info("Evict all coaches of {}", subject);
        return "redirect:/home";
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ModelAndView findAllCoach(@RequestParam("pageSize") Optional<Integer> pageSize,
                                     @RequestParam("page") Optional<Integer> page){
        ModelAndView modelAndView = new ModelAndView("home");

        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Coach> coachList = this.reserveService.findAllCoaches(evalPageSize,evalPage);
        cacheAllCoach(coachList);
        PagerModel pager = new PagerModel(coachList.getTotalPages(),coachList.getNumber(),BUTTONS_TO_SHOW);
        // add coach model
        modelAndView.addObject("coachList",coachList);
        // evaluate page size
        modelAndView.addObject("selectedPageSize", evalPageSize);
        // add page sizes
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        // add pager
        modelAndView.addObject("pager", pager);

        return modelAndView;
    }

    @Cacheable(value = "findAll")
    public Page<Coach> cacheAllCoach(Page<Coach> coachList){
        return coachList;
    }

    @CacheEvict(value = "findAll")
    @RequestMapping("/findAll/evict")
    public String evictAllCoach() {
        log.info("Evict all coaches");
        return "redirect:/home";
    }

}
