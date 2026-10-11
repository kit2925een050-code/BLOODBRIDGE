package com.bloodbridge.controller;

import com.bloodbridge.model.Hospital;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HospitalController {

    // Store registered hospitals temporarily
    private final List<Hospital> hospitals = new ArrayList<>();

    // Hospital registration page
    @GetMapping("/hospital/register")
    public String showHospitalRegistration(Model model) {
        model.addAttribute("hospital", new Hospital());
        return "hospital-register";
    }

    // Save hospital registration
    @PostMapping("/hospital/register")
    public String registerHospital(
            @ModelAttribute("hospital") Hospital hospital) {

        hospitals.add(hospital);
        return "redirect:/hospital/success";
    }

    // Registration successful page
    @GetMapping("/hospital/success")
    public String hospitalSuccess() {
        return "hospital-success";
    }

    // View registered hospitals
    @GetMapping("/hospitals")
    public String viewHospitals(Model model) {
        model.addAttribute("hospitals", new ArrayList<>(hospitals));
        return "hospitals";
    }
}