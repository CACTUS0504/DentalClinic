package org.example.controller;

import org.example.model.Role;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value="")
    public String showHome(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", userService.getCurrentUser());
        for (Role role : currentUser.getRoles()){
            if (role.getName().equals("ROLE_ADMIN")){
                return "admin/home";
            } else if (role.getName().equals("ROLE_DOCTOR")){
                return "doctor/home";
            }
        }
        return "patient/home";
    }
}
