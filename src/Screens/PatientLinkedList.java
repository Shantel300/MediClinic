package Screens;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lenovo
 */
public class PatientLinkedList {
        private PatientNode head;
    private int patientCounter;

    public PatientLinkedList() {
        head = null;
        patientCounter = 1;
    }
    /**
     * Inserts a new patient at the end of the linked list.
     */
    public void insertPatient(String patientName,
                              int age,
                              String gender,
                              String phoneNumber,
                              String address) {

        // Automatically generate Patient ID
        String patientID = "P" + String.format("%03d", patientCounter++);

        // Create a Patient object
        Patient patient = new Patient(
                patientID,
                patientName,
                age,
                gender,
                phoneNumber,
                address);

        // Create a node
        PatientNode newNode = new PatientNode(patient);

        // If the list is empty
        if (head == null) {

            head = newNode;

        } else {

            // Traverse to the last node
            PatientNode current = head;

            while (current.next != null) {

                current = current.next;

            }

            // Link the new node
            current.next = newNode;

        }

    }
    
    /**
 * Searches for a patient using Patient ID.
 * Returns the Patient object if found,
 * otherwise returns null.
 */
public Patient searchPatient(String patientID) {

    // Start from the first node
    PatientNode current = head;

    // Traverse the linked list
    while (current != null) {

        // Compare IDs
        if (current.patient.getPatientID().equalsIgnoreCase(patientID)) {

            // Patient found
            return current.patient;

        }

        // Move to the next node
        current = current.next;
    }

    // Patient not found
    return null;
}
/**
 * Updates a patient's information.
 * Returns true if the patient was updated,
 * otherwise returns false.
 */
public boolean updatePatient(String patientID,
                             String patientName,
                             int age,
                             String gender,
                             String phoneNumber,
                             String address) {

    // Start from the first node
    PatientNode current = head;

    // Traverse the linked list
    while (current != null) {

        // Check if the patient ID matches
        if (current.patient.getPatientID().equalsIgnoreCase(patientID)) {

            // Update the patient's details
            current.patient.setPatientName(patientName);
            current.patient.setAge(age);
            current.patient.setGender(gender);
            current.patient.setPhoneNumber(phoneNumber);
            current.patient.setAddress(address);

            return true;
        }

        // Move to the next node
        current = current.next;
    }

    // Patient not found
    return false;
}
/**
 * Deletes a patient using the Patient ID.
 * Returns true if the patient was deleted,
 * otherwise returns false.
 */
public boolean deletePatient(String patientID) {

    // Check if the list is empty
    if (head == null) {
        return false;
    }

    // Case 1: The first node is the patient to delete
    if (head.patient.getPatientID().equalsIgnoreCase(patientID)) {

        head = head.next;
        return true;
    }

    // Start traversing the list
    PatientNode current = head;

    while (current.next != null) {

        // Check the next node
        if (current.next.patient.getPatientID().equalsIgnoreCase(patientID)) {

            // Skip the node to delete it
            current.next = current.next.next;

            return true;
        }

        current = current.next;
    }

    // Patient not found
    return false;
}

/**
 * Returns the first node in the linked list.
 */
public PatientNode getHead() {
    return head;
}

/**
 * Performs a Linear Search based on the selected search field.
 */
public PatientNode searchPatientNode(String searchBy, String keyword) {

    // Start from the first node
    PatientNode current = head;

    // Traverse the linked list
    while (current != null) {

        // Search by Patient ID
        if (searchBy.equals("Patient ID")) {

            if (current.patient.getPatientID().toLowerCase().contains(keyword.toLowerCase())) {
                return current;
            }

        }

        // Search by Patient Name
        else if (searchBy.equals("Patient Name")) {

            if (current.patient.getPatientName().toLowerCase().contains(keyword.toLowerCase())) {
                return current;
            }

        }

        // Search by Phone Number
        else if (searchBy.equals("Phone Number")) {

            if (current.patient.getPhoneNumber().toLowerCase().contains(keyword.toLowerCase())) {
                return current;
            }

        }

        // Move to the next node
        current = current.next;
    }

    // No patient found
    return null;
}
}
