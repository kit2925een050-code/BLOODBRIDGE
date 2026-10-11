package com.bloodbridge.service;

import com.bloodbridge.model.BloodRequest;
import com.bloodbridge.model.Donor;
import com.bloodbridge.model.Notification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NotificationService {

    private final List<Notification> notifications = new ArrayList<>();

    private final AtomicLong counter = new AtomicLong(1);

    // Create alerts for matching donors
    public synchronized void createNotifications(
            BloodRequest request,
            List<Donor> matchingDonors) {

        if (request == null || matchingDonors == null) {
            return;
        }

        for (Donor donor : matchingDonors) {

            Notification notification = new Notification(
                    counter.getAndIncrement(),
                    donor.getName(),
                    donor.getPhone(),
                    request.getHospitalName(),
                    request.getPatientName(),
                    request.getBloodGroup(),
                    request.getCity(),
                    request.getUrgency()
            );

            notifications.add(notification);
        }
    }

    // Get every notification
    public synchronized List<Notification> getNotifications() {
        return new ArrayList<>(notifications);
    }

    // Get notifications for a particular donor phone number
    public synchronized List<Notification> getNotificationsForDonor(
            String phone) {

        List<Notification> result = new ArrayList<>();

        if (phone == null) {
            return result;
        }

        for (Notification notification : notifications) {

            if (notification.getDonorPhone() != null
                    && notification.getDonorPhone().equals(phone)) {

                result.add(notification);
            }
        }

        return result;
    }

    // Allow donors to accept or decline their alerts
    public synchronized boolean updateStatus(
            Long id,
            String phone,
            String status) {

        if (!"Accepted".equals(status)
                && !"Declined".equals(status)) {
            return false;
        }

        if (id == null || phone == null) {
            return false;
        }

        for (Notification notification : notifications) {

            if (notification.getId().equals(id)
                    && phone.equals(notification.getDonorPhone())) {

                notification.setStatus(status);
                return true;
            }
        }

        return false;
    }

    // Count all alerts
    public synchronized int getTotalNotifications() {
        return notifications.size();
    }

    // Count pending alerts
    public synchronized int getPendingNotifications() {

        int count = 0;

        for (Notification notification : notifications) {

            if ("Pending".equals(notification.getStatus())) {
                count++;
            }
        }

        return count;
    }
}