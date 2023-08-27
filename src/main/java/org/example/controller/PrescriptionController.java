package org.example.controller;

import org.example.model.*;
import org.example.service.DoctorService;
import org.example.service.PatientService;
import org.example.service.PrescriptionService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final UserService userService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    PrescriptionController(PrescriptionService prescriptionService, UserService userService, DoctorService doctorService, PatientService patientService){
        this.prescriptionService = prescriptionService;
        this.userService = userService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @GetMapping("/doctor/prescription")
    public String returnDoctorPrescription(Model model) {
        User currentUser = userService.getCurrentUser();
        Doctor doctor = doctorService.findByUser(currentUser.getId());
        model.addAttribute("patients", doctor.getPatients());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("prescription", new Prescription());
        if (doctor == null) return "/patient/no_doctor";
        return "doctor/create_prescription";
    }

    @PostMapping("/doctor/prescription")
    public String createPrescription(@ModelAttribute Prescription prescription, Model model){
        prescriptionService.createEntity(prescription);

        User currentUser = userService.getCurrentUser();
        Doctor doctor = doctorService.findByUser(currentUser.getId());
        model.addAttribute("patients", doctor.getPatients());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("prescription", new Prescription());
        return "doctor/create_prescription";
    }

    @GetMapping("/patient/prescription")
    public String returnPatientPrescription(Model model) {
        User currentUser = userService.getCurrentUser();
        Patient patient = patientService.findByUser(userService.getCurrentUser().getId());
        Doctor doctor = patient.getDoctor();
        List<Prescription> prescriptions = patient.getPrescriptions();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("patient_name", patient.getLastName() + ' ' + patient.getFirstName() + ' ' + patient.getMiddleName());
        model.addAttribute("doctor_name", doctor.getLastName() + ' ' + doctor.getFirstName() + ' ' + doctor.getMiddleName());
        if (prescriptions.isEmpty()) return "patient/no_prescription.html";
        model.addAttribute("prescriptions", prescriptions);
        return "patient/prescription";
    }

    @Controller
    @RequestMapping("/api/prescription/pdf")
    public class TestPDFcontroller {
        @GetMapping
        public ResponseEntity<byte[]> ping(@RequestParam String patient_name, @RequestParam String doctor_name, @RequestParam String prescription_body, HttpServletResponse response) throws IOException {
            final String uri = "http://localhost:8090/api/prescription" + "?patient_name=" + patient_name + "&doctor_name=" + doctor_name + "&prescription_body=" + prescription_body;
            RestTemplate restTemplate = new RestTemplate();
            byte[] bytes = restTemplate.getForObject(uri, byte[].class);
            ByteArrayOutputStream baos_doc = new ByteArrayOutputStream();
            baos_doc.writeBytes(bytes);

            response.setContentType("application/pdf");
            DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
            String currentDateTime = dateFormat.format(new Date());
            String headerkey = "Content-Disposition";
            String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
            response.setHeader(headerkey, headervalue);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(bytes);
        }
    }
}