package Screens;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lenovo
 */
public class Consultation {


    private String consultationID;
    private String patientID;
    private String doctorID;
    private String consultationDate;
    private String diagnosis;
    private String notes;

  public Consultation(String consultationID,
               String patientID,
               String doctorID,
               String consultationDate,
               String diagnosis,
               String notes) {

    this.consultationID = consultationID;
    this.patientID = patientID;
    this.doctorID = doctorID;
    this.consultationDate = consultationDate;
    this.diagnosis = diagnosis;
    this.notes = notes;
}

// Getters
    public String getConsultationID() {
        return consultationID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getConsultationDate() {
        return consultationDate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getNotes() {
        return notes;
    }

    // Setters
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public void setConsultationDate(String consultationDate) {
        this.consultationDate = consultationDate;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
