package com.bloodbridge.controller;

import com.bloodbridge.model.BloodRequest;
import com.bloodbridge.model.Donor;
import com.bloodbridge.service.BloodRequestService;
import com.bloodbridge.service.DonorService;
import com.bloodbridge.service.NotificationService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BloodRequestController {

    private final BloodRequestService bloodRequestService;
    private final DonorService donorService;
    private final NotificationService notificationService;

    public BloodRequestController(
            BloodRequestService bloodRequestService,
            DonorService donorService,
            NotificationService notificationService) {

        this.bloodRequestService = bloodRequestService;
        this.donorService = donorService;
        this.notificationService = notificationService;
    }

    // Show emergency request form
    @GetMapping("/emergency")
    public String showEmergencyForm(Model model) {

        if (!model.containsAttribute("bloodRequest")) {
            model.addAttribute("bloodRequest", new BloodRequest());
        }

        return "emergency";
    }

    // Submit emergency request and notify matching donors
    @PostMapping("/emergency")
    public String sendEmergencyAlert(
            BloodRequest bloodRequest,
            RedirectAttributes redirectAttributes) {

        if (isBlank(bloodRequest.getHospitalName())
                || isBlank(bloodRequest.getPatientName())
                || isBlank(bloodRequest.getCity())
                || isBlank(bloodRequest.getContactNumber())
                || isBlank(bloodRequest.getBloodGroup())
                || isBlank(bloodRequest.getUrgency())) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Please fill in all required fields."
            );

            return "redirect:/emergency";
        }

        String contact = bloodRequest.getContactNumber().trim();

        if (!contact.matches("\\d{10}")) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Enter a valid 10-digit contact number."
            );

            return "redirect:/emergency";
        }

        String bloodGroup =
                bloodRequest.getBloodGroup().trim().toUpperCase();

        List<String> validBloodGroups = List.of(
                "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
        );

        if (!validBloodGroups.contains(bloodGroup)) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Select a valid blood group."
            );

            return "redirect:/emergency";
        }

        String urgency = bloodRequest.getUrgency().trim();

        if (!urgency.equalsIgnoreCase("Urgent")
                && !urgency.equalsIgnoreCase("Critical")) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Select Urgent or Critical urgency."
            );

            return "redirect:/emergency";
        }

        // Clean submitted values
        bloodRequest.setHospitalName(
                bloodRequest.getHospitalName().trim()
        );

        bloodRequest.setPatientName(
                bloodRequest.getPatientName().trim()
        );

        bloodRequest.setCity(
                bloodRequest.getCity().trim()
        );

        bloodRequest.setContactNumber(contact);
        bloodRequest.setBloodGroup(bloodGroup);

        bloodRequest.setUrgency(
                urgency.equalsIgnoreCase("Critical")
                        ? "Critical"
                        : "Urgent"
        );

        // Save the request
        bloodRequestService.addRequest(bloodRequest);

        // Find matching available donors
        List<Donor> matchingDonors =
                donorService.findMatchingDonors(
                        bloodRequest.getBloodGroup(),
                        bloodRequest.getCity()
                );

        // Create notifications for matching donors
        notificationService.createNotifications(
                bloodRequest,
                matchingDonors
        );

        if (matchingDonors.isEmpty()) {

            redirectAttributes.addFlashAttribute(
                    "message",
                    "Request saved. No matching available donors found."
            );

        } else {

            redirectAttributes.addFlashAttribute(
                    "message",
                    "Emergency alert created for "
                            + matchingDonors.size()
                            + " matching donor(s)."
            );
        }

        return "redirect:/blood-requests";
    }

    // Display all emergency requests
    @GetMapping("/blood-requests")
    public String showBloodRequests(Model model) {

        model.addAttribute(
                "requests",
                bloodRequestService.getAllRequests()
        );

        return "blood-requests";
    }

    // Display matching donors
    @GetMapping("/blood-requests/matches")
    public String showMatchingDonors(
            @RequestParam(required = false) String bloodGroup,
            @RequestParam(required = false) String city,
            Model model) {

        List<Donor> matches = List.of();

        if (!isBlank(bloodGroup) && !isBlank(city)) {
            matches = donorService.findMatchingDonors(
                    bloodGroup.trim().toUpperCase(),
                    city.trim()
            );
        }

        model.addAttribute("matches", matches);
        model.addAttribute("bloodGroup", bloodGroup);
        model.addAttribute("city", city);

        return "donor-matches";
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}