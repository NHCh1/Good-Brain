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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Student {
    private String studentID;
    private String name;
    private String ic;
    private String password;
    private String contact;
    private String email;
    private String intake;
//    private String assignedLecturer;
    
    private String newContact;
    private String newIntake;
//    private String newLectureAssigned;     
    
    private String icPrefix;
    
    private DefaultTableModel table;
    
    
    //Validate the details entered
    public Student(String name, String ic, String contact){
        this.name = name;
        this.ic = ic;
        this.contact = contact;       
    }
    
    //Add new student && Edit
    public Student(String id, String name, String ic, String contact, String email, String intake){
        this.studentID = id;
        this.name = name;
        this.ic = ic;
        this.contact = contact;
        this.newContact=contact;
        this.email = email;
        this.intake = intake;     
        this.newIntake = intake;
    }
    
    //Add new account details
    public Student(String id,String password){
        this.studentID = id;
        this.password = password;
    }
    
    //Update student details
//    public Student(String id, String name, String ic, String contact, String email, String intake, String lecture){
//        this.studentID = id;
//        this.name = name;
//        this.ic = ic;
//        this.newContact = contact;
//        this.email = email;
//        this.newIntake = intake;
//        this.assignedLecturer = lecture;
//    }
    
    public Student(DefaultTableModel table){
        this.table = table;
    }
    
    //Delete student from both student and user.txt
    public Student (DefaultTableModel table, String id){
        this.table = table;
        this.studentID = id;
    }
    
    //pass the assigned lecturer during lc registration
//    public Student (String lecturer){
//        this.assignedLecturer = lecturer;
//    }
    
    public String getStudentID(){
        createStudentID();
        return studentID;
    }

    public String getPassword(String ic){
        this.ic = ic;
        createPassword(ic);
        return password;
    }
    
    public String getEmail(){
        createEmail();
        return email;
    }
    
    public Student(){
        
    }
        
    private void createStudentID(){
        studentID = "";
        
        try{
            File file = new File("student.txt");
            if (!file.exists()) {
                file.createNewFile();
                studentID = "S001";
            }
            else{
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                List<String> studentIDs = new ArrayList<>();
                
                while ((line = br.readLine()) != null){
                    String [] data = line.split(",");
                    studentIDs.add(data[0]);
                }
                br.close();
                
                if (!studentIDs.isEmpty()) {
                    int lastStudentID = -1;

                    for (String id : studentIDs) {
                        int digit = Integer.parseInt(id.substring(1));
                        if (digit > lastStudentID) {
                            lastStudentID = digit;
                        }
                    }
                studentID = "S" + String.format("%03d", lastStudentID + 1);
                }
                else {
                    studentID = "S001"; // Set the first student ID if there are no existing students
                }
            }
        }
        catch(IOException ex){
            Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createPassword(String ic){
        if (ic.matches("\\d{6}-\\d{2}-\\d{4}")) {
            icPrefix = ic.substring(0, 6);
            password = icPrefix + studentID;
        }
    }
    
    private void createEmail(){
        email = studentID + "@mail.goodbrain.edu.my";
    }
     
    public List<String> studentValidation(){
        List<String> errors = new ArrayList<>();
        StringBuilder errorMessage = new StringBuilder();

        String nameError = validateName();
        if (!nameError.isEmpty()) {
            errors.add(nameError);
        }

        String icError = validateIC();
        if (!icError.isEmpty()) {
            errors.add(icError);
        }

        String contactError = validateContact();
        if (!contactError.isEmpty()) {
            errors.add(contactError);
        }
        
//        String intakeError = validateIntake();
//        if(!intakeError.isEmpty()){
//            errors.add(intakeError);
//        }
        
        return errors;
    }

    private String validateName(){
        if (name.isEmpty()){
            return "Name field is empty.";
        }
        if (!name.matches("[a-zA-Z ]+")){
            return "Name field contains invalid characters.";
        }
        return "";
    }

    private String validateIC(){
        if (ic.isEmpty()){
            return "IC Field is empty.";
        }
        if (!ic.matches("\\d{6}-\\d{2}-\\d{4}")){
            return "Please enter valid IC (E.g. : 123456-78-9012)";
        }
        return "";
    }

    private String validateContact(){
        if (contact.isEmpty()){
            return "Contact Field is empty.";
        }
        if(!contact.matches("^60[0-9]{9,10}$")){
            return "Contact number must be more than 11 or 12 digit.";
        }
        return "";
    }
    
    public void showErrorMessage(String field, String message){
        JOptionPane.showMessageDialog(null, message, "Error in" + field, JOptionPane.ERROR_MESSAGE);
    }
    
    public void addStudent(){
        try{
            FileWriter writer = new FileWriter("student.txt", true);            
            writer.write(studentID + "," + name + "," + ic + "," + contact + "," + email + "," + intake + "\n");
            writer.close();

            Icon icon = new ImageIcon(getClass().getResource("/Icon/success.png"));
            JOptionPane.showMessageDialog(null, "New student has been added! ", 
                                "Notification", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to add student! ", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void createStudentAccount(){
        try{
            FileWriter fw = new FileWriter("user.txt", true);
            fw.write(studentID + "," + password + "," + "3" + "\n");
            fw.close();
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Failed to add student! ", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void showStudent(){
        FileHandler fh = new FileHandler();
        fh.displayData("student.txt", table);
    }
       
    public void editStudent() throws IOException{
        try{
            ArrayList<String[]> data = new ArrayList<String[]>();
            BufferedReader br = new BufferedReader(new FileReader("student.txt"));
            String record;
            
            while ((record = br.readLine()) != null){
                String[] old = record.split(",");
                String[] row = new String[6];
                System.arraycopy(old, 0, row, 0, 6);
                data.add(row);
            }
            
            int selectedRowToUpdate = -1;
            for (int i = 0; i <data.size(); i++){
                if (data.get(i)[0].equals(studentID)){
                    selectedRowToUpdate = i;
                    break;
                }
            }
            if(selectedRowToUpdate != -1){
                String [] row = data.get(selectedRowToUpdate);
                
                row[3] = newContact;
                row[5] = newIntake;
//                row[6] = newLectureAssigned;            
            }
            
            BufferedWriter bw = new BufferedWriter(new FileWriter("student.txt"));
            for(String[] row : data){
                String newData = String.join(",", row);
                bw.write(newData);
                bw.newLine();
            }
            bw.close();
            Icon icon = new ImageIcon(getClass().getResource("/Icon/success.png"));
            JOptionPane.showMessageDialog(null, "Update Success! ", "Notification", 
                                JOptionPane.INFORMATION_MESSAGE, icon);
            
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
//    public void deleteStudent(){
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
//                    rowBuilder.append(",");
//                }
//            }
//            tableRows.add(rowBuilder.toString());
//        }
//        
//        try{
//            BufferedWriter bw = new BufferedWriter(new FileWriter("student.txt"));
//            for (String row : tableRows){
//                bw.write(row);
//                bw.newLine();
//            }
//            bw.close();
//    
//            table.setRowCount(0);
//            showStudent();
//            
//            Icon icon = new ImageIcon(getClass().getResource("/Icon/success.png"));
//            JOptionPane.showMessageDialog(null, "Student has been removed.", 
//                                "Notification", JOptionPane.INFORMATION_MESSAGE, icon);
//        }
//        catch(IOException ex){
//            Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    public void deleteStudent() {
        int rowQty = table.getRowCount();
        int colQty = table.getColumnCount();

        ArrayList<String> tableRows = new ArrayList<>();
        for (int i = 0; i < rowQty; i++) {
            StringBuilder rowBuilder = new StringBuilder();

            for (int j = 0; j < colQty - 1; j++) {
                rowBuilder.append(table.getValueAt(i, j));

                if (j != colQty - 2) {
                    rowBuilder.append(",");
                }
            }
            tableRows.add(rowBuilder.toString());
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("student.txt"));
            for (String row : tableRows) {
                bw.write(row);
                bw.newLine();
            }
            bw.close();

            FileHandler fh = new FileHandler();
            fh.deleteUserInformation(studentID);

            table.setRowCount(0);
            showStudent();

            Icon icon = new ImageIcon(getClass().getResource("/Icon/success.png"));
            JOptionPane.showMessageDialog(null, "Student has been removed.",
                    "Notification", JOptionPane.INFORMATION_MESSAGE, icon);
        } catch (IOException ex) {
            Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public void addAssignedLecturer() throws IOException{
//        try{
//            ArrayList<String[]> data = new ArrayList<String[]>();
//            BufferedReader br = new BufferedReader(new FileReader("student.txt"));
//            String record;
//            
//            while ((record = br.readLine()) != null){
//                String[] old = record.split(",");
//                data.add(old);
//            }
//            
//            int selectedRowToUpdate = -1;
//            for (int i = 0; i <data.size(); i++){
//                if (data.get(i)[0].equals(studentID)){
//                    selectedRowToUpdate = i;
//                    break;
//                }
//            }
//            if(selectedRowToUpdate != -1){
//                String [] row = data.get(selectedRowToUpdate);
//                row[7] = assignedLecturer;
//                                
//            }
//            
//            BufferedWriter bw = new BufferedWriter(new FileWriter("student.txt"));
//            for(String[] row : data){
//                String newData = String.join(",", row);
//                bw.write(newData);
//                bw.newLine();
//            }
//            bw.close();
//            Icon icon = new ImageIcon(getClass().getResource("/Icon/success.png"));
//            JOptionPane.showMessageDialog(null, "Update Success! ", "Notification", 
//                                JOptionPane.INFORMATION_MESSAGE, icon);
//            
//        }
//        catch (FileNotFoundException ex) {
//            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    public void addStudentIntoComboBox(JComboBox cb){
//        try{
//            cb.removeAllItems();           
//            BufferedReader br = new BufferedReader(new FileReader("student.txt"));
//            Object[] rows = br.lines().toArray();
//            
//            for (int i = 0; i < rows.length; i++){
//                String line = rows[i].toString();
//                String [] dataRow = line.split(",");
//                cb.addItem(dataRow[0]);
//            }
//        
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//    }   
}
    
   
