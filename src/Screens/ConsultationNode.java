package Screens;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lenovo
 */
public class ConsultationNode {

        Consultation consultation;
    ConsultationNode next;

    public ConsultationNode(Consultation consultation) {
        this.consultation = consultation;
        this.next = null;
    }

}
