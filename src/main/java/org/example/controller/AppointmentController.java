package org.example.controller;

import org.example.model.Appointment;
import org.example.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    // Добавить view
    @GetMapping(value="")
    @ResponseBody
    public List<Appointment> readAll(Model model) {
        model.addAttribute("appointments", appointmentService.readAllEntity());
        return appointmentService.readAllEntity();
    }
}