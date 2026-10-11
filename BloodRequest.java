package com.bloodbridge.model;

public class BloodRequest {

    private String hospitalName;
    private String patientName;
    private String bloodGroup;
    private String city;
    private String contactNumber;
    private String urgency;

    public BloodRequest() {
    }

    public BloodRequest(
            String hospitalName,
            String patientName,
            String bloodGroup,
            String city,
            String contactNumber,
            String urgency) {

        this.hospitalName = hospitalName;
        this.patientName = patientName;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.contactNumber = contactNumber;
        this.urgency = urgency;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }
}