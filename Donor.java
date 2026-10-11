package com.bloodbridge.model;

public class Donor {

    private String name;
    private int age;
    private String phone;
    private String bloodGroup;
    private String city;
    private String availability;

    public Donor() {
    }

    public Donor(String name, int age, String phone,
                 String bloodGroup, String city, String availability) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}