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

//        inputFile.delete();
//        tempFile.renameTo(inputFile);
        if (!inputFile.delete()) {
            throw new IOException("Failed to delete original user file");
        }

        if (!tempFile.renameTo(inputFile)) {
            throw new IOException("Failed to rename temporary user file");
        }
    }
        
        
}
