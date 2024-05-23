/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package project_manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class newProject extends javax.swing.JPanel {

    private pmHomePage homePage;
    private pmProjectInfo pjInfo;
    private DateTimeFormatter formatter;
    private boolean isEdit;
    private boolean initializing;
    
    
    private Map<String, String> lecturerMap;
    
    /**
     * Creates new form newProject
     * @param homePage
     * @param pjInfo
     * @param isEdit
     */
    public newProject(pmHomePage homePage, pmProjectInfo pjInfo, boolean isEdit) {
        this.homePage = homePage;
        this.pjInfo = pjInfo;
        this.isEdit = isEdit;
        initComponents();
        lecturerMap = new HashMap<>();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        loadLecturerData();
//        initializing = true;
        
        assignAllLecToBox();       
        populateIntakeComboBox();
        initializing = false;

        
        if (isEdit) {
            projID.setText(pjInfo.getProjectID());
            projName.setSelectedItem(pjInfo.getProjectName());
            projIntake.setSelectedItem(pjInfo.getIntake());
            
            String supervisorName = lecturerMap.get(pjInfo.getSupervisor());
            String secondMarkerName = lecturerMap.get(pjInfo.getSecondMarker());
            
            projSupervisor.setSelectedItem(supervisorName);
            projSecondMarker.setSelectedItem(secondMarkerName);
            
            projID.setEnabled(false);
            projName.setEnabled(false);
            projIntake.setEnabled(false);
            projSupervisor.setEnabled(false);
            projSecondMarker.setEnabled(false);
        } else {
            projID.setVisible(false);
            jLabel1.setVisible(false);
        }
        
        
        String dueDateString = pjInfo.getDueDate();
       
        if (dueDateString != null && !dueDateString.isEmpty()){
            try{
                LocalDate date = LocalDate.parse(dueDateString, formatter);
                projDueDate.setDate(Date.valueOf(date));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    private void loadLecturerData() {
        try (BufferedReader br = new BufferedReader(new FileReader("lecturer.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assuming the format of each line is: lecturerID,lecturerName
                String[] parts = line.split(";");
                if (parts.length >= 2) {
                    String lecturerID = parts[0].trim();
                    String lecturerName = parts[1].trim();
                    lecturerMap.put(lecturerID, lecturerName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void assignAllLecToBox(){
        projSupervisor.removeAllItems();
        projSecondMarker.removeAllItems();

        for (String lecturerName : lecturerMap.values()) {
            projSupervisor.addItem(lecturerName);
            projSecondMarker.addItem(lecturerName);
        }
    }
        
    private List<String> getAvailableIntakes(String filename){
        List<String> availableIntakes = new ArrayList<>();
        LocalDate today = LocalDate.now();
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split(";");
                String intakeID = parts[0];
                LocalDate dueDate = LocalDate.parse(parts[9], formatter);
                if(dueDate.isAfter(today)){
                    availableIntakes.add(intakeID);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return availableIntakes;
    }
        
    private String getAreaByIntake(String intakeID){
        String filename = "intake.txt";
        try(BufferedReader br  = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(";");
                if (parts[0].equals(intakeID)){
                    return parts[2];
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    
    private List<String> getLecturersByArea(String area){
        List<String> lecturersByArea = new ArrayList<>();
        String filename = "lecturer.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split(";");
                String majorArea = parts[5];
                String minorArea = parts[6];
                if (majorArea.equals(area) || minorArea.equals(area)){
//                    Lecturer lecturer = new Lecturer(parts[0], parts[1], parts[2], parts[3], parts[4], majorArea, minorArea, parts[7]);
//                    lecturersByArea.add(parts[0] + " - " + parts[1]);
                    lecturersByArea.add(parts[0]);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return lecturersByArea;
    }

    private void populateIntakeComboBox(){
        
        //Add available intakes
        List<String> availableIntakes = getAvailableIntakes("intake.txt");
        for (String intake : availableIntakes){
            projIntake.addItem(intake);
        }
        
        if (isEdit) {
            projID.setText(pjInfo.getProjectID());
            projName.setSelectedItem(pjInfo.getProjectName());
            projIntake.setSelectedItem(pjInfo.getIntake());

            String supervisorName = lecturerMap.get(pjInfo.getSupervisor());
            String secondMarkerName = lecturerMap.get(pjInfo.getSecondMarker());

            projSupervisor.setSelectedItem(supervisorName);
            projSecondMarker.setSelectedItem(secondMarkerName);
        } else {
            projIntake.addItem("");
            projSupervisor.addItem("");
            projSecondMarker.addItem("");
            
            //Add action listerner to projIntake combo box
        projIntake.addActionListener(e -> {
            if (initializing) return;
            
            Object selected = projIntake.getSelectedItem();
            if (selected == null) {
                System.out.println("error error");
            }
            
            String selectedIntake = projIntake.getSelectedItem().toString();
            if (!selectedIntake.isEmpty()){
                projSupervisor.removeAllItems();
                projSecondMarker.removeAllItems();
//                projSupervisor.addItem(""); // Add empty item for supervisors
//                projSecondMarker.addItem(""); // Add empty item for second markers
                String area = getAreaByIntake(selectedIntake);
                List<String> lecturersByArea = getLecturersByArea(area);

                for (String lecturer : lecturersByArea) {
                    String lecturerName = lecturerMap.get(lecturer);
                    if (lecturerName != null) {
                        projSupervisor.addItem(lecturerName);
                        projSecondMarker.addItem(lecturerName);
                    }
                }

            }
        });
        }

        
//        //Add action listerner to projIntake combo box
//        projIntake.addActionListener(e -> {
//            if (initializing) return;
//            
//            Object selected = projIntake.getSelectedItem();
//            if (selected == null) {
//                System.out.println("error error");
//            }
//            
//            String selectedIntake = projIntake.getSelectedItem().toString();
//            if (!selectedIntake.isEmpty()){
//                projSupervisor.removeAllItems();
//                projSecondMarker.removeAllItems();
////                projSupervisor.addItem(""); // Add empty item for supervisors
////                projSecondMarker.addItem(""); // Add empty item for second markers
//                String area = getAreaByIntake(selectedIntake);
//                List<String> lecturersByArea = getLecturersByArea(area);
//
//                for (String lecturer : lecturersByArea) {
//                    String lecturerName = lecturerMap.get(lecturer);
//                    if (lecturerName != null) {
//                        projSupervisor.addItem(lecturerName);
//                        projSecondMarker.addItem(lecturerName);
//                    }
//                }
//
//            }
//        });
    }
    
    
    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    

    private String generateProjectID(String projectName, String intake){
        String projectShortForm = "";
        switch (projectName){
            case "Internships":
                projectShortForm = "INT";
                break;
            case "Investigation Report":
                projectShortForm = "IR";
                break;
            case "RCMP":
                projectShortForm = "RCMP";
                break;
            case "Capstone Project P1":
                projectShortForm = "CP1";
                break;
            case "Capstone Project CP2":
                projectShortForm = "CP2";
                break;
            case "Final Year Project":
                projectShortForm = "FYP";
                break;
        }
        System.out.println(projectShortForm + intake);
        return projectShortForm + intake;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnBack = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnConfirm = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        projDueDate = new com.toedter.calendar.JDateChooser();
        projName = new javax.swing.JComboBox<>();
        projIntake = new javax.swing.JComboBox<>();
        projSupervisor = new javax.swing.JComboBox<>();
        projSecondMarker = new javax.swing.JComboBox<>();
        projID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel2.setText("Project Name :");

        jLabel3.setText("Intake :");

        jLabel4.setText("Supervisor :");

        jLabel5.setText("Second Marker :");

        btnConfirm.setText("Confirm");
        btnConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConfirmMouseClicked(evt);
            }
        });

        jLabel6.setText("Due Date :");

        projName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Internships", "Investigation Report", "RMCP", "Capstone Project P1", "Capstone Project P2", "Final Year Project" }));

        projIntake.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projIntakeActionPerformed(evt);
            }
        });

        jLabel1.setText("Project ID :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnBack))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnConfirm)
                                .addGap(300, 300, 300))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(157, 157, 157)
                                .addComponent(projDueDate, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addGap(164, 164, 164)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(projID)
                            .addComponent(projName, 0, 226, Short.MAX_VALUE)
                            .addComponent(projIntake, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(projSupervisor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(projSecondMarker, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(239, 239, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnBack)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(projID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(projName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(projIntake, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(projSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(projSecondMarker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(jLabel6))
                    .addComponent(projDueDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(btnConfirm)
                .addGap(38, 38, 38))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        homePage.backButtonActionPerformed();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnConfirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmMouseClicked
        // TODO add your handling code here:
        String projectID;
        if(isEdit){
            projectID = projID.getText();
        } else {
            projectID = generateProjectID(projName.getSelectedItem().toString(), projIntake.getSelectedItem().toString());
        }
        
        String projectName = projName.getSelectedItem().toString();
        String intake = projIntake.getSelectedItem().toString();
        String supervisor = projSupervisor.getSelectedItem().toString();
        String supervisorId = getKeyByValue(lecturerMap, supervisor);
        String secondMarker = projSecondMarker.getSelectedItem().toString();
        String secondMarkerId = getKeyByValue(lecturerMap, secondMarker);
        LocalDate duedate = projDueDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        String dueDateString = formatter.format(duedate);
        
        // Check if the supervisor and second marker are the same
        if(supervisor.equals(secondMarker)){
            JOptionPane.showMessageDialog(this,"The selected supervisor and second marker cannot be the same.");
            return;
        }
        
        pmProjectInfo pjInfo = new pmProjectInfo();
        pjInfo.setProjectID(projectID);
        pjInfo.setProjectName(projectName);
        pjInfo.setIntake(intake);
        pjInfo.setSupervisor(supervisorId);
        pjInfo.setSecondMarker(secondMarkerId);
        pjInfo.setDueDate(dueDateString);
        
        //Update the project file
        pmFunction func = new pmFunction();
        if(isEdit){
            try {
                func.updateProjectData("project.txt", pjInfo);
                System.out.println(pjInfo.getDueDate());
                JOptionPane.showMessageDialog(this,"Project updated successfully!");
            } catch(IOException e){
                JOptionPane.showMessageDialog(this, "Error updating project: " + e.getMessage());
            }
        } else {
            try{
                func.addProjectData("project.txt", pjInfo);
                JOptionPane.showMessageDialog(this, "Project added successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error adding project: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnConfirmMouseClicked

    private void projIntakeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projIntakeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_projIntakeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private com.toedter.calendar.JDateChooser projDueDate;
    private javax.swing.JTextField projID;
    private javax.swing.JComboBox<String> projIntake;
    private javax.swing.JComboBox<String> projName;
    private javax.swing.JComboBox<String> projSecondMarker;
    private javax.swing.JComboBox<String> projSupervisor;
    // End of variables declaration//GEN-END:variables
}
