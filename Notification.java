package com.bloodbridge.model;

import java.time.LocalDateTime;

public class Notification {

    private Long id;
    private String donorName;
    private String donorPhone;
    private String hospitalName;
    private String patientName;
    private String bloodGroup;
    private String city;
    private String urgency;
    private String status;
    private LocalDateTime createdAt;

    public Notification() {
    }

    public Notification(
            Long id,
            String donorName,
            String donorPhone,
            String hospitalName,
            String patientName,
            String bloodGroup,
            String city,
            String urgency) {

        this.id = id;
        this.donorName = donorName;
        this.donorPhone = donorPhone;
        this.hospitalName = hospitalName;
        this.patientName = patientName;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.urgency = urgency;
        this.status = "Pending";
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getDonorName() {
        return donorName;
    }

    public String getDonorPhone() {
        return donorPhone;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getCity() {
        return city;
    }

    public String getUrgency() {
        return urgency;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}