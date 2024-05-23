/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_manager;

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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class pmFunction {
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
//        ArrayList<String[]> dataList = new ArrayList<>();
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split(";");
                model.addRow(parts);
//                dataList.add(parts);
            }
            
        } catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    public void setTable (String fileName, JTable table, String id) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
//        ArrayList<String[]> dataList = new ArrayList<>();
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split(";");
                model.addRow(parts);
//                dataList.add(parts);
            }
            
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
        System.out.println(file.toPath());
        
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
    
    public void addProjectData(String filename, pmProjectInfo newProject) throws IOException {
        File file = new File(filename);
        List<String> pjDataList = Files.readAllLines(file.toPath());
//        System.out.println(file.toPath());
        
        for (String line : pjDataList){
            String[] pjData = line.split(";");
            if (pjData.length >= 1 && pjData[0].equals(newProject.getProjectID())){
                System.out.println("Project with ID " + newProject.getProjectID() + " already exists.");
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

//        Files.write(file.toPath(), pjDataList);
    }
               
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
}

