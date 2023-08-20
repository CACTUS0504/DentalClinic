package org.example.controller;

import org.example.model.Patient;
import org.example.model.Role;
import org.example.model.User;
import org.example.service.PatientService;
import org.example.service.RoleService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/authorization")
public class AuthController {
    private UserService userService;
    private RoleService roleService;
    private PatientService patientService;

    @Autowired
    public AuthController(UserService userService, RoleService roleService, PatientService patientService) {
        this.userService = userService;
        this.roleService = roleService;
        this.patientService = patientService;
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user, @ModelAttribute("patient") Patient patient) {
        return "authorization/registration";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "authorization/login";
    }

    // Через форму регистрации могут добавляться только пользователи. Врачей добавляет админ.
    @PostMapping("/registration")
    public String signUpUser(@ModelAttribute("user") User user, @ModelAttribute("patient") Patient patient) {
        Role role = roleService.findRoleByName("ROLE_USER");
        user.getRoles().add(role);
        role.getUsers().add(user);
        patient.setUser(user);
        user.setIsBanned(false);
        userService.signUpUser(user);
        patientService.createEntity(patient);
        return "authorization/login";
    }
}
