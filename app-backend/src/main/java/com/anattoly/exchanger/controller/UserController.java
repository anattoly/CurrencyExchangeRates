package com.anattoly.exchanger.controller;

import com.anattoly.exchanger.model.user.User;
import com.anattoly.exchanger.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @GetMapping({"/", "/login"})
    public String signin() {

        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/signup")
    public String createUser(Model model, @Valid User user, BindingResult bindingResult) {

        if (!userService.saveUser(user)) {
            bindingResult.rejectValue("login", "error.user", "This login already exists!");
            LOGGER.info("User with login \"" + user.getLogin() + "\" already exists");
            return "signup";
        } else {
            model.addAttribute("msg", "User has been registered successfully!");
            LOGGER.info("User with login \"" + user.getLogin() + "\" has been created");
            model.addAttribute("user", new User());
        }

        return "redirect:/login";
    }

    @GetMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }
}
