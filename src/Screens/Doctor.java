package Screens;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lenovo
 */
public class Doctor {


    private String doctorID;
    private String doctorName;
    private String specialization;
    private String phoneNumber;
    private String emailAddress;
    private String address;

  public Doctor(String doctorID,
               String doctorName,
               String specialization,
               String phoneNumber,
               String emailAddress,
               String address) {

    this.doctorID = doctorID;
    this.doctorName = doctorName;
    this.specialization = specialization;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.address = address;
}

// Getters
    public String getDoctorID() {
        return doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getAddress() {
        return address;
    }

    // Setters
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setAddress(String address) {
        this.address = address;
    }

      
}