package Screens;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Holds the linked lists shared across every screen, so patients, doctors,
 * and consultations added (or removed) in one screen are immediately visible
 * from every other screen and from the Dashboard.
 *
 * @author Lenovo
 */
public class ClinicData {

    public static PatientLinkedList patientList = new PatientLinkedList();
    public static DoctorLinkedList doctorList = new DoctorLinkedList();
    public static ConsultationLinkedList consultationList = new ConsultationLinkedList();

}
