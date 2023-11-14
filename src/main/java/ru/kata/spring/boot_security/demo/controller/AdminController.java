package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAdmin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

    @PostMapping("/users/delete")
    public String delete(@RequestParam(value = "id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @PostMapping("/users/update")
    public String update(@RequestParam(value = "ids") String ids, @RequestParam(value = "id") Long id,
                         @RequestBody User user) {
        userService.update(id, user, List.of(ids));
        return "redirect:/admin";
    }

    @PostMapping("/users/new")
    public String create(@RequestParam(value = "ids") String ids, @RequestBody User user) {
        userService.save(user, List.of(ids));
        return "redirect:/admin";
    }
}
