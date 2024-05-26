/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class FileHandler {
    public FileHandler(){
        
    };
    
    public void displayData(String fileName, DefaultTableModel table) {
        File file = new File(fileName);

        if (!file.exists()) {
            table.setRowCount(0);
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Object[] tableRows = reader.lines().toArray();

            for (int i = 0; i < tableRows.length; i++) {
                String line = tableRows[i].toString().trim();
                String[] dataRow = line.split(";");
                table.addRow(dataRow);
            }

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "File not exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public int countTotalLecturer(){
        int count = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader("lecturer.txt"));
            while(br.readLine() != null){
                count ++;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return count;
    }
    
    public int countTotalStudent(){
        int count = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader("student.txt"));
            while(br.readLine() != null){
                count ++;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return count;
    }
    
    public int countTotalIntake(){
        int count = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader("intake.txt"));
            while(br.readLine() != null){
                count ++;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return count;
    }
    
    public List<String> readFile(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    public void writeFile(String filePath, List<String> lines) throws IOException {
        Files.write(Paths.get(filePath), lines);
    }

    public boolean deleteUserInformation(String filePath, String id) throws IOException {
        List<String> lines = readFile(filePath);
        List<String> updatedLines = new ArrayList<>();

        boolean found = false;
        for (String line : lines) {
            if (!line.startsWith(id)) {
                updatedLines.add(line);
            } else {
                found = true;
            }
        }

        if (found) {
            writeFile(filePath, updatedLines);
        }

        return found;
    }

    public void deleteFromUserFile(String userId) throws IOException {
        String userFilePath = "user.txt";
        deleteUserInformation(userFilePath, userId);
    }

    public void deleteFromSpecificFile(String userId) throws IOException {
        String filePath;
        if (userId.startsWith("S")) {
            filePath = "student.txt";
        } else if (userId.startsWith("L")) {
            filePath = "lecturer.txt";
        } else {
            throw new IllegalArgumentException("Unknown user ID prefix");
        }
        deleteUserInformation(filePath, userId);
    }  
}
