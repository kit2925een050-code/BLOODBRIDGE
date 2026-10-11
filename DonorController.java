package com.bloodbridge.controller;

import com.bloodbridge.model.Donor;
import com.bloodbridge.service.DonorService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    // Display donor registration form
    @GetMapping("/donor/register")
    public String showDonorRegistration(Model model) {
        model.addAttribute("donor", new Donor());
        return "donor-register";
    }

    // Process donor registration
    @PostMapping("/donor/register")
    public String registerDonor(
            @ModelAttribute("donor") Donor donor,
            Model model) {

        if (donor.getName() == null
                || donor.getName().trim().isEmpty()) {

            model.addAttribute("error", "Donor name is required.");
            return "donor-register";
        }

        if (donor.getAge() < 18 || donor.getAge() > 65) {
            model.addAttribute(
                    "error",
                    "Donor age must be between 18 and 65."
            );

            return "donor-register";
        }

        if (donor.getPhone() == null
                || !donor.getPhone().matches("[0-9]{10}")) {

            model.addAttribute(
                    "error",
                    "Please enter a valid 10-digit phone number."
            );

            return "donor-register";
        }

        donorService.addDonor(donor);

        return "redirect:/donor/success";
    }

    // Display donor registration success page
    @GetMapping("/donor/success")
    public String donorSuccess() {
        return "donor-success";
    }

    // Display all registered donors
    @GetMapping("/donors")
    public String viewDonors(Model model) {
        model.addAttribute("donors", donorService.getAllDonors());
        return "donors";
    }
}