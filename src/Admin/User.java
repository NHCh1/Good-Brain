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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class User {
    private String id;
    private String newPassword;
    private String newRole;
    private DefaultTableModel table;
    
    public User(){
        
    }
    
    public User(DefaultTableModel table, String id){
        this.table = table;
        this.id = id;
    }
    
    public User(String id, String newPwd, String newRole){
        this.id = id;
        this.newPassword = newPwd;
        this.newRole = newRole;
    }
    
    public User(DefaultTableModel tb){
        this.table = tb;
    }
    
    
    public void showUser(){
        FileHandler fh = new FileHandler();
        fh.displayData("user.txt", table);
    }
    
    public void updateUser() throws IOException{
        try{
            ArrayList<String[]> data = new ArrayList<String[]>();
            BufferedReader br = new BufferedReader(new FileReader("user.txt"));
            String rec;
            
            while((rec = br.readLine()) != null){
                String[] fileData = rec.split(";");
                String[] row = new String[3];
                System.arraycopy(fileData, 0, row, 0, 3);
                data.add(row);
            }
            
            int selectedRowToUpdate = -1;
            for(int i = 0; i < data.size(); i++){
                if(data.get(i)[0].equals(id)){
                    selectedRowToUpdate = i;
                    break;
                }
            }
            
            if(selectedRowToUpdate != -1){
                String[] row = data.get(selectedRowToUpdate);
                row[1] = newPassword;
                row[2] = newRole;
            }
            
            BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt"));
            for(String[] row : data){
                String updatedData = String.join(";", row);
                bw.write(updatedData);
                bw.newLine();
            }
            bw.close();
            Icon icon = new ImageIcon(getClass().getResource("/Icon/success.png"));
            JOptionPane.showMessageDialog(null, "Update Success! ", "Notification", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        catch (FileNotFoundException ex){
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public void deleteUser(){
//        int rowQty = table.getRowCount();
//        int colQty = table.getColumnCount();
//        
//        ArrayList<String> tableRows = new ArrayList<String>();
//        for(int i = 0; i < rowQty; i++){
//            StringBuilder rowBuilder = new StringBuilder();
//            
//            for (int j = 0; j < colQty - 1; j++) {
//                rowBuilder.append(table.getValueAt(i, j));
//                
//                if (j != colQty - 2) {
//                    rowBuilder.append(";");
//                }
//            }
//            tableRows.add(rowBuilder.toString());
//        }
//        
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt"))) {
//            for (String row : tableRows) {
//                bw.write(row);
//                bw.newLine();
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    
//        try {
//            processUserInformation(id);
//            table.setRowCount(0);
//            showUser();
//
//            Icon icon = new ImageIcon(getClass().getResource("/Icon/success.png"));
//            JOptionPane.showMessageDialog(null, "User has been removed.",
//                    "Notification", JOptionPane.INFORMATION_MESSAGE, icon);
//        } catch (IOException ex) {
//            Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
//            Icon icon = new ImageIcon(getClass().getResource("/Icon/error.png"));
//            JOptionPane.showMessageDialog(null, "Failed to remove user.",
//                    "Error", JOptionPane.ERROR_MESSAGE, icon);
//        }
//    }
    
    public void deleteUser() {
    int rowQty = table.getRowCount();
    int colQty = table.getColumnCount();

    ArrayList<String> tableRows = new ArrayList<>();
    for (int i = 0; i < rowQty; i++) {
        StringBuilder rowBuilder = new StringBuilder();

        for (int j = 0; j < colQty - 1; j++) {
            rowBuilder.append(table.getValueAt(i, j));

            if (j != colQty - 2) {
                rowBuilder.append(";");
            }
        }
        tableRows.add(rowBuilder.toString());
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt"))) {
        for (String row : tableRows) {
            bw.write(row);
            bw.newLine();
        }
    } catch (IOException ex) {
        Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
    }

    try {
        processUserInformation(id);
        table.setRowCount(0);
        showUser();

        Icon icon = new ImageIcon(getClass().getResource("/Icon/success.png"));
        JOptionPane.showMessageDialog(null, "User has been removed.",
                "Notification", JOptionPane.INFORMATION_MESSAGE, icon);
    } catch (IOException ex) {
        Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
        Icon icon = new ImageIcon(getClass().getResource("/Icon/error.png"));
        JOptionPane.showMessageDialog(null, "Failed to remove user.",
                "Error", JOptionPane.ERROR_MESSAGE, icon);
    }
}

    
    private void processUserInformation(String userID) throws IOException {
        String fileName = "";
        if (userID.startsWith("S")) {
            fileName = "student.txt";
        } else if (userID.startsWith("L")) {
            fileName = "lecturer.txt";
        }

        File inputFile = new File(fileName);
        File tempFile = new File("temp" + fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(userID)) {
                    continue; // Skip the line containing the user ID
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Error processing user information", e);
        }

        if (!deleteFileWithRetry(inputFile, 3, 1000)) {
            throw new IOException("Failed to delete original file after retries: " + fileName);
        }

        if (!tempFile.renameTo(inputFile)) {
            throw new IOException("Failed to rename temporary file: " + tempFile.getName());
        }
    }

    private boolean deleteFileWithRetry(File file, int attempts, long waitTimeMillis) {
        for (int i = 0; i < attempts; i++) {
            if (file.delete()) {
                return true;
            }
            try {
                Thread.sleep(waitTimeMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }


    
//    private void processUserInformation(String userID) throws IOException {
//        String fileName = "";
//        if (userID.startsWith("S")) {
//            fileName = "student.txt";
//        } else if (userID.startsWith("L")) {
//            fileName = "lecturer.txt";
//        }
//
//        File inputFile = new File(fileName);
//        File tempFile = new File("temp" + fileName);
//
//        BufferedReader br = null;
//        BufferedWriter bw = null;
//
//        try {
//            br = new BufferedReader(new FileReader(inputFile));
//            bw = new BufferedWriter(new FileWriter(tempFile));
//
//            String line;
//            while ((line = br.readLine()) != null) {
//                if (line.contains(userID)) {
//                    continue; // Skip the line containing the user ID
//                }
//                bw.write(line);
//                bw.newLine();
//            }
//        }
//        catch (IOException e) {
//            throw new IOException("Error processing user information", e);
//        }
//        finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (bw != null) {
//                try {
//                    bw.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        // Ensure the original file is deleted and the temp file is renamed atomically
//        if (!inputFile.delete()) {
//            throw new IOException("Failed to delete original file: " + fileName);
//        }
//
//        if (!tempFile.renameTo(inputFile)) {
//            throw new IOException("Failed to rename temporary file: " + tempFile.getName());
//        }
//    }


    
//    private void processUserInformation(String userID) throws IOException {
//        String fileName = "";
//        if (userID.startsWith("S")) {
//            fileName = "student.txt";
//        }
//        else if (userID.startsWith("L")) {
//            fileName = "lecturer.txt";
//        }
//
//        File inputFile = new File(fileName);
//        File tempFile = new File("temp" + fileName);
//
//        BufferedReader br = new BufferedReader(new FileReader(inputFile));
//        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
//
//        String line;
//        while ((line = br.readLine()) != null) {
//            if (line.contains(userID)) {
//                continue;
//            }
//            bw.write(line);
//            bw.newLine();
//        }
//
//        br.close();
//        bw.close();
//
//        inputFile.delete();
//        tempFile.renameTo(inputFile);
//    }
}

