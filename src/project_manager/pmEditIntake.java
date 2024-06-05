/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package project_manager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import static project_manager.pmNewProject.getKeyByValue;
import project_manager.pmHomePage;
import project_manager.pmIntakeInfo;

/**
 *
 * @author User
 */
public class pmEditIntake extends javax.swing.JPanel {
    private pmHomePage homePage;
    private pmIntakeInfo intInfo;
    private DateTimeFormatter formatter;
    private Map<String, String> lecturerMap;
    

    /**
     * Creates new form pmEditIntake1
     * @param homePage
     * @param intInfo
     * @throws java.io.FileNotFoundException
     */
    public pmEditIntake(pmHomePage homePage, pmIntakeInfo intInfo){
        this.homePage = homePage;
        this.intInfo = intInfo;
        
        initComponents();
        lecturerMap = new HashMap<>();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
//        loadLecturerData();
        loadLecIntoBox();
        
        intID.setText(intInfo.getIntakeID());
        intStudyLevel.setText(intInfo.getIntakeStudyLevel());
        intName.setText(intInfo.getIntakeName());
        intDuration.setText(intInfo.getIntakeDuration());
        String supervisorName = lecturerMap.get(intInfo.getIntakeSupervisor());
        String secondMarkerName = lecturerMap.get(intInfo.getIntakeSecondMarker());

        intSupervisor.setSelectedItem(supervisorName);
        intSecondMarker.setSelectedItem(secondMarkerName);
        
        String regStartString = intInfo.getIntakeRegisterStart();
        String regEndString = intInfo.getIntakeRegisterEnd();
        String startString = intInfo.getIntakeStart();
        String endString = intInfo.getIntakeEnd();

        try{
            LocalDate rStartDate = LocalDate.parse(regStartString, formatter);
            LocalDate rEndDate = LocalDate.parse(regEndString, formatter);
            LocalDate startDate = LocalDate.parse(startString, formatter);
            LocalDate endDate = LocalDate.parse(endString, formatter);
            intRegStart.setDate(Date.valueOf(rStartDate));
            intRegEnd.setDate(Date.valueOf(rEndDate));
            intStart.setDate(Date.valueOf(startDate));
            intEnd.setDate(Date.valueOf(endDate));
        } catch (Exception e){
            e.printStackTrace();
        }
        
        intID.setEnabled(false);
        intStudyLevel.setEnabled(false);
        intName.setEnabled(false);
        intDuration.setEnabled(false);
        intRegStart.setEnabled(false);
        intRegEnd.setEnabled(false);
        intStart.setEnabled(false);
        intEnd.setEnabled(false);
    }
    
//    private void loadLecturerData() {
//        try (BufferedReader br = new BufferedReader(new FileReader("lecturer.txt"))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                // Assuming the format of each line is: lecturerID,lecturerName
//                String[] parts = line.split(";");
//                if (parts[0].equals(intID.getText()) {
//                    String lecturerID = parts[0].trim();
//                    String lecturerName = parts[1].trim();
//                    lecturerMap.put(lecturerID, lecturerName);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void loadLecIntoBox(){
        List<String> lecturersByArea = new ArrayList<>();
        String intakeArea = intName.getText();

        try (BufferedReader br = new BufferedReader(new FileReader("lecturer.txt"))) {
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split(";");
                String majorArea = parts[5];
                String minorArea = parts[6];
                if (majorArea.equals(intakeArea) || minorArea.equals(intakeArea)){
                    lecturersByArea.add(parts[0]);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        //Add default item for no supervisor or second marker
        intSupervisor.addItem("-");
        intSecondMarker.addItem("-");

        for (String lecturer : lecturersByArea) {
            String lecturerName = lecturerMap.get(lecturer);
            if (lecturerName != null) {
                intSupervisor.addItem(lecturerName);
                intSecondMarker.addItem(lecturerName);
            }
        }

        //Set the initial selection for supervisor and second marker
        String supervisorName = lecturerMap.get(intInfo.getIntakeSupervisor());
        String secondMarkerName = lecturerMap.get(intInfo.getIntakeSecondMarker());

        if(supervisorName != null){
            intSupervisor.setSelectedItem(supervisorName);
        } else {
            intSupervisor.setSelectedItem(supervisorName);
        }

        if(secondMarkerName != null){
            intSecondMarker.setSelectedItem(secondMarkerName);
        } else {
            intSecondMarker.setSelectedItem("-");
        }
    }

    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        intName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        intDuration = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        intStudyLevel = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        intRegStart = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        intRegEnd = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        intStart = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        intEnd = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        intID = new javax.swing.JTextField();
        btnConfirm = new javax.swing.JButton();
        intSupervisor = new javax.swing.JComboBox<>();
        intSecondMarker = new javax.swing.JComboBox<>();

        jLabel2.setText("Intake Name :");

        jLabel3.setText("Duration :");

        jLabel4.setText("Supervisor :");

        jLabel5.setText("Second Marker :");

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel6.setText("Study Level :");

        jLabel7.setText("Registration Start Date:");

        jLabel8.setText("Registration End Date:");

        jLabel9.setText("Intake Start Date :");

        jLabel10.setText("Intake End Date :");

        jLabel1.setText("Intake ID :");

        intID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intIDActionPerformed(evt);
            }
        });

        btnConfirm.setText("Confirm");
        btnConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConfirmMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(intID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5))
                                        .addGap(26, 26, 26)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(intDuration)
                                            .addComponent(intName, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(intSupervisor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(intSecondMarker, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(136, 136, 136)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(128, 128, 128)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(intStudyLevel)
                            .addComponent(intRegStart, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                            .addComponent(intRegEnd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(intStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(intEnd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(383, 383, 383)
                        .addComponent(btnConfirm))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(btnBack)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnBack)
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6)
                            .addComponent(intID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(intStudyLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel7)
                                    .addComponent(intName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(intRegStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addComponent(intDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(intRegEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9)
                            .addComponent(intSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(intStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10)
                            .addComponent(intSecondMarker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnConfirm))
                    .addComponent(intEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void intIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_intIDActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        homePage.backToIntakeButtonActionPerformed();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnConfirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmMouseClicked
        String intakeID = intID.getText();
        String intakeLevel = intStudyLevel.getText();
        String intakeName = intName.getText();
        String duration = intDuration.getText();
        String supervisor = intSupervisor.getSelectedItem().toString();
        String supervisorId = getKeyByValue(lecturerMap, supervisor);
        String secondMarker = intSecondMarker.getSelectedItem().toString();
        String secondMarkerId = getKeyByValue(lecturerMap, secondMarker);
        LocalDate regStart = intRegStart.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate regEnd = intRegEnd.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate start = intStart.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = intEnd.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        String regStartString = formatter.format(regStart);
        String regEndString = formatter.format(regEnd);
        String startString = formatter.format(start);
        String endString = formatter.format(end);
        
        if(supervisor.equals(secondMarker)){
            JOptionPane.showMessageDialog(this,"The selected supervisor and second marker cannot be the same.");
            return;
        }
        
        pmIntakeInfo intInfo = new pmIntakeInfo();
        intInfo.setIntakeID(intakeID);
        intInfo.setIntakeStudyLevel(intakeLevel);
        intInfo.setIntakeName(intakeName);
        intInfo.setIntakeDuration(duration);
        intInfo.setIntakeSupervisor(supervisorId);
        intInfo.setIntakeSecondMarker(secondMarkerId);
        intInfo.setIntakeRegisterStart(regStartString);
        intInfo.setIntakeRegisterEnd(regEndString);
        intInfo.setIntakeStart(startString);
        intInfo.setIntakeEnd(endString);
        
        pmProjectInfo projInfo = new pmProjectInfo();
        
        // Update intake file
        pmFunction func = new pmFunction();
//        func.modifyIntakeLec("intake.txt","project.txt",intInfo);
    }//GEN-LAST:event_btnConfirmMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JTextField intDuration;
    private com.toedter.calendar.JDateChooser intEnd;
    private javax.swing.JTextField intID;
    private javax.swing.JTextField intName;
    private com.toedter.calendar.JDateChooser intRegEnd;
    private com.toedter.calendar.JDateChooser intRegStart;
    private javax.swing.JComboBox<String> intSecondMarker;
    private com.toedter.calendar.JDateChooser intStart;
    private javax.swing.JTextField intStudyLevel;
    private javax.swing.JComboBox<String> intSupervisor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
