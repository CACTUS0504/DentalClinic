package org.example.controller;

import org.example.model.Doctor;
import org.example.model.Patient;
import org.example.model.Role;
import org.example.model.User;
import org.example.service.DoctorService;
import org.example.service.PatientService;
import org.example.service.RoleService;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class AdminController {
    private final UserService userService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final RoleService roleService;

    public AdminController(UserService userService, DoctorService doctorService, PatientService patientService, RoleService roleService) {
        this.userService = userService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.roleService = roleService;
    }

    @GetMapping(value = "admin/ban")
    public String returnBanPage(Model model) {
        User currentUser = userService.getCurrentUser();
        List <User> users = userService.readAllEntity();
        for (int i = 0; i < users.size(); i++){
            if (users.get(i).getUsername().equals(currentUser.getUsername())){
                users.remove(i);
                break;
            }
        }
        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);
        return "/admin/ban";
    }

    @GetMapping(value = "admin/ban/{id}")
    public String banUser(Model model, @PathVariable(name = "id") long id) {
        User user = userService.readOneEntity(id);
        user.setIsBanned(true);
        userService.updateEntity(user, user.getId());
        model.addAttribute("currentUser", userService.getCurrentUser());
        return "/admin/banned";
    }

    @GetMapping(value = "admin/add-doctor")
    public String returnAddDoctorPage(Model model) {
        model.addAttribute("currentUser", userService.getCurrentUser());
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("user", new User());
        return "/admin/add_doctor";
    }

    @PostMapping(value = "admin/add-doctor")
    public String addDoctor(Model model, @ModelAttribute User user, @ModelAttribute Doctor doctor) {
        user.setIsBanned(false);
        Role role = roleService.findRoleByName("ROLE_DOCTOR");
        user.getRoles().add(role);
        role.getUsers().add(user);
        doctor.setUser(user);
        userService.signUpUser(user);
        doctorService.createEntity(doctor);
        model.addAttribute("currentUser", userService.getCurrentUser());
        return "/admin/add_doctor";
    }
}
