package Screens;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lenovo
 */
public class Patient {


    private String patientID;
    private String patientName;
    private int age;
    private String gender;
    private String phoneNumber;
    private String address;

  public Patient(String patientID,
               String patientName,
               int age,
               String gender,
               String phoneNumber,
               String address) {

    this.patientID = patientID;
    this.patientName = patientName;
    this.age = age;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.address = address;
}

// Getters
    public String getPatientID() {
        return patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    // Setters
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}