/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_manager;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author User
 */
public class pmFunction {
    
    private pmProjectInfo updatedInfo;
    
    public static ArrayList<String[]> readFile(String fileName) {
        ArrayList<String[]> dataList = new ArrayList<>();
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split(";");
                dataList.add(parts);
            }
            
        } catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return dataList;
    }
    
    public void setTable (String fileName, JTable table) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split(";");
                model.addRow(parts);
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    public void setTable (String fileName, JTable table, String filter) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split(";");
//                if(filter == null || filter.isEmpty() || matchesFilter(parts, filter))
                model.addRow(parts);
            }
            br.close();
            
        } catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    public void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.setRowCount(0);
    }
    
    public void updateProjectData(String filename, pmProjectInfo updatedInfo) throws IOException {
        File file = new File(filename);
        List<String> pjDataList = Files.readAllLines(file.toPath());
//        System.out.println(file.toPath());
        
        for (int i = 0; i < pjDataList.size(); i++) {
            String[] pjData = pjDataList.get(i).split(";");
            if (pjData.length >= 1 && pjData[0].equals(updatedInfo.getProjectID())) {
                StringBuilder updatedLine = new StringBuilder();
                updatedLine.append(updatedInfo.getProjectID()).append(";").append(updatedInfo.getProjectName()).append(";")
                        .append(updatedInfo.getIntake()).append(";").append(updatedInfo.getSupervisor()).append(";")
                        .append(updatedInfo.getSecondMarker()).append(";").append(updatedInfo.getDueDate());
                pjDataList.set(i, updatedLine.toString());
                break;
            }
        }
        Files.write(file.toPath(), pjDataList);
    }
    
    public void updateProjectLec(String filename, String projID, String supervisor, String secondMarker) throws IOException{
        File file = new File(filename);
        List<String> preDataList = Files.readAllLines(file.toPath());
        
        for (int i = 0; i < preDataList.size(); i++) {
            String[] preData = preDataList.get(i).split(";");
            
            if (preData.length >= 1 && preData[2].equals(projID)) {
                preData[6] = supervisor;
                preData[8] = secondMarker == null ? "-" : secondMarker;
                System.out.println(secondMarker);
//                StringBuilder updatedLine = new StringBuilder();
//                updatedLine.append(updatedInfo.getProjectID()).append(";").append(updatedInfo.getProjectName()).append(";")
//                        .append(updatedInfo.getIntake()).append(";").append(updatedInfo.getSupervisor()).append(";")
//                        .append(updatedInfo.getSecondMarker()).append(";").append(updatedInfo.getDueDate());
                String updatedLine = String.join(";", preData);
                preDataList.set(i, updatedLine);
            }
        }
        Files.write(file.toPath(), preDataList);
    }
    
    public void addProjectData(String filename, pmProjectInfo newProject) throws IOException {
        File file = new File(filename);
        
        List<String> pjDataList = Files.readAllLines(file.toPath());
        
        for (String line : pjDataList){
            String[] pjData = line.split(";");
            if (pjData.length >= 1 && pjData[0].equals(newProject.getProjectID())){
                JOptionPane.showMessageDialog(null, "Error updating project");
                return;     //Exit the method if the project ID already exists
            }
        }
        
        // If project ID doesn't exist, add new project
        StringBuilder newLine = new StringBuilder();
        newLine.append(newProject.getProjectID()).append(";")
                .append(newProject.getProjectName()).append(";")
                .append(newProject.getIntake()).append(";")
                .append(newProject.getSupervisor()).append(";")
                .append(newProject.getSecondMarker()).append(";")
                .append(newProject.getDueDate());
        pjDataList.add(newLine.toString());
        
        //Write the updated list back to the file
        Files.write(file.toPath(), pjDataList);
//        JOptionPane.showMessageDialog(null, "Success updating project");
    }
    
//    public void modifyIntakeLec(String intakeFilename, String projectFilename, pmIntakeInfo mofidyIntake, pmProjectInfo updatedInfo) throws IOException{
//        File intakeFile = new File(intakeFilename);
//        List<String> intDataList = Files.readAllLines(intakeFile.toPath());
//        
//        for (int i = 0; i < intDataList.size(); i++) {
//            String[] pjData = intDataList.get(i).split(";");
//            if (pjData.length >= 1 && pjData[0].equals(mofidyIntake.getIntakeID())) {
//                StringBuilder updatedLine = new StringBuilder();
//                updatedLine.append(mofidyIntake.getIntakeID()).append(";").append(mofidyIntake.getIntakeStudyLevel()).append(";")
//                        .append(mofidyIntake.getIntakeName()).append(";").append(mofidyIntake.getIntakeDuration()).append(";")
//                        .append(mofidyIntake.getIntakeSupervisor()).append(";").append(mofidyIntake.getIntakeSecondMarker())
//                        .append(";").append(mofidyIntake.getIntakeRegisterStart()).append(mofidyIntake.getIntakeRegisterEnd()).append(";")
//                        .append(mofidyIntake.getIntakeStart()).append(";").append(mofidyIntake.getIntakeEnd()).append(";");
//                intDataList.set(i, updatedLine.toString());
//                break;
//            }
//        }
//        Files.write(intakeFile.toPath(), intDataList);
//        
//        // Update project information in the project file
//        File projectFile = new File(projectFilename);
//        List<String> pjDataList = Files.readAllLines(projectFile.toPath());
//        
//        for (int i = 0; i < pjDataList.size(); i++) {
//            String[] pjData = pjDataList.get(i).split(";");
//            if (pjData.length >= 1 && pjData[2].equals(mofidyIntake.getIntakeID()) && !pjData[1].equalsIgnoreCase("Final Year Project")) {
//                StringBuilder updatedLine = new StringBuilder();
//                updatedLine.append(pjData[0]).append(";")
//                        .append(pjData[1]).append(";")
//                        .append(pjData[2]).append(";")
//                        .append(updatedInfo.getSupervisor()).append(";")
//                        .append(updatedInfo.getSecondMarker()).append(";")
//                        .append(pjData[5]);
//                pjDataList.set(i, updatedLine.toString());
//                break;
//            }
//        }
//        Files.write(projectFile.toPath(), pjDataList);
//    }
               
    public void deleteData(String filename, String projectID) throws IOException{
        ArrayList<String[]> pjDataList = readFile(filename);
        
        // Find and remove the project with the specified projectID
        pjDataList.removeIf(pjData -> pjData[0].equals(projectID));
        
        // Overwrite the file with the updated data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for(String[] pjData : pjDataList){
                writer.write(String.join(";", pjData));
                writer.newLine();
            }
        } catch (IOException e){
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    public void addPlaceholder(JTextField textField){
        Font font = textField.getFont();
        font = font.deriveFont(Font.ITALIC);
        textField.setFont(font);
        textField.setForeground(Color.GRAY);
    }
    
    public void removePlaceholder(JTextField textField){
        Font font = textField.getFont();
        font = font.deriveFont(Font.PLAIN);
        textField.setFont(font);
        textField.setForeground(Color.BLACK);
    }
    
    public void searchFocusGained(JTextField searchName){
        if (searchName.getText().equals("Search here")){
            searchName.setText(null);
            searchName.requestFocus();
            removePlaceholder(searchName);
        }
    }
    
    public void searchFocusLost(JTextField searchName){
        if (searchName.getText().length() == 0){
            addPlaceholder(searchName);
            searchName.setText("Search here");
        }
    }
    
    public void searchKeyPressed (JTable table, JTextField searchName){
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        TableRowSorter <DefaultTableModel> row = new TableRowSorter <DefaultTableModel>(model);
        table.setRowSorter(row);
        row.setRowFilter(RowFilter.regexFilter(searchName.getText()));
    }
}

