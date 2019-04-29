package edu.bjtu.demo.controller;

import edu.bjtu.demo.domain.User;
import edu.bjtu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class IndexController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/index" })
    public String index() {
        return "index";
    }

    @RequestMapping(value = { "", "/"})
    public String entrance() {
        return "entrance";
    }

    @RequestMapping(value = {"/signIn"}, method = RequestMethod.GET)
    public String signInShow(Model model) {
        model.addAttribute("activePage", "signIn");
        return "entrance";
    }

    @RequestMapping(value = {"/signUp"}, method = RequestMethod.GET)
    public String signUpShow(Model model) {
        model.addAttribute("activePage", "signUp");
        return "entrance";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public String signIn(User user, Model model) {
        if(!this.userService.checkUser(user)){
            model.addAttribute("activePage", "signIn");
            return "entrance";
        }
        return "redirect:/index";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(@Valid User user, Model model) {
        if (this.userService.existUser(user)) {
            model.addAttribute("activePage", "signUp");
            return "entrance";
        }
        this.userService.saveUser(user);
        return "redirect:/signIn";
    }
}
