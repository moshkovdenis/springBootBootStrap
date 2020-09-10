package ru.denis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.denis.model.Role;
import ru.denis.model.User;
import ru.denis.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("admin/")
public class AdminPageController {


    @Autowired
    private UserService userService;
    private Set<Role> allRoles = new HashSet<>();
    {
        allRoles.add(new Role("ROLE_ADMIN"));
        allRoles.add(new Role("ROLE_USER"));
    }

    @GetMapping(value = "/adminPage")
    public String adminPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByName(authentication.getName()).orElse(
                new User("admin", "admin", (byte) 25, "admin", "admin", allRoles)
        );
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("user", user);
        return "admin/adminPage";
    }

    @PostMapping(value = "/edit")
    public String saveUser(@RequestParam Long id, @RequestParam String firstName,
                           @RequestParam String lastName, @RequestParam String age,
                           @RequestParam String email, @RequestParam String password,
                           @RequestParam(required = false) Set<String> roleList) {
        User user = userService.findById(id);
        user.setName(firstName);
        user.setLastName(lastName);
        user.setAge(Byte.parseByte(age));
        user.setMail(email);
        user.setPassword(password);
        if (roleList != null) {
            user.setRoles(new HashSet<>());
            for (String s : roleList) {
                user.getRoles().add(userService.findRoleByName(s).orElse(new Role("ROLE_ADMIN")));
            }
        }
        userService.updateUser(user);
        return "redirect:/admin/adminPage";
    }

    @PostMapping(value = "/add")
    public String addUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam byte age,
                          @RequestParam String email, @RequestParam String password, @RequestParam Set<String> roleList) {
        User user = new User(firstName, lastName, age, email, password);
        user.setRoles(new HashSet<>());
        for (String s : roleList) {
            user.getRoles().add(userService.findRoleByName(s).orElse(new Role("ROLE_ADMIN")));
        }
        userService.addUser(user);
        return "redirect:/admin/adminPage";
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(userService.findById(id));
        return "redirect:/admin/adminPage";
    }
    @GetMapping(value = "/user")
    public String user(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByName(authentication.getName()).get();
        model.addAttribute("user", user);
        return "admin/user";
    }
}
