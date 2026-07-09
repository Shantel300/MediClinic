package Screens;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Lenovo
 */
public class ConsultationLinkedList {

    private ConsultationNode head;
    private int consultationCounter;

    public ConsultationLinkedList() {
        head = null;
        consultationCounter = 1;
    }

    /**
     * Inserts a new consultation at the end of the linked list.
     */
    public void insertConsultation(String patientID,
            String doctorID,
            String consultationDate,
            String diagnosis,
            String notes) {

        // Automatically generate Consultation ID
        String consultationID = "C" + String.format("%03d", consultationCounter++);

        // Create a Consultation object
        Consultation consultation = new Consultation(
                consultationID,
                patientID,
                doctorID,
                consultationDate,
                diagnosis,
                notes);

        // Create a node
        ConsultationNode newNode = new ConsultationNode(consultation);

        // If the list is empty
        if (head == null) {

            head = newNode;

        } else {

            // Traverse to the last node
            ConsultationNode current = head;

            while (current.next != null) {

                current = current.next;

            }

            // Link the new node
            current.next = newNode;

        }

    }

    /**
     * Searches for a consultation using Consultation ID. Returns the
     * Consultation object if found, otherwise returns null.
     */
    public Consultation searchConsultation(String consultationID) {

        // Start from the first node
        ConsultationNode current = head;

        // Traverse the linked list
        while (current != null) {

            // Compare IDs
            if (current.consultation.getConsultationID().equalsIgnoreCase(consultationID)) {

                // Consultation found
                return current.consultation;

            }

            // Move to the next node
            current = current.next;
        }

        // Consultation not found
        return null;
    }

    /**
     * Updates a consultation's information. Returns true if the consultation
     * was updated, otherwise returns false.
     */
    public boolean updateConsultation(String consultationID,
            String patientID,
            String doctorID,
            String consultationDate,
            String diagnosis,
            String notes) {

        // Start from the first node
        ConsultationNode current = head;

        // Traverse the linked list
        while (current != null) {

            // Check if the consultation ID matches
            if (current.consultation.getConsultationID().equalsIgnoreCase(consultationID)) {

                // Update the consultation's details
                current.consultation.setPatientID(patientID);
                current.consultation.setDoctorID(doctorID);
                current.consultation.setConsultationDate(consultationDate);
                current.consultation.setDiagnosis(diagnosis);
                current.consultation.setNotes(notes);

                return true;
            }

            // Move to the next node
            current = current.next;
        }

        // consultation not found
        return false;
    }

    /**
     * Deletes a consultation using the Consultation ID. Returns true if the
     * consultation was deleted, otherwise returns false.
     */
    public boolean deleteConsultation(String consultationID) {

        // Check if the list is empty
        if (head == null) {
            return false;
        }

        // Case 1: The first node is the consultation to delete
        if (head.consultation.getConsultationID().equalsIgnoreCase(consultationID)) {

            head = head.next;
            return true;
        }

        // Start traversing the list
        ConsultationNode current = head;

        while (current.next != null) {

            // Check the next node
            if (current.next.consultation.getConsultationID().equalsIgnoreCase(consultationID)) {

                // Skip the node to delete it
                current.next = current.next.next;

                return true;
            }

            current = current.next;
        }

        // consultation not found
        return false;
    }

    /**
     * Returns the first node in the linked list.
     */
    public ConsultationNode getHead() {
        return head;
    }

    /**
     * Performs a Linear Search based on the selected search field.
     */
    public ConsultationNode searchConsultationNode(String searchBy, String keyword) {

        // Start from the first node
        ConsultationNode current = head;

        // Traverse the linked list
        while (current != null) {

            // Search by Consultation ID
            if (searchBy.equals("Consultation ID")) {

                if (current.consultation.getConsultationID().toLowerCase().contains(keyword.toLowerCase())) {
                    return current;
                }

            } // Search by Patient ID
            else if (searchBy.equals("Patient ID")) {

                if (current.consultation.getPatientID().toLowerCase().contains(keyword.toLowerCase())) {
                    return current;
                }

            } // Search by Doctor ID
            else if (searchBy.equals("Doctor ID")) {

                if (current.consultation.getDoctorID().toLowerCase().contains(keyword.toLowerCase())) {
                    return current;
                }

            }

            // Move to the next node
            current = current.next;
        }

        // No consultation found
        return null;
    }
}
