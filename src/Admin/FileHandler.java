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
import java.nio.file.Path;
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
                String[] dataRow = line.split(",");
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
    
    public void deleteUserInformation(String id) throws IOException {
        File inputFile = new File("user.txt");
        File tempFile = new File("tempUser.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains(id)) {
                continue; // Skip the line that contains the user ID
            }
            writer.write(line);
            writer.newLine();
        }

        reader.close();
        writer.close();

//        Files.deleteIfExists(Paths.get(inputFile.getPath()));
//        Files.move(Paths.get(tempFile.getPath()), Paths.get(inputFile.getPath()));
        
        inputFile.delete();
        tempFile.renameTo(inputFile);
    }
    
//    public void displayProtectedPassword(String fileName, DefaultTableModel table) {
//        File file = new File(fileName);
//
//        if (!file.exists()) {
//            table.setRowCount(0);
//            return;
//        }
//
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            Object[] tableRows = reader.lines().toArray();
//
//            for (int i = 0; i < tableRows.length; i++) {
//                String line = tableRows[i].toString().trim();
//                String[] dataRow = line.split(",");
//
//                // Mask the password
//                if (dataRow.length > 1 && dataRow[1] != null) {
//                    dataRow[1] = hidePassword(dataRow[1]);
//                }
//
//                table.addRow(dataRow);
//            }
//
//        } catch (FileNotFoundException ex) {
//            JOptionPane.showMessageDialog(null, "File not exist.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private String hidePassword(String password) {
//        StringBuilder hiddenPassword = new StringBuilder();
//        for (int i = 0; i < password.length(); i++) {
//            hiddenPassword.append("*");
//        }
//        return hiddenPassword.toString();
//    }
//    
//    public String showActualPassword(String hiddenPassword) {
//        StringBuilder actualPassword = new StringBuilder();
//        for (int i = 0; i < hiddenPassword.length(); i++) {
//            actualPassword.append(hiddenPassword.charAt(i));
//        }
//        return actualPassword.toString();
//    }
        
        
}
