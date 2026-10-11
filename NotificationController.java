package com.bloodbridge.controller;

import com.bloodbridge.model.Notification;
import com.bloodbridge.service.NotificationService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Display alerts
    @GetMapping("/notifications")
    public String showNotifications(
            @RequestParam(required = false) String phone,
            Model model) {

        List<Notification> notifications =
                notificationService.getNotifications();

        model.addAttribute("notifications", notifications);
        model.addAttribute("selectedPhone", phone);

        if (phone != null && !phone.isBlank()) {

            model.addAttribute(
                    "donorNotifications",
                    notificationService.getNotificationsForDonor(
                            phone.trim()
                    )
            );
        }

        model.addAttribute(
                "totalAlerts",
                notificationService.getTotalNotifications()
        );

        model.addAttribute(
                "pendingAlerts",
                notificationService.getPendingNotifications()
        );

        return "notifications";
    }

    // Accept or decline an alert
    @PostMapping("/notifications/respond")
    public String respondToNotification(
            @RequestParam Long id,
            @RequestParam String phone,
            @RequestParam String status,
            RedirectAttributes redirectAttributes) {

        boolean updated = notificationService.updateStatus(
                id,
                phone,
                status
        );

        if (updated) {

            redirectAttributes.addFlashAttribute(
                    "message",
                    "Your response has been recorded."
            );

        } else {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Unable to update the alert."
            );
        }

        return "redirect:/notifications?phone=" + phone;
    }
}