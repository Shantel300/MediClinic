package Screens;

import Screens.ConsultationScreen;
import Screens.DoctorsScreen;
import Screens.PatientsScreen;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Lenovo
 */
public class DashboardScreen extends javax.swing.JFrame {

    /**
     * Creates new form MediClinicGUI
     */
    public DashboardScreen() {
        initComponents();

        // Every time the Dashboard opens, pull the latest figures from the
        // shared linked lists
        refreshDashboard();
    }

    /**
     * Reloads every live figure on the Dashboard from the shared linked
     * lists: the three totals, the Recent Consultations table, and clears
     * any leftover Global Search keyword/results from a previous visit.
     */
    private void refreshDashboard() {

        lblTotalPatients.setText(String.valueOf(countPatients()));
        lblTotalDoctors.setText(String.valueOf(countDoctors()));
        lblTotalConsultations.setText(String.valueOf(countConsultations()));

        loadRecentConsultationsTable();

        // Clear any keyword/results left over from a previous visit
        txtGlobalSearch.setText("");
        DefaultTableModel searchModel = (DefaultTableModel) tblGlobalSearchResults.getModel();
        searchModel.setRowCount(0);

    }

    /**
     * Counts every patient currently stored in the shared PatientLinkedList.
     */
    private int countPatients() {

        int count = 0;

        // Start from the first node
        PatientNode current = ClinicData.patientList.getHead();

        // Traverse the linked list
        while (current != null) {
            count++;
            current = current.next;
        }

        return count;
    }

    /**
     * Counts every doctor currently stored in the shared DoctorLinkedList.
     */
    private int countDoctors() {

        int count = 0;

        // Start from the first node
        DoctorNode current = ClinicData.doctorList.getHead();

        // Traverse the linked list
        while (current != null) {
            count++;
            current = current.next;
        }

        return count;
    }

    /**
     * Counts every consultation currently stored in the shared
     * ConsultationLinkedList.
     */
    private int countConsultations() {

        int count = 0;

        // Start from the first node
        ConsultationNode current = ClinicData.consultationList.getHead();

        // Traverse the linked list
        while (current != null) {
            count++;
            current = current.next;
        }

        return count;
    }

    /**
     * Looks up a patient's name by ID in the shared PatientLinkedList.
     * Returns "Unknown" if the patient no longer exists.
     */
    private String findPatientName(String patientID) {

        Patient patient = ClinicData.patientList.searchPatient(patientID);

        return (patient != null) ? patient.getPatientName() : "Unknown";
    }

    /**
     * Looks up a doctor's name by ID in the shared DoctorLinkedList. Returns
     * "Unknown" if the doctor no longer exists.
     */
    private String findDoctorName(String doctorID) {

        Doctor doctor = ClinicData.doctorList.searchDoctor(doctorID);

        return (doctor != null) ? doctor.getDoctorName() : "Unknown";
    }

    /**
     * Builds a "ID - Name" label for a patient, by looking them up in the
     * shared PatientLinkedList. Falls back to the raw ID if not found.
     */
    private String findPatientLabel(String patientID) {

        Patient patient = ClinicData.patientList.searchPatient(patientID);

        if (patient != null) {
            return patient.getPatientID() + " - " + patient.getPatientName();
        }

        return patientID;
    }

    /**
     * Populates the Recent Consultations table by traversing the shared
     * ConsultationLinkedList, resolving the Patient/Doctor names from their
     * respective linked lists via the stored IDs.
     */
    private void loadRecentConsultationsTable() {

        // Get the table model
        DefaultTableModel model
                = (DefaultTableModel) tblRecentConsultation.getModel();

        // Clear existing rows
        model.setRowCount(0);

        // Start from the first node
        ConsultationNode current = ClinicData.consultationList.getHead();

        // Traverse the linked list
        while (current != null) {

            model.addRow(new Object[]{
                current.consultation.getConsultationID(),
                findPatientName(current.consultation.getPatientID()),
                findDoctorName(current.consultation.getDoctorID()),
                current.consultation.getConsultationDate(),
                current.consultation.getDiagnosis()
            });

            current = current.next;
        }

    }

    /**
     * Validates the Global Search keyword and, if present, runs the search.
     * Shared by both the Search button and pressing Enter in the search box.
     */
    private void runGlobalSearch() {

        String keyword = txtGlobalSearch.getText().trim();

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a keyword.");
            return;
        }

        performGlobalSearch(keyword);
    }

    /**
     * Searches Patients (by ID, Name, or Phone Number), Doctors (by ID,
     * Name, or Phone Number), and Consultations (by ID or Diagnosis) using
     * Linear Search across each shared linked list, and populates the
     * Search Results table. Shows a message if nothing matched.
     */
    private void performGlobalSearch(String keyword) {

        DefaultTableModel model = (DefaultTableModel) tblGlobalSearchResults.getModel();
        model.setRowCount(0);

        String key = keyword.toLowerCase();

        // Search patients by ID, name, or phone number
        PatientNode p = ClinicData.patientList.getHead();
        while (p != null) {

            if (p.patient.getPatientID().toLowerCase().contains(key)
                    || p.patient.getPatientName().toLowerCase().contains(key)
                    || p.patient.getPhoneNumber().toLowerCase().contains(key)) {

                model.addRow(new Object[]{
                    "Patient",
                    p.patient.getPatientID(),
                    p.patient.getPatientName(),
                    "Phone: " + p.patient.getPhoneNumber()
                });
            }

            p = p.next;
        }

        // Search doctors by ID, name, or phone number
        DoctorNode d = ClinicData.doctorList.getHead();
        while (d != null) {

            if (d.doctor.getDoctorID().toLowerCase().contains(key)
                    || d.doctor.getDoctorName().toLowerCase().contains(key)
                    || d.doctor.getPhoneNumber().toLowerCase().contains(key)) {

                model.addRow(new Object[]{
                    "Doctor",
                    d.doctor.getDoctorID(),
                    d.doctor.getDoctorName(),
                    d.doctor.getSpecialization()
                });
            }

            d = d.next;
        }

        // Search consultations by ID or diagnosis
        ConsultationNode c = ClinicData.consultationList.getHead();
        while (c != null) {

            if (c.consultation.getConsultationID().toLowerCase().contains(key)
                    || c.consultation.getDiagnosis().toLowerCase().contains(key)) {

                model.addRow(new Object[]{
                    "Consultation",
                    c.consultation.getConsultationID(),
                    findPatientLabel(c.consultation.getPatientID()),
                    c.consultation.getDiagnosis()
                });
            }

            c = c.next;
        }

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No results found.");
        }

        // Clear the keyword after every search (found or not) - the results
        // table keeps showing the matches until the next search runs
        txtGlobalSearch.setText("");

    }
    
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnlHeader = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnDashboard = new javax.swing.JButton();
        btnPatients = new javax.swing.JButton();
        btnDoctors = new javax.swing.JButton();
        btnConsultation = new javax.swing.JButton();
        btnAbout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTotalPatients = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblTotalDoctors = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblTotalConsultations = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRecentConsultation = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtGlobalSearch = new javax.swing.JTextField();
        btnGlobalSearch = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGlobalSearchResults = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 204));

        pnlHeader.setBackground(new java.awt.Color(0, 51, 102));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/copy.png"))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("MediClinic");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 204));
        jPanel2.setName("pnlHeader"); // NOI18N

        btnDashboard.setBackground(new java.awt.Color(0, 102, 204));
        btnDashboard.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnDashboard.setText("Dashboard");
        btnDashboard.setBorder(null);

        btnPatients.setBackground(new java.awt.Color(0, 102, 204));
        btnPatients.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnPatients.setForeground(new java.awt.Color(255, 255, 255));
        btnPatients.setText("Patients");
        btnPatients.setBorder(null);
        btnPatients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPatientsActionPerformed(evt);
            }
        });

        btnDoctors.setBackground(new java.awt.Color(0, 102, 204));
        btnDoctors.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDoctors.setForeground(new java.awt.Color(255, 255, 255));
        btnDoctors.setText("Doctors");
        btnDoctors.setBorder(null);
        btnDoctors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoctorsActionPerformed(evt);
            }
        });

        btnConsultation.setBackground(new java.awt.Color(0, 102, 204));
        btnConsultation.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnConsultation.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultation.setText("Consultations");
        btnConsultation.setBorder(null);
        btnConsultation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultationActionPerformed(evt);
            }
        });

        btnAbout.setBackground(new java.awt.Color(0, 102, 204));
        btnAbout.setForeground(new java.awt.Color(255, 255, 255));
        btnAbout.setText("About");
        btnAbout.setBorder(null);
        btnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAbout, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDoctors, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPatients, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnConsultation, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnPatients)
                .addGap(39, 39, 39)
                .addComponent(btnDoctors)
                .addGap(37, 37, 37)
                .addComponent(btnConsultation)
                .addGap(32, 32, 32)
                .addComponent(btnAbout)
                .addContainerGap(163, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("DASHBOARD");

        jLabel2.setText("Welcome back! Here is your sytem overview");

        jPanel4.setBackground(new java.awt.Color(191, 214, 236));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setText("TOTAL PATIENTS");

        lblTotalPatients.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTotalPatients.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(lblTotalPatients)
                        .addGap(76, 76, 76))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalPatients, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(191, 214, 236));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setText("TOTAL DOCTORS");

        lblTotalDoctors.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTotalDoctors.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(lblTotalDoctors)
                        .addGap(87, 87, 87))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalDoctors, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        jPanel6.setBackground(new java.awt.Color(191, 214, 236));
        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setText("TOTAL CONSULTATIONS");

        lblTotalConsultations.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTotalConsultations.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(lblTotalConsultations)
                        .addGap(85, 85, 85))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(15, 15, 15))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotalConsultations, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tblRecentConsultation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Consultation ID", "Patient name", "Doctor Name", "Date", "Diagnotis"
            }
        ));
        jScrollPane1.setViewportView(tblRecentConsultation);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 204));
        jLabel9.setText("RECENT CONSULTATIONS");

        jPanel9.setBackground(new java.awt.Color(191, 214, 236));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 204));
        jLabel12.setText("GLOBAL SEARCH");

        txtGlobalSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGlobalSearchActionPerformed(evt);
            }
        });

        btnGlobalSearch.setBackground(new java.awt.Color(0, 102, 204));
        btnGlobalSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnGlobalSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        btnGlobalSearch.setText("Search");
        btnGlobalSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGlobalSearchActionPerformed(evt);
            }
        });

        jLabel15.setText("Search for patients,doctors or consultations");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 204));
        jLabel13.setText("SEARCH RESULTS");

        tblGlobalSearchResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Type", "ID", "Name", "Information"
            }
        ));
        tblGlobalSearchResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGlobalSearchResultsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGlobalSearchResults);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(47, 47, 47)
                                .addComponent(txtGlobalSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnGlobalSearch)))
                        .addGap(26, 26, 26))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(5, 5, 5)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtGlobalSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGlobalSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(63, 63, 63)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(0, 51, 102));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDoctorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoctorsActionPerformed
new DoctorsScreen().setVisible(true);
    dispose();
    }//GEN-LAST:event_btnDoctorsActionPerformed

    private void btnPatientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPatientsActionPerformed
        new PatientsScreen().setVisible(true);
        dispose();    }//GEN-LAST:event_btnPatientsActionPerformed

    private void btnConsultationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultationActionPerformed
new ConsultationScreen().setVisible(true);
    dispose();
    }//GEN-LAST:event_btnConsultationActionPerformed

    private void btnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutActionPerformed
JOptionPane.showMessageDialog(this,
        "MediClinic\nVersion 1.0\nDeveloped by Shantel Ruth Ndlovu");
    }//GEN-LAST:event_btnAboutActionPerformed

    private void txtGlobalSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGlobalSearchActionPerformed
        runGlobalSearch();
    }//GEN-LAST:event_txtGlobalSearchActionPerformed

    private void btnGlobalSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGlobalSearchActionPerformed
        runGlobalSearch();
    }//GEN-LAST:event_btnGlobalSearchActionPerformed

    private void tblGlobalSearchResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGlobalSearchResultsMouseClicked
if(evt.getClickCount()==2){

    int row = tblGlobalSearchResults.getSelectedRow();

    String type =
            tblGlobalSearchResults.getValueAt(row,0).toString();

    // Column 1 is always the ID of the matched record
    String id = tblGlobalSearchResults.getValueAt(row, 1).toString();

    if(type.equals("Patient")){

        PatientsScreen patientsScreen = new PatientsScreen();
        patientsScreen.selectPatientByID(id);
        patientsScreen.setVisible(true);

        dispose();

    }

    else if(type.equals("Doctor")){

        DoctorsScreen doctorsScreen = new DoctorsScreen();
        doctorsScreen.selectDoctorByID(id);
        doctorsScreen.setVisible(true);

        dispose();

    }

    else{

        ConsultationScreen consultationScreen = new ConsultationScreen();
        consultationScreen.selectConsultationByID(id);
        consultationScreen.setVisible(true);

        dispose();

    }
}
    }//GEN-LAST:event_tblGlobalSearchResultsMouseClicked
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DashboardScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbout;
    private javax.swing.JButton btnConsultation;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnDoctors;
    private javax.swing.JButton btnGlobalSearch;
    private javax.swing.JButton btnPatients;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTotalConsultations;
    private javax.swing.JLabel lblTotalDoctors;
    private javax.swing.JLabel lblTotalPatients;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JTable tblGlobalSearchResults;
    private javax.swing.JTable tblRecentConsultation;
    private javax.swing.JTextField txtGlobalSearch;
    // End of variables declaration//GEN-END:variables
}
