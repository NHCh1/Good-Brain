/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

import Admin.AdminPages;
import Admin.FileHandler;
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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Lecturer {
    private String lectureID;
    private String password;
    private String name;
    private String ic;
    private String contact;
    private String email;
    private String major;
    private String minor;
//    private String student;
    private String projectManager;
    
    private int lastLecturerID = 0;
    private String icPrefix;
    
    private String newContact;
    private String newMajor;
    private String newMinor;
    private String isPMRole;
    
    private DefaultTableModel table;
    
    
    //Validation purpose
    public Lecturer(String name, String ic, String contact){
        this.name = name;
        this.ic = ic;
        this.contact = contact;
//        this.role = role;
//        this.student = student;
//        this.projectManager = pm;
    }
    
    
    //Add lecturer
    public Lecturer(String id, String name, String ic, String contact, String email, String major, String minor){
        this.lectureID = id;
        this.name = name;
        this.ic = ic;
        this.contact = contact;
        this.email = email;
        this.major = major;
        this.minor = minor; 
    }
    
    //Update lecturer
    public Lecturer(String id, String name, String ic, String contact, String email, String major, String minor, String pm){
        this.lectureID = id;
        this.name = name;
        this.ic = ic;
        this.newContact = contact;
        this.newMajor = major;
        this.newMinor = minor;
        this.isPMRole = pm;
    }
    
    public Lecturer (String id, String password){
        this.lectureID = id;
        this.password = password;
    }
    
    public Lecturer(DefaultTableModel table){
        this.table = table;
    }
    
    //For delete purpose
    public Lecturer(String id){
        this.lectureID = id;
    }
    
    public Lecturer(){
        
    }
    
    // ----------- REGISTRATION By INDIVIDUAL STARTS -----------
    public String getEmail(){
        createEmail();
        return email;
    }
    
    public String getPassword(String ic){
        this.ic = ic;
        createPassword(ic);
        return password;
    }
    
    public String getLectureID(){
        createLecturerID();
        return lectureID;
    }
    
    public List<String> lecturerValidation(){
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
    
    
    private void createEmail(){
        email = lectureID + "@staff.goodbrain.edu.my";
    }
    
    private void createPassword(String ic){
        if (ic.matches("\\d{6}-\\d{2}-\\d{4}")) {
            icPrefix = ic.substring(0, 6);
            password = icPrefix + lectureID;
        }
    }
        
    public void createLecturerID(){
        lectureID = "";
        
        try{
            File file = new File("lecturer.txt");
            if (!file.exists()) {
                file.createNewFile();
                lectureID = "L001";
            }
            else{
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                List<String> lectureIDs = new ArrayList<>();
                
                while ((line = br.readLine()) != null){
                    String [] data = line.split(";");
                    lectureIDs.add(data[0]);
                }
                br.close();
                
                if (!lectureIDs.isEmpty()) {
                    int lastLectureID = -1;

                    for (String id : lectureIDs) {
                        int digit = Integer.parseInt(id.substring(1));
                        if (digit > lastLectureID) {
                            lastLectureID = digit;
                        }
                    }
                lectureID = "L" + String.format("%03d", lastLectureID + 1);
                }
                else {
                    lectureID = "L001";
                }
            }
        }
        catch(IOException ex){
            Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void createLectureAccount(){
        try{
            FileWriter fw = new FileWriter("user.txt", true);
            fw.write(lectureID + ";" + password + ";" + "2" + "\n");
            fw.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Failed to add user! ", "Error",
                    JOptionPane.ERROR_MESSAGE);
            }   
    }
    
    public void addLecturer(){
    String minorEmpty = (minor == null || minor.isEmpty()) ? "-" : minor;
        
        try{
            FileWriter fw = new FileWriter("lecturer.txt", true);
            fw.write(lectureID + ";" + name + ";" + ic + ";" + contact + ";" + email + ";" + major + ";" 
                    + minorEmpty + ";" + "-" + "\n");
            fw.close();

            Icon icon = new ImageIcon(getClass().getResource("/Icon/success.png"));
            JOptionPane.showMessageDialog(null, "New lecturer has been added! ", 
                                "Notification", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(null, "Failed to add lecturer! ", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        
    }
    // ----------- REGISTRATION By INDIVIDUAL ENDS -----------
    
    
    
    // ------- Registration by Group STARTS -------
    public List<String[]> loadExistingRecords() {
        List<String[]> existingRecords = new ArrayList<>();
        try {
            File file = new File("lecturer.txt");
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    existingRecords.add(line.split(";"));
                }
                br.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Lecturer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existingRecords;
    }

    public void initializeLastLecturerID() {
        try {
            File file = new File("lecturer.txt");
            if (!file.exists()) {
                lastLecturerID = 0;
            } else {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                List<String> studentIDs = new ArrayList<>();

                while ((line = br.readLine()) != null) {
                    String[] data = line.split(";");
                    studentIDs.add(data[0]);
                }
                br.close();

                if (!studentIDs.isEmpty()) {
                    int highestID = 0;
                    for (String id : studentIDs) {
                        int digit = Integer.parseInt(id.substring(1));
                        if (digit > highestID) {
                            highestID = digit;
                        }
                    }
                    lastLecturerID = highestID;
                } else {
                    lastLecturerID = 0; // Initialize if no students are present in the file
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String createLectureIDForGroup() {
        lastLecturerID++;
        lectureID = "L" + String.format("%03d", lastLecturerID);
        return lectureID;
    }
    
    public String createPasswordForGroup(String ic, String id){
        if(ic.matches("\\d{6}-\\d{2}-\\d{4}")){
            icPrefix = ic.substring(0,6);
            return icPrefix + lectureID;
        }
        return null;
    }
    
    public String createEmailForGroup (String lectureID){
        return lectureID + "@staff.goodbrain.edu.my";
    }
     
    public void addLecturer(List<String> lectureData){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("lecturer.txt", true))){
            for(String data : lectureData){
                bw.write(data);
                bw.newLine();
            }
            Icon icon = new ImageIcon(getClass().getResource("/Icon/success.png"));
            JOptionPane.showMessageDialog(null, "Lecturers has been added! ","Notification", JOptionPane.INFORMATION_MESSAGE, icon);
            }
        
        catch (IOException ie){
            JOptionPane.showMessageDialog(null, "Failed to add lecturer! ", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void createLectureAccount(List<String> rec){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt", true))){
            for(String data : rec){
                bw.write(data);
                bw.newLine();
            }
        }
        catch (IOException ie){
            JOptionPane.showMessageDialog(null, "Failed to add users! ", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    // ------- Registration by Group END -------
    
    
    //add PM validation during update
    public void updateLecturer() throws IOException {
        try{
            ArrayList<String[]> data = new ArrayList<String[]>();
            BufferedReader br = new BufferedReader(new FileReader("lecturer.txt"));
            String record;
            
            while ((record = br.readLine()) != null){
                String[] old = record.split(";");
                String[] row = new String[8];
                System.arraycopy(old, 0, row, 0, 8);
                data.add(row);
            }
            
            int selectedRowToUpdate = -1;
            for (int i = 0; i <data.size(); i++){
                if (data.get(i)[0].equals(lectureID)){
                    selectedRowToUpdate = i;
                    break;
                }
            }
            if(selectedRowToUpdate != -1){
                String [] row = data.get(selectedRowToUpdate);
                row[3] = newContact;
                row[5] = newMajor;
                row[6] = newMinor;
                row[7] = isPMRole;
                
                updateRoleBasedOnPMValue(lectureID, isPMRole);
            }
            
            BufferedWriter bw = new BufferedWriter(new FileWriter("lecturer.txt"));
            for(String[] row : data){
                String newData = String.join(";", row);
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
    
    
    public void updateRoleBasedOnPMValue(String id, String pm) throws IOException{
        try{
            ArrayList<String[]> data = new ArrayList<String[]>();
            BufferedReader br = new BufferedReader(new FileReader("user.txt"));
            String record;
            
            while ((record = br.readLine()) != null){
                String[] old = record.split(";");
                data.add(old);
            }
            
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[0].equals(lectureID)) {
                    String[] row = data.get(i);
                    if (pm.equals("Yes")) {
                        row[2] = "4"; // Update to project manager
                    } else if (pm.equals("No")) {
                        row[2] = "2"; // Keep as project manager
                    }
                    data.set(i, row);
                    break;
                }
            }
            
            BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt"));
            for(String[] row : data){
                String newData = String.join(";", row);
                bw.write(newData);
                bw.newLine();
            }
            bw.close();
            
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        public void deleteLecturer() {
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

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("lecturer.txt"));
                for (String row : tableRows) {
                    bw.write(row);
                    bw.newLine();
                }
                bw.close();

                FileHandler fh = new FileHandler();
                fh.deleteFromSpecificFile(lectureID);
                fh.deleteFromUserFile(lectureID);

                table.setRowCount(0);
                showLecturer();

                Icon icon = new ImageIcon(getClass().getResource("/Icon/success.png"));
                JOptionPane.showMessageDialog(null, "Lecturer has been removed.",
                        "Notification", JOptionPane.INFORMATION_MESSAGE, icon);
            } catch (IOException ex) {
                Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public void showLecturer(){
        FileHandler fh = new FileHandler();
        fh.displayData("lecturer.txt", table);
    }
    
    public void addLecturerListIntoComboBox(JComboBox cb){
         try{
            cb.removeAllItems();           
            BufferedReader br = new BufferedReader(new FileReader("lecturer.txt"));
            Object[] rows = br.lines().toArray();

            for (int i = 0; i < rows.length; i++){
                String line = rows[i].toString();
                String [] dataRow = line.split(";");
                cb.addItem(dataRow[0]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
