package edu.bjtu.demo.controller;

import edu.bjtu.demo.domain.Coach;
import edu.bjtu.demo.domain.PagerModel;
import edu.bjtu.demo.service.CoachService;
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
@RequestMapping("/home")
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private CoachService coachService;

    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = { 5, 10};

    @Autowired
    public void setCoachService(CoachService coachService) {
        this.coachService = coachService;
    }


    @RequestMapping(value = { "", "/"}, method = RequestMethod.GET) // findAll
    public ModelAndView findAllCoach(@RequestParam("pageSize") Optional<Integer> pageSize,
                                     @RequestParam("page") Optional<Integer> page){
        ModelAndView modelAndView = new ModelAndView("home");

        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Coach> coachList = this.coachService.findAllCoaches(evalPageSize,evalPage);
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

    @Cacheable(value = "all")
    public Page<Coach> cacheAllCoach(Page<Coach> coachList){
        return coachList;
    }

    @CacheEvict(value = "all")
    @RequestMapping("/evict")
    public String evictAllCoach() {
        log.info("Evict all coaches");
        return "redirect:/home";
    }
//
//    @Cacheable(value = "find", key = "#subject")
//    public Page<Coach> cacheCoach(Page<Coach> coachList, String subject){
//        return coachList;
//    }
//
//    @CacheEvict(value = "find", key = "#subject")
//    @RequestMapping("/evict/{subject}")
//    public String evict(@PathVariable String subject) {
//        log.info("Evict " + subject + "coaches");
//        return "redirect:/home";
//    }

}
