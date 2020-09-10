package ru.denis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.denis.model.User;
import ru.denis.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user")
    public String user(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByName(authentication.getName()).get();
        model.addAttribute("user", user);
        return "/user";
    }
}
