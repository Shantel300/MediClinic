package Screens;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Lenovo
 */
public class DoctorLinkedList {

    private DoctorNode head;
    private int doctorCounter;

    public DoctorLinkedList() {
        head = null;
        doctorCounter = 1;
    }

    /**
     * Inserts a new doctor at the end of the linked list.
     */
    public void insertDoctor(String doctorName,
            String specialization,
            String phoneNumber,
            String emailAddress,
            String address) {

        // Automatically generate Doctor ID
        String doctorID = "D" + String.format("%03d", doctorCounter++);

        // Create a Doctor object
        Doctor doctor = new Doctor(
                doctorID,
                doctorName,
                specialization,
                phoneNumber,
                emailAddress,
                address);

        // Create a node
        DoctorNode newNode = new DoctorNode(doctor);

        // If the list is empty
        if (head == null) {

            head = newNode;

        } else {

            // Traverse to the last node
            DoctorNode current = head;

            while (current.next != null) {

                current = current.next;

            }

            // Link the new node
            current.next = newNode;

        }

    }

    /**
     * Searches for a doctor using doctor ID. Returns the Doctor object if
     * found, otherwise returns null.
     */
    public Doctor searchDoctor(String doctorID) {

        // Start from the first node
        DoctorNode current = head;

        // Traverse the linked list
        while (current != null) {

            // Compare IDs
            if (current.doctor.getDoctorID().equalsIgnoreCase(doctorID)) {

                // Doctor found
                return current.doctor;

            }

            // Move to the next node
            current = current.next;
        }

        // Doctor not found
        return null;
    }

    /**
     * Updates a doctor's information. Returns true if the doctor was updated,
     * otherwise returns false.
     */
    public boolean updateDoctor(String doctorID,
            String doctorName,
            String specialization,
            String phoneNumber,
            String emailAddress,
            String address) {

        // Start from the first node
        DoctorNode current = head;

        // Traverse the linked list
        while (current != null) {

            // Check if the doctor ID matches
            if (current.doctor.getDoctorID().equalsIgnoreCase(doctorID)) {

                // Update the doctor's details
                current.doctor.setDoctorName(doctorName);
                current.doctor.setSpecialization(specialization);
                current.doctor.setPhoneNumber(phoneNumber);
                current.doctor.setEmailAddress(emailAddress);
                current.doctor.setAddress(address);

                return true;
            }

            // Move to the next node
            current = current.next;
        }

        // doctor not found
        return false;
    }

    /**
     * Deletes a doctor using the Doctor ID. Returns true if the doctor was
     * deleted, otherwise returns false.
     */
    public boolean deleteDoctor(String doctorID) {

        // Check if the list is empty
        if (head == null) {
            return false;
        }

        // Case 1: The first node is the doctor to delete
        if (head.doctor.getDoctorID().equalsIgnoreCase(doctorID)) {

            head = head.next;
            return true;
        }

        // Start traversing the list
        DoctorNode current = head;

        while (current.next != null) {

            // Check the next node
            if (current.next.doctor.getDoctorID().equalsIgnoreCase(doctorID)) {

                // Skip the node to delete it
                current.next = current.next.next;

                return true;
            }

            current = current.next;
        }

        // doctor not found
        return false;
    }

    /**
     * Returns the first node in the linked list.
     */
    public DoctorNode getHead() {
        return head;
    }

    /**
     * Performs a Linear Search based on the selected search field.
     */
    public DoctorNode searchDoctorNode(String searchBy, String keyword) {

        // Start from the first node
        DoctorNode current = head;

        // Traverse the linked list
        while (current != null) {

            // Search by Doctor ID
            if (searchBy.equals("Doctor ID")) {

                if (current.doctor.getDoctorID().toLowerCase().contains(keyword.toLowerCase())) {
                    return current;
                }

            } // Search by Doctor Name
            else if (searchBy.equals("Doctor Name")) {

                if (current.doctor.getDoctorName().toLowerCase().contains(keyword.toLowerCase())) {
                    return current;
                } else {
                }

            } // Search by Phone Number
            else if (searchBy.equals("Phone Number")) {

                if (current.doctor.getPhoneNumber().toLowerCase().contains(keyword.toLowerCase())) {
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
