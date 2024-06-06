/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentation;

import MainProgram.general_home;
import project_manager.pmHomePage;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import presentation.test;
import presentation.schedule;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Owx
 */
public class Lecturer extends javax.swing.JFrame {
    
    DefaultTableModel Model;
    DefaultTableModel Report;
    DefaultTableModel Present;
    DefaultTableModel Cancel;
    
    private DefaultComboBoxModel<String> dataModel;
    private DefaultComboBoxModel<String> projectModel;
    
    private DefaultComboBoxModel<String> hourBox;
    private DefaultComboBoxModel<String> minuteBox;
    private final String logID;
    public String secID;
    public String selectedDate;
    public String selectedDateString;
    private pmHomePage pmHome;

    /**
     * Creates new form Lecturer
     */
    public Lecturer(String logID) throws IOException {
        this.logID = logID;
        initComponents();
        scheduleid.setText(logID);
        profileid.setText(logID);
        lecid.setText(logID);
        timetable1.setMonth(6);
        timetable1.setYear(2024);
        scheduleSC.setMonth(6);
        scheduleSC.setYear(2024);
        int currentMonth = timetable1.getMonth();
        int currentYear = timetable1.getYear();
        int currentMonth1 = scheduleSC.getMonth();
        int currentYear1 = scheduleSC.getYear();
        showtime();
        supervisee();
        String LEC = logID.toUpperCase();
        cancelid.setText(logID);
        welcome.setText( LEC);
        studapp.setEnabled(false);
        date.setEditable(false);
        Present = (DefaultTableModel) presentationtable.getModel();
        Model = (DefaultTableModel) Table.getModel();
        Report = (DefaultTableModel) reporttable.getModel();
        Cancel = (DefaultTableModel) canceltable.getModel();
        
        //Schedule = new DefaultTableModel();
        dataModel = new DefaultComboBoxModel<>();
        projectModel = new DefaultComboBoxModel<>();
        hourBox = new DefaultComboBoxModel<>();
        minuteBox = new DefaultComboBoxModel<>();
        projectcode.setModel(projectModel);
        intakecode.setModel(dataModel);
        hour.setModel(hourBox);
        minute.setModel(minuteBox);
        supapp.setEditable(false);
        scapp.setEditable(false);
        notification();
        IntakeInfo();
        ProjectInfo();
        time();
        

        studentid.setEditable(false);
        profileid.setEditable(false);
        functionala.setEditable(false);
        scfunctional.setEditable(false);
        profileemail.setEditable(false);
        codeproject.setEditable(false);
        studentname.setEditable(false);
        supervisorname.setEditable(false);
        scname.setEditable(false);
        projectname.setEditable(false);
        presentid.setEditable(false);
        studapp.setEditable(false);
        displayPresentInfo();
        displayReportInfo();
        displayStuInfo();
        displayScheduleInfo1();

        intakecode.addItemListener((e) -> displayStuInfo());
        projectcode.addItemListener((e) -> displayReportInfo());

        lecid.setEditable(false);
        SecondMarker();
        setProfile();
      
        profile();
       
        pmHome = new pmHomePage(logID);
    }
    
    private void profile (){
        String imagePath = "src/Profile/" + logID + ".jpg";
//        URL imageURL = getClass().getResource(imagePath);
        if (imagePath != null) {
            profilepic.setIcon(new ImageIcon(imagePath));
            lecimage.setIcon(new ImageIcon(imagePath));
        } else {
            System.err.println("Resource not found: " + imagePath);
            // Handle the error appropriately, e.g., display a default image or show an error message
        }

    }
    
    public void setProfile(){
        String lecId1 = logID.toUpperCase();
        String lecId = logID.toLowerCase();
        ArrayList<String> List = test.getLecturerInfo();
        
        for(String lines : List){
            String [] parts = lines.split(";");

            
            if(parts.length>=7 && parts[0].equalsIgnoreCase(lecId1) || parts[0].equalsIgnoreCase(lecId)){
                profileid.setText(parts[0]);
                profilename.setText(parts[1]);
                profileic.setText(parts[2]);
                profilephone.setText(parts[3]);
                profileemail.setText(parts[4]);
                functionala.setText(parts[5]);
                scfunctional.setText(parts[6]);
          }
        }
    }

  public void SecondMarker(){
      String lecId = logID.toLowerCase();
      String lecId1 = logID.toUpperCase();
      String secondmarker;
        ArrayList<String> ProjectList = test.getProjectInfo();

        for (String line : ProjectList) {
            String[] parts = line.split(";");

            if (parts.length >= 5 && (lecId.equalsIgnoreCase(parts[3]) || lecId1.equalsIgnoreCase(parts[3]))) {
                secID = parts[4];
                scid.setText(secID);
                
            }else{
                
            }
        }
    }

    public void setSelectedDateString(String selectedDateString) {
        if (selectedDateString != null) {
            String datenew = selectedDateString;

            JOptionPane.showMessageDialog(this, datenew);


        } else {
            System.err.println("No date selected");
        }
                
    }
    
    public void test(){
        time time = new time();
        time.click();
    }
    
    
    
    public void info (){
        String lecid = logID;
        String lecId = logID.toLowerCase();

        int row = presentationtable.getSelectedRow();
        String StuID = presentationtable.getValueAt(row, 1).toString();


        ArrayList<String> PresentationList = test.getPresentationInfo();
        for (String line : PresentationList) {
            String[] parts = line.split(";");
            if (parts.length >=9 && StuID.equalsIgnoreCase(parts[1])){
                String code = parts[2];

                if(parts.length >=9 && code.equals(parts[2])&& parts[6].equalsIgnoreCase(lecid) || parts[6].equalsIgnoreCase(lecId)) {
                scapp.setEnabled(false); 

                supapp.setText("Accept");
                break;
            }


            if ((parts.length >= 9) && code.equals(parts[2]) && (parts[8].equals(lecid) || parts[8].equalsIgnoreCase(lecId))) {
                supapp.setEnabled(false);

                 scapp.setText("Accept");

                 break;
            }
        }
        
      
        

    }
    }
    
    private void renew() {
        ArrayList<String> PresentationList = test.getPresentationInfo();
        String lecID = logID.toLowerCase();
        String LecID = logID.toUpperCase();
        int n = 0;

        for (String line : PresentationList) {
            String[] parts = line.split(";");

            // Check if the status is "Pending" and "Approve"
            if (parts.length >= 9 && ((lecID.equalsIgnoreCase(parts[6]) || LecID.equalsIgnoreCase(parts[6])) && parts[7].equalsIgnoreCase("Pending"))) {
                n++;
            }

            // Check if the supervisor's status is "Pending" and "Approve"
            if (parts.length >= 9 && ((lecID.equalsIgnoreCase(parts[8]) || LecID.equalsIgnoreCase(parts[8])) && parts[9].equalsIgnoreCase("Pending") )) {
                n++;
            }
        }

        notification.setText(String.valueOf(n));

    }

    
    private void notification() {
        ArrayList<String> PresentationList = test.getPresentationInfo();
        String lecID = logID.toLowerCase();
        String LecID = logID.toUpperCase();
        int n = 0;

        for (String line : PresentationList) {
            String[] parts = line.split(";");

            // Check if the status is "Pending" and "Approve"
            if (parts.length >= 9 && ((lecID.equalsIgnoreCase(parts[6]) || LecID.equalsIgnoreCase(parts[6])) && parts[7].equalsIgnoreCase("Pending"))) {
                n++;
            }

            // Check if the supervisor's status is "Pending" and "Approve"
            if (parts.length >= 9 && ((lecID.equalsIgnoreCase(parts[8]) || LecID.equalsIgnoreCase(parts[8])) && parts[9].equalsIgnoreCase("Pending"))) {
                n++;
            }
        }

        notification.setText(String.valueOf(n));

    }

    private void supervisee() {
        ArrayList<String> ProjectList = test.getProjectInfo();
        HashSet<String> uniqueIntakeCodes = new HashSet<>();
        String lecID = logID.toLowerCase();
        String LecID = logID.toUpperCase();
        int n = 0;
        String intake = "";

        for (String line : ProjectList) {
            String[] parts = line.split(";");

            if (parts.length >= 5 && (lecID.equalsIgnoreCase(parts[3]) || LecID.equalsIgnoreCase(parts[3]))) {
                intake = parts[2];
                if (uniqueIntakeCodes.add(intake)){

                ArrayList<String> StudentList = test.getStudentInfo();
                for (String student : StudentList) {
                    String[] studentdata = student.split(";");
                    if (studentdata.length >= 4 && studentdata[3].equalsIgnoreCase(intake)) {


                        n++;
                        System.out.println(studentdata[0]);
                    } 
                }
            }
            }

            if (parts.length >= 5 && lecID.equalsIgnoreCase(parts[4]) || LecID.equalsIgnoreCase(parts[4])) {
                intake = parts[2];

                 if (uniqueIntakeCodes.add(intake)){

                ArrayList<String> StudentList = test.getStudentInfo();
                for (String student : StudentList) {
                    String[] studentdata = student.split(";");
                    if (studentdata.length >= 4 && studentdata[3].equalsIgnoreCase(intake)) {


                        n++;
                        System.out.println(studentdata[0]);
                    } 
                }
            }    
        }
    }

    
    notification1.setText(String.valueOf(n));
}


    
    
    private void approve() {
        String lecid = logID;
        String lecId = logID.toLowerCase();

        int row = Table.getSelectedRow();
        //int row1 = presentationtable.getSelectedRow();
        //String StuID1=presentationtable.getValueAt(row1, 1).toString();
        String StuID = Table.getValueAt(row, 0).toString();

        ArrayList<String> PresentationList = test.getPresentationInfo();
        for (String line : PresentationList) {
            String[] parts = line.split(";");
            if (parts.length >=9 && StuID.equalsIgnoreCase(parts[1]) ){
                String code = parts[2];

                if(parts.length >=9 && code.equals(parts[2])&& parts[6].equalsIgnoreCase(lecid) || parts[6].equalsIgnoreCase(lecId)) {
                scapp.setEnabled(false); 

                supapp.setText("Accept");
                break;
                }
                if ((parts.length >= 8) && code.equals(parts[2]) && (parts[8].equals(lecid) || parts[8].equalsIgnoreCase(lecId))) {
                    supapp.setEnabled(false);
                    scapp.setText("Accept");
                    break;
                }
            }
        }
    }


    public void enable() {
        String lecid = logID;
        String lecId = logID.toLowerCase();

        // Get the selected row from Table
        int row = Table.getSelectedRow();

        if (row != -1) {
            String StuID = Table.getValueAt(row, 0).toString();

            ArrayList<String> PresentationList = test.getPresentationInfo();
            for (String line : PresentationList) {
                String[] parts = line.split(";");
                if (parts.length >= 9 && StuID.equalsIgnoreCase(parts[1])) {
                    String code = parts[2];

                    if (parts.length >= 9 && code.equals(parts[2]) && parts[6].equalsIgnoreCase(lecid)) {
                        scapp.setEnabled(false);
                        supapp.setText("Accept");
                        break;
                    }
                    if (parts.length >= 9 && code.equals(parts[2]) && parts[8].equalsIgnoreCase(lecid)) {
                        supapp.setEnabled(false);
                        scapp.setText("Accept");
                        break;
                    }
                }
            }
        } 

        // Get the selected row from presentationtable
        int row1 = presentationtable.getSelectedRow();
        if (row1 != -1) {
            String StuID1 = presentationtable.getValueAt(row1, 1).toString();

            ArrayList<String> PresentationList = test.getPresentationInfo();
            for (String line : PresentationList) {
                String[] parts = line.split(";");
                if (parts.length >= 9 && StuID1.equalsIgnoreCase(parts[1])) {
                    String code = parts[2];

                    if (parts.length >= 9 && code.equals(parts[2]) && parts[6].equalsIgnoreCase(lecid)) {
                        scapp.setEnabled(false);
                        supapp.setText("Accept");
                        break;
                    }
                    if (parts.length >= 9 && code.equals(parts[2]) && parts[8].equalsIgnoreCase(lecid)) {
                        supapp.setEnabled(false);
                        scapp.setText("Accept");
                        break;
                    }
                }
            }
        }
    }


    private void generateReport(String StuID) {
        test test = new test(); // Instantiate the Test class
        test.generateReport(StuID); // Call the generateBill method
    }
    
    private void calendar(String lecID) {
        String lecId = logID;
         
        schedule schedule = new schedule(); 
        
        schedule.setDate(lecId); 
        schedule.refreshCalendar(lecId);
    }
     
    private void calendar1(String lecID) {
        String lecId = secID;
         
        schedule1 schedule1 = new schedule1(); 
        
        scheduleSC.setDate(lecId); 
        scheduleSC.refreshCalendar(lecId);

    }
    
  

    private void collect(String StuID) {
        test test = new test(); // Instantiate the Test class
        test.collectData(StuID); 

    }

    private void IntakeInfo() {
        ArrayList<String> projectList = test.getProjectInfo();
        String lecturerID = logID;
        HashSet<String> uniqueIntakeCodes = new HashSet<>(); // Set to store unique intake codes

        for (String line : projectList) {
            String[] parts = line.split(";");

            if (parts.length >= 5 && 
               (parts[3].equalsIgnoreCase(lecturerID) || parts[4].equalsIgnoreCase(lecturerID))) {
                String intakeCode = parts[2];
                if (uniqueIntakeCodes.add(intakeCode)) { // Adds intake code to set and checks if it was added
                    dataModel.addElement(intakeCode);
                }
            }
        }
    }

    
   
    private void ProjectInfo() {
        ArrayList<String> ProjectList = test.getProjectInfo();
        String lec = logID;
        for (String lines : ProjectList) {
            String[] parts = lines.split(";");
            if (parts.length >= 5  && parts[3].equalsIgnoreCase(lec) || parts[4].equalsIgnoreCase(lec)) {
                projectModel.addElement(parts[0]);
            }
        }
    }
    
    

    private void displayStuInfo() {
        if (intakecode.getSelectedItem() == null) {
            return; // No item is selected, so do nothing
        }

        String Intake = (String) intakecode.getSelectedItem();
        Model.setRowCount(0); // Clear table before adding new data
        ArrayList<String> StudentList = test.getStudentInfo();

        for (String lines : StudentList) {
            String[] parts = lines.split(";");

            if (parts.length >= 4 && parts[3].equals(Intake)) {

                Model.addRow(parts);
            }
        }
    }
    private void Schedulerefresh(){
        Cancel.setRowCount(0);
        
        String lecID = logID.toLowerCase();
        String lecID1 = logID.toUpperCase();
        String approve = "Schedule";
        ArrayList<String> presentationList = test.getScheduleInfo();

        for (String line : presentationList) {
            String[] parts = line.split(";");

            // Ensure we have at least 10 parts for correct indexing
            if (parts.length >= 2) {
                // Check if the status is 'Approve' and either lecturer or coordinator matches lecID
                if (parts[2].equalsIgnoreCase(approve) && parts[0].equalsIgnoreCase(lecID) || parts[0].equalsIgnoreCase(lecID1)) {
                   
                    String ID = parts[0];
                    String date = parts[1];
                    String status = parts[2];
                    String[] row = {ID, date, status};
                    Cancel.addRow(row);
                }
            }
        }
    }
      
      private void newfresh(){
        if (projectcode.getSelectedItem() == null) {
            return; // No item is selected, so do nothing
        }

        String ProjectCode = (String) projectcode.getSelectedItem();

        Report.setRowCount(0); // Clear table before adding new data
        String lecID = logID.toUpperCase();

        String approve = "Granded";
        ArrayList<String> ReportList = test.getReportInfo();

        for (String lines : ReportList) {
            String[] parts = lines.split(";");
            if(parts[1].equalsIgnoreCase(ProjectCode)){
                Report.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3], parts[4]});
            }
        }
    }
              

    private void refresh(){
        Present.setRowCount(0);
        
        String lecID = logID.toLowerCase();

        String approve = "Approve";
        ArrayList<String> presentationList = test.getPresentationInfo();

        for (String line : presentationList) {
            String[] parts = line.split(";");

            // Ensure we have at least 10 parts for correct indexing
            if (parts.length >= 9) {
                // Check if the status is 'Approve' and either lecturer or coordinator matches lecID
                if ((parts[6].equalsIgnoreCase(lecID) || parts[6].equals(lecid) || parts[8].equals(lecid) || parts[8].equalsIgnoreCase(lecID))) {

                    String presentID = parts[0];
                    String stuID = parts[1];
                    String projectCode = parts[2];
                    String presentDate = parts[3];
                    String time = parts[4];
                    String acceptance = "";
                    
                    String id = "";

                    if (parts[6].equalsIgnoreCase(lecID) || parts[6].equals(lecid)) {
                        acceptance = parts[7];
                        id = parts[6];
                    }

                    if (parts[8].equalsIgnoreCase(lecID) || parts[8].equals(lecid)) {
                        acceptance = parts[9];
                        id = parts[8];
                    }

                    String[] row = {presentID, stuID, projectCode, presentDate,time, id, acceptance};
                    Present.addRow(row);
                }
            }
        }
    }

    private void displayPresentInfo() {
        String lecID = logID.toLowerCase();
        
        ArrayList<String> presentationList = test.getPresentationInfo();

        for (String line : presentationList) {
            String[] parts = line.split(";");

            // Ensure we have at least 10 parts for correct indexing
            if (parts.length >= 9) {
                // Check if the status is 'Approve' and either lecturer or coordinator matches lecID
                if ( (parts[6].equalsIgnoreCase(lecID) || parts[6].equals(lecid) || parts[8].equals(lecid) || parts[8].equalsIgnoreCase(lecID))) {

                    String presentID = parts[0];
                    String stuID = parts[1];
                    String projectCode = parts[2];
                    String presentDate = parts[3];
                    String time = parts[4];
                    String acceptance = "";
                    
                    String id = "";

                    if (parts[6].equalsIgnoreCase(lecID) || parts[6].equals(lecid)) {
                        acceptance = parts[7];
                        id = parts[6];
                    }

                    if (parts[8].equalsIgnoreCase(lecID) || parts[8].equals(lecid)) {
                        acceptance = parts[9];
                        id = parts[8];
                    }

                    String[] row = {presentID, stuID, projectCode, presentDate,time, id, acceptance};
                    Present.addRow(row);
                }
            }
        }
    }
    


    private void displayScheduleInfo1() {
        String lecID = logID.toLowerCase();
        String lecID1 = logID.toUpperCase();
        String approve = "Schedule";
        ArrayList<String> presentationList = test.getScheduleInfo();

        for (String line : presentationList) {
            String[] parts = line.split(";");

            // Ensure we have at least 10 parts for correct indexing
            if (parts.length >= 2) {
                // Check if the status is 'Approve' and either lecturer or coordinator matches lecID
                if (parts[2].equalsIgnoreCase(approve) && parts[0].equalsIgnoreCase(lecID) || parts[0].equalsIgnoreCase(lecID1)) {
                    

                    String ID = parts[0];
                    String date = parts[1];
                    String status = parts[2];

                    String[] row = {ID, date, status};
                    Cancel.addRow(row);
                }
            }
        }
    }
    
    
    private void openReport(String StuID) {
        test test = new test(); // Instantiate the Test class
        test.openReport(StuID); // Ca
    }
    
     
    public static void openFileByName(String directoryPath, String fileName) {
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            JOptionPane.showMessageDialog(null, "Directory not found: " + directoryPath, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a filter to match files by name (ignoring extension)
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String baseName = name.substring(0, name.lastIndexOf('.'));
                return baseName.equalsIgnoreCase(fileName);
            }
        };

        // Search for the file
        File[] matchingFiles = dir.listFiles(filter);
        if (matchingFiles != null && matchingFiles.length > 0) {
            File fileToOpen = matchingFiles[0];
            openFile(fileToOpen);
        } else {
            JOptionPane.showMessageDialog(null, "File not found: " + fileName, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void openFile(File file) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to open file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Opening files is not supported on this platform.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancelDate() {
        int row = canceltable.getSelectedRow();
        if (row != -1) {

            String date = canceltable.getValueAt(row, 1).toString();
            String lecid = logID.toLowerCase();
            String lecid1 = logID.toUpperCase();

            // Read the current presentation information from the file
            ArrayList<String> presentationList = test.getScheduleInfo();

            // Update the presentation list with the canceled presentation
            for (int i = 0; i < presentationList.size(); i++) {
                String line = presentationList.get(i);
                String[] parts = line.split(";");

                // Check if SCID, date, and lecturer ID match and update the status to "Cancel"
                if ( parts[1].equalsIgnoreCase(date) && 
                        (parts[0].equalsIgnoreCase(lecid) || parts[0].equalsIgnoreCase(lecid1))) {
                    parts[2] = "Cancel";
                    // Update the line in the presentation list
                    presentationList.set(i, String.join(";", parts));
                }
            }
            System.out.println(presentationList);
            // Define the file path
            String file = "schedule.txt";

            // Write the updated presentation information back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String updatedLine : presentationList) {
                    writer.write(updatedLine);
                    writer.newLine(); // Add newline after each line
                }
            } catch (IOException ex) {
                Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

        
        
     
      
    


    public void showNewDate1() {
        // Retrieve the selected date from the JDateChooser
        Date selectedDate = newdate.getDate();
        String lecid = "";
        String sec = "";

        // If no date is selected, set the JDateChooser to the current date
        if (selectedDate == null) {
            newdate.setDate(new Date());
            selectedDate = newdate.getDate();
        }
        String project = codeproject.getText();
        String name = projectname.getText();
        ArrayList<String> projectList = test.getProjectInfo();

        // Find lecturer ID and section from project info
        for (String one : projectList) {
            String[] code = one.split(";");
            if (code.length >= 5 && code[0].equalsIgnoreCase(project) && code[1].equalsIgnoreCase(name)) {
                lecid = code[3];
                sec = code[4];
                break; // Exit loop once project is found
            }
        }

        // Format the selected date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(selectedDate);

        // Set the formatted date to the text field
        String datenew = formattedDate;
        String Hour = (String) hour.getSelectedItem();
        String Minute = (String) minute.getSelectedItem();
        String time = Hour + ":" + Minute;
        boolean leccheck = true;
        boolean sccheck = true;

        String LecID = logID.toLowerCase();
        String LecID1 = logID.toUpperCase();
        String lecturer = lecid;
        String scID = sec;

        if (lecturer.equalsIgnoreCase(LecID) || lecturer.equalsIgnoreCase(LecID1)) {
            ArrayList<String> scheduleList = test.getScheduleInfo();
            for (String liness : scheduleList) {
                String schedule[] = liness.split(";");

                if (schedule[0].equalsIgnoreCase(LecID) || schedule[0].equalsIgnoreCase(LecID1)) {
                    String date = schedule[1];
                    if (formattedDate.equalsIgnoreCase(date)) {
                        JOptionPane.showMessageDialog(this, LecID1 + " , you have a scheduled that day. Please select another date.");
                        leccheck = false;
                        break;

                    }

                } else if (schedule[0].equalsIgnoreCase(scID) || schedule[0].equalsIgnoreCase(scID)) {
                    String date = schedule[1];
                    if (formattedDate.equalsIgnoreCase(date)) {
                        JOptionPane.showMessageDialog(this, scID + " , another marker of this project has a scheduled that day. Please select another date.");
                        leccheck = false;
                        break;
                    }
                }
            }
        }

        if (leccheck) {
            String StuID = studentid.getText();
            ArrayList<String> NewPresentationList = test.getPresentationInfo();
            for (int i = 0; i < NewPresentationList.size(); i++) {
                String line = NewPresentationList.get(i);
                String[] parts = line.split(";");
                if (parts.length >= 9 && StuID.equalsIgnoreCase(parts[1])) {
                    if (parts[6].equals(LecID) || parts[6].equalsIgnoreCase(LecID1)) {
                        parts[3] = datenew;
                        parts[4] = time;
                        parts[5] = "Pending";
                        parts[7] = "Accept";
                        parts[9] = "Pending";
                    }
                    // Join the parts back into a single line
                    String updatedLine = String.join(";", parts);
                    // Replace the original line with the updated line
                    NewPresentationList.set(i, updatedLine);
                    JOptionPane.showMessageDialog(this, StuID + " presentation date changed successfully.");
                    break;
                }
            }
            
            

            // Define the file path
            String file = "presentation.txt";

            // Write the updated presentation information back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String updatedLine : NewPresentationList) {
                    writer.write(updatedLine);
                    writer.newLine(); // Add newline after each line
                }
            } catch (IOException ex) {
                Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
            }
}

            if (scID.equalsIgnoreCase(LecID) || scID.equalsIgnoreCase(LecID1)) {
                ArrayList<String> scheduleList = test.getScheduleInfo();
                for (String liness : scheduleList) {
                    String schedule[] = liness.split(";");

                    if (schedule[0].equalsIgnoreCase(LecID) || schedule[0].equalsIgnoreCase(LecID1)) {
                        String date = schedule[1];
                        if (formattedDate.equalsIgnoreCase(date)) {
                            JOptionPane.showMessageDialog(this, LecID1 + " , you have a scheduled that day. Please select another date.");
                            sccheck = false;
                            break;

                        }

                    } else if (schedule[0].equalsIgnoreCase(lecturer) || schedule[0].equalsIgnoreCase(lecturer)) {
                        String date = schedule[1];
                        if (formattedDate.equalsIgnoreCase(date)) {
                            JOptionPane.showMessageDialog(this, lecturer + " , another marker of this project has a scheduled that day. Please select another date.");
                            sccheck = false;
                            
                            break;
                        }
                    }
                }
            }

            if (sccheck) {
                String StuID1 = studentid.getText();
                ArrayList<String> NewList = test.getPresentationInfo();
                for (int i = 0; i < NewList.size(); i++) {
                    String line = NewList.get(i);
                    String[] parts = line.split(";");
                    if (parts.length >= 9 && StuID1.equalsIgnoreCase(parts[1])) {
                        if (parts[8].equals(LecID) || parts[8].equalsIgnoreCase(LecID1)) {
                            parts[3] = datenew;
                            parts[4] = time;
                            parts[5] = "Pending";
                            parts[7] = "Pending";
                            parts[9] = "Accept";
                        }
                        // Join the parts back into a single line
                        String updatedLine1 = String.join(";", parts);
                        // Replace the original line with the updated line
                        NewList.set(i, updatedLine1);
                        JOptionPane.showMessageDialog(this, StuID1 + " presentation date changed successfully.");
                        break;
                    }
                }
                

                // Define the file path
                String file1 = "presentation.txt";

                // Write the updated presentation information back to the file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file1))) {
                    for (String updatedLine1 : NewList) {
                        writer.write(updatedLine1);
                        writer.newLine(); // Add newline after each line
                    }
                } catch (IOException ex) {
                    Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
    }

    public void showNewDatetry() {
        // Retrieve the selected date from the JDateChooser
        Date selectedDate = newdate.getDate();
        String lecid = "";
        String sec = "";
        String Student = studentid.getText();

        // If no date is selected, set the JDateChooser to the current date
        if (selectedDate == null) {
            newdate.setDate(new Date());
            selectedDate = newdate.getDate();
        }
        String project = codeproject.getText();
        String name = projectname.getText();
        ArrayList<String> projectList = test.getProjectInfo();

        // Find lecturer ID and section from project info
        for (String one : projectList) {
            String[] code = one.split(";");
            if (code.length >= 5 && code[0].equalsIgnoreCase(project) && code[1].equalsIgnoreCase(name)) {
                lecid = code[3];
                sec = code[4];
                break; // Exit loop once project is found
            }
        }

        // Format the selected date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(selectedDate);

        // Set the formatted date to the text field
        String datenew = formattedDate;
        String Hour = (String) hour.getSelectedItem();
        String Minute = (String) minute.getSelectedItem();
        String time = Hour + ":" + Minute;
        boolean leccheck = true;
        boolean sccheck = true;

        String LecID = logID.toLowerCase();
        String LecID1 = logID.toUpperCase();
        String lecturer = lecid;
        String scID = sec;

        // Flags to track if a message should be displayed
        boolean showMessage = true;
        boolean dateChanged = false;

        if (lecturer.equalsIgnoreCase(LecID) || lecturer.equalsIgnoreCase(LecID1)) {
            ArrayList<String> scheduleList = test.getScheduleInfo();
            for (String liness : scheduleList) {
                String schedule[] = liness.split(";");

                if (schedule[0].equalsIgnoreCase(LecID) || schedule[0].equalsIgnoreCase(LecID1)) {
                    String date = schedule[1];
                    if (formattedDate.equalsIgnoreCase(date)) {
                        JOptionPane.showMessageDialog(this, LecID1 + " , you have a scheduled that day. Please select another date.");
                        leccheck = false;
                        sccheck = false;
                        showMessage = false;
                        break;
                    }

                } else if (schedule[0].equalsIgnoreCase(scID) || schedule[0].equalsIgnoreCase(scID)) {
                    String date = schedule[1];
                    if (formattedDate.equalsIgnoreCase(date)) {
                        JOptionPane.showMessageDialog(this, scID + " , another marker of this project has a scheduled that day. Please select another date.");
                        leccheck = false;
                        sccheck = false;
                        showMessage = false;
                        break;
                    }
                }
            }
        }

        if (leccheck) {
            ArrayList<String> NewPresentationList = test.getPresentationInfo();
            for (int i = 0; i < NewPresentationList.size(); i++) {
                String line = NewPresentationList.get(i);
                String[] parts = line.split(";");
                if (parts.length >= 9 && Student.equalsIgnoreCase(parts[1])) {
                    if (parts[6].equals(LecID) || parts[6].equalsIgnoreCase(LecID1)) {
                        parts[3] = datenew;
                        parts[4] = time;
                        parts[5] = "Pending";
                        parts[7] = "Accept";
                        parts[9] = "Pending";
                        dateChanged = true;
                        sccheck = false;
                    }
                    // Join the parts back into a single line
                    String updatedLine = String.join(";", parts);
                    // Replace the original line with the updated line
                    NewPresentationList.set(i, updatedLine);
                }
            }
            // Define the file path
            String file = "presentation.txt";

            // Write the updated presentation information back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String updatedLine : NewPresentationList) {
                    writer.write(updatedLine);
                    writer.newLine(); // Add newline after each line
                }
            } catch (IOException ex) {
                Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (scID.equalsIgnoreCase(LecID) || scID.equalsIgnoreCase(LecID1)) {
            ArrayList<String> scheduleList = test.getScheduleInfo();
            for (String liness : scheduleList) {
                String schedule[] = liness.split(";");

            if (schedule[0].equalsIgnoreCase(LecID) || schedule[0].equalsIgnoreCase(LecID1)) {
                String date = schedule[1];
                if (formattedDate.equalsIgnoreCase(date)) {
                    JOptionPane.showMessageDialog(this, LecID1 + " , you have a scheduled that day. Please select another date.");
                    leccheck = false;
                    sccheck = false;
                    showMessage = false;
                    break;
                }

            } else if (schedule[0].equalsIgnoreCase(lecturer) || schedule[0].equalsIgnoreCase(lecturer)) {
                String date = schedule[1];
                if (formattedDate.equalsIgnoreCase(date)) {
                    JOptionPane.showMessageDialog(this, lecturer + " , another marker of this project has a scheduled that day. Please select another date.");
                    leccheck = false;
                    sccheck = false;
                    showMessage = false;
                    break;
                }
            }
        }
    }

    if (sccheck) {
        ArrayList<String> NewList = test.getPresentationInfo();
        for (int i = 0; i < NewList.size(); i++) {
            String line = NewList.get(i);
            String[] parts = line.split(";");
            if (parts.length >= 9 && Student.equalsIgnoreCase(parts[1])) {
                if (parts[8].equals(LecID) || parts[8].equalsIgnoreCase(LecID1)) {
                    parts[3] = datenew;
                    parts[4] = time;
                    parts[5] = "Pending";
                    parts[7] = "Pending";
                    parts[9] = "Accept";
                    dateChanged = true;
                    leccheck=false;
                }
                // Join the parts back into a single line
                String updatedLine1 = String.join(";", parts);
                // Replace the original line with the updated line
                NewList.set(i, updatedLine1);
            }
        }
        // Define the file path
        String file1 = "presentation.txt";

        // Write the updated presentation information back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file1))) {
            for (String updatedLine1 : NewList) {
                writer.write(updatedLine1);
                writer.newLine(); // Add newline after each line
            }
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    // Show success message only if the date was changed and no conflicts were found
    if (showMessage && dateChanged) {
        JOptionPane.showMessageDialog(this, Student + " presentation date changed successfully.");
    }
}

    public void fresh(){
        studentid.setText("");
    }


    public ArrayList<String> showNewState() {
        String sunewState = supapp.getText();
        String scnewState = scapp.getText();
        String StuID = studentid.getText();
        ArrayList<String> NewPresentationList = test.getPresentationInfo();
        for (int i = 0; i < NewPresentationList.size(); i++) {
            String line = NewPresentationList.get(i);
            String[] parts = line.split(";");
            if (parts.length >= 9 && StuID.equalsIgnoreCase(parts[1])) {
                parts[7] = sunewState;
                parts[9] = scnewState;
                // Join the parts back into a single line
                String updatedLine = String.join(";", parts);
                // Replace the original line with the updated line
                NewPresentationList.set(i, updatedLine);

                // No need to continue looping, as we have already found and updated the line
            }
        }
        String file = "presentation.txt";
        // Write the updated presentation information back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : NewPresentationList) {
                writer.write(line);
                writer.newLine(); // Add newline after each line
            }
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }

        return NewPresentationList;
    }
    
    public void reloadass(){
        String ass = id.getText();
        ArrayList<String> ReportList = test.getReportInfo();

        for (String lines : ReportList) {
            String[] parts = lines.split(";");

            if (parts.length >= 8 && parts[0].equals(ass)) {
                id.setText(parts[0]);
                code.setText(parts[1]);
                stuidtext.setText(parts[2]);
                status.setText(parts[3]);
                grade.setText(parts[4]);
                assname.setText(parts[5]);
                //assfile.setText(parts[6]);
                lfeedback.setText(parts[7]);
                scfeedback.setText(parts[8]);
            }
        }
    }
    

    public void editAss() {
        String lecid = logID.toUpperCase();
        String fb = newfb.getText();
        String grade = (String) newgrade.getSelectedItem();
        String ID = id.getText();
        String pc = code.getText();
        String student = stuidtext.getText();

        ArrayList<String> ProjectList = test.getProjectInfo();
        for (String lines : ProjectList) {
            String[] parts = lines.split(";");
            if (parts.length >= 5 && parts[0].equalsIgnoreCase(pc)) {
                String lec = parts[3];
                String sc = parts[4];

                ArrayList<String> updatedReportList = new ArrayList<>(); // Create a new list to hold updated lines

                ArrayList<String> ReportList = test.getReportInfo();
                for (String line : ReportList) {
                    String[] part = line.split(";");
                    if (part.length >= 8 && lecid.equalsIgnoreCase(lec) && part[2].equalsIgnoreCase(student) && part[0].equalsIgnoreCase(ID)) {
                        part[3]="Graded";
                        part[7] = fb;
                        part[4] = grade;
                    } else if (part.length >= 8 && lecid.equalsIgnoreCase(sc) && part[2].equalsIgnoreCase(student) && part[0].equalsIgnoreCase(ID)) {
                        part[3]="Graded";
                        part[8] = fb;
                        part[4] = grade;
                    }
                    String updatedLine = String.join(";", part);
                    updatedReportList.add(updatedLine); // Add the updated line to the new list
                }JOptionPane.showMessageDialog(this,  "Modification successfully.");

                String file = "submission.txt";
                // Write the updated presentation information back to the file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (String line : updatedReportList) {
                        writer.write(line);
                        writer.newLine(); // Add newline after each line
                    }
                } catch (IOException ex) {
                    Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    public ArrayList<String> addSchedule() {
        // Retrieve the selected date from the JDateChooser
        Date selectedDate = newdate2.getDate();

        // If no date is selected, set the JDateChooser to the current date
        if (selectedDate == null) {
            newdate2.setDate(new Date());
            selectedDate = newdate2.getDate();
        }

        String LecID = logID.toLowerCase();
        String LecID1 = logID.toUpperCase();

        // Format the selected date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(selectedDate);

        // Create a new list to store the updated schedule entries
        ArrayList<String> updatedList = new ArrayList<>();

        // Retrieve the existing schedule information
        ArrayList<String> currentList = test.getScheduleInfo();

        // Iterate over the existing schedule entries and add them to the updated list
        for (String line : currentList) {
            updatedList.add(line);
        }

        // Create a new schedule entry
        String status = "Schedule";
        String newschedule = LecID1 + ";" + formattedDate + ";" + status;

        // Add the new schedule entry to the updated list
        updatedList.add(newschedule);
        // Define the file path
        String file = "schedule.txt";

        // Write the updated presentation information back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String updatedLine : updatedList) {
                writer.write(updatedLine);
                writer.newLine(); // Add newline after each line
            }
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updatedList;

    }

       
    private void time() {
        // Populate hourBox with numbers from 1 to 24
        // Populate hourBox with numbers from 00 to 24
        for (int i = 9; i <= 17; i++) {
            hourBox.addElement(String.format("%02d", i));
        }

        // Populate minuteBox with numbers from 00 to 59
        for (int i = 0; i < 60; i++) {
            minuteBox.addElement(String.format("%02d", i));
        }
    }

    private void clean(){

        String StuID = studentid.getText();
        ArrayList<String> PresentationList = test.getPresentationInfo();
        for (String lines : PresentationList) {
            String[] parts = lines.split(";");

            if (parts.length >= 9 && parts[1].equals(StuID)) {
                codeproject.setText(parts[2]);
                presentid.setText(parts[0]);
                studapp.setText(parts[5]);
                supapp.setText(parts[7]);
                scapp.setText(parts[9]);
                date.setText(parts[3]+" "+ parts[4]);

                String lec=parts[6];
                String sc=parts[8];
                String pcode=parts[2];

                ArrayList<String> StudentList = test.getStudentInfo();
                for (String line : StudentList) {
                    String[] student = line.split(";");
                    if(student.length >= 4 && StuID.equalsIgnoreCase(student[0])){
                        studentname.setText(student[1]);
                        
                        ArrayList<String> LecturerList = test.getLecturerInfo();

                        for (String lecturer : LecturerList) {
                            String[] lecture = lecturer.split(";");

                            if(lecture.length>=1 && lec.equalsIgnoreCase(lecture[0])){
                                supervisorname.setText(lecture[1]);
                            }

                            if (lecture.length >= 1 && sc.equalsIgnoreCase(lecture[0])) {
                                scname.setText(lecture[1]);

                                ArrayList<String> ProjectList = test.getProjectInfo();
                                for (String project : ProjectList) {
                                    String[] projectdata = project.split(";");

                                    if(projectdata.length >=1 && pcode.equals(projectdata[0])){
                                        projectname.setText(projectdata[1]);

                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }                             
    }
  
  private static void setLecturerWelcome(String logID) {
       String lowerCaseID = logID.toLowerCase();
        String upperCaseID = logID.toUpperCase();
        
        ArrayList<String> lecturerList = test.getLecturerInfo();

        for (String line : lecturerList) {
            String[] parts = line.split(";");
            if (parts.length > 1 && (parts[0].equals(lowerCaseID) || parts[0].equals(upperCaseID))) {
                String name = parts[1];
                String welcome = "Welcome, " + name;             

                break; // Exit the loop once the lecturer is found
            }
        }
  }

    private void displayReportInfo() {
        if (projectcode.getSelectedItem() == null) {
            return; // No item is selected, so do nothing
        }

        String ProjectCode = (String) projectcode.getSelectedItem();
        Report.setRowCount(0); // Clear table before adding new data
        ArrayList<String> ReportList = test.getReportInfo();

        for (String lines : ReportList) {
            String[] parts = lines.split(";");

            if (parts.length >= 8 && parts[1].equals(ProjectCode)) {

                Report.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3], parts[4]});
            }
        }
    }
    
    private void showtime(){
        String monthName;
        int currentMonth = timetable1.getMonth();
         int currentMonth1 = scheduleSC.getMonth();
        switch (currentMonth ) {
        case 1:
            monthName = "January";
            break;
        case 2:
            monthName = "February";
            break;
        case 3:
            monthName = "March";
            break;
        case 4:
            monthName = "April";
            break;
        case 5:
            monthName = "May";
            break;
        case 6:
            monthName = "June";
            break;
        case 7:
            monthName = "July";
            break;
        case 8:
            monthName = "August";
            break;
        case 9:
            monthName = "September";
            break;
        case 10:
            monthName = "October";
            break;
        case 11:
            monthName = "November";
            break;
        case 12:
            monthName = "December";
            break;
        default:
            monthName = "Invalid month";
            break;
    }
        int currentYear = timetable1.getYear();

        month.setText(monthName);
        year.setText(String.valueOf(currentYear));
        month1.setText(monthName);
        year1.setText(String.valueOf(currentYear));
    }
    
     private void showtime1(){
        String monthName;
        
         int currentMonth1 = scheduleSC.getMonth();
        switch (currentMonth1 ) {
        case 1:
            monthName = "January";
            break;
        case 2:
            monthName = "February";
            break;
        case 3:
            monthName = "March";
            break;
        case 4:
            monthName = "April";
            break;
        case 5:
            monthName = "May";
            break;
        case 6:
            monthName = "June";
            break;
        case 7:
            monthName = "July";
            break;
        case 8:
            monthName = "August";
            break;
        case 9:
            monthName = "September";
            break;
        case 10:
            monthName = "October";
            break;
        case 11:
            monthName = "November";
            break;
        case 12:
            monthName = "December";
            break;
        default:
            monthName = "Invalid month";
            break;
    }
        int currentYear = scheduleSC.getYear();

        month1.setText(monthName);
        year1.setText(String.valueOf(currentYear));
    }
        private void showNextMonth1() {
         String lecid = secID;
       
        int currentMonth1 = scheduleSC.getMonth();
        int currentYear1 = scheduleSC.getYear();

       
        if (currentMonth1 == 12) { // If it's December, move to January of the next year
            scheduleSC.setMonth(1);
            scheduleSC.setYear(currentYear1 + 1);
        } else { // Otherwise, just move to the next month
            scheduleSC.setMonth(currentMonth1 + 1);
        }

        scheduleSC.refreshCalendar(lecid);
        showtime1();
    }
    
    
    private void showNextMonth() {
        String lecid = logID;
        int currentMonth = timetable1.getMonth();
        int currentYear = timetable1.getYear();
        int currentMonth1 = scheduleSC.getMonth();
        int currentYear1 = scheduleSC.getYear();

        if (currentMonth == 12) { // If it's December, move to January of the next year
            timetable1.setMonth(1);
            timetable1.setYear(currentYear + 1);
        } else { // Otherwise, just move to the next month
            timetable1.setMonth(currentMonth + 1);
        }
        
        if (currentMonth1 == 12) { // If it's December, move to January of the next year
            scheduleSC.setMonth(1);
            scheduleSC.setYear(currentYear + 1);
        } else { // Otherwise, just move to the next month
            scheduleSC.setMonth(currentMonth + 1);
        }

        timetable1.refreshCalendar(lecid);// Refresh the calendar to show the next month
        scheduleSC.refreshCalendar(lecid);
        showtime();
    }
    
    private void showPreviousMonth1() {
        String stuid = secID;
       
        int currentMonth1 = scheduleSC.getMonth();
        int currentYear1 = scheduleSC.getYear();

        if (currentMonth1 == 1) { // If it's January, move to December of the previous year
            scheduleSC.setMonth(12);
            scheduleSC.setYear(currentYear1 - 1);
        } else { // Otherwise, just move to the previous month
            scheduleSC.setMonth(currentMonth1 - 1);
        }
     
        scheduleSC.refreshCalendar(stuid);
        showtime1();
    }

    private void showPreviousMonth() {
        String stuid = logID;
        int currentMonth = timetable1.getMonth();

        int currentYear = timetable1.getYear();

        if (currentMonth == 1) { // If it's January, move to December of the previous year
            timetable1.setMonth(12);
            timetable1.setYear(currentYear - 1);
        } else { // Otherwise, just move to the previous month
            timetable1.setMonth(currentMonth - 1);
        }

        timetable1.refreshCalendar(stuid);
        timetable1.refreshCalendar(stuid);
        showtime();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        profilepic = new presentation.ImageAvatar();
        dashpanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        dashboardbar = new javax.swing.JLabel();
        studentbar = new javax.swing.JLabel();
        presentationbar = new javax.swing.JLabel();
        reportbar = new javax.swing.JLabel();
        timetable = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        dashboard = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        notification = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        notification1 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        welcome = new javax.swing.JLabel();
        student = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        intakecode = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        presentation = new javax.swing.JPanel();
        lecid = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        presentationtable = new javax.swing.JTable();
        jLabel37 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        report = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        reporttable = new javax.swing.JTable();
        projectcode = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        assesment = new javax.swing.JPanel();
        code = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        stuidtext = new javax.swing.JTextField();
        id = new javax.swing.JTextField();
        grade = new javax.swing.JTextField();
        assname = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        status = new javax.swing.JTextField();
        lfeedback = new javax.swing.JTextField();
        scfeedback = new javax.swing.JTextField();
        assfile = new javax.swing.JButton();
        jLabel68 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        timedate = new javax.swing.JPanel();
        orgdate = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        newdate = new com.toedter.calendar.JDateChooser();
        hour = new javax.swing.JComboBox<>();
        minute = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        feedback = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        newfb = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        newgrade = new javax.swing.JComboBox<>();
        jLabel66 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        studentpre = new javax.swing.JPanel();
        studentid = new javax.swing.JTextField();
        presentid = new javax.swing.JTextField();
        codeproject = new javax.swing.JTextField();
        date = new javax.swing.JTextField();
        projectname = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        edit = new javax.swing.JButton();
        approve = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        scname = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        scapp = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        supervisorname = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        supapp = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        studentname = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        studapp = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        cancelsc = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        canceltable = new javax.swing.JTable();
        cancelid = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        scday = new javax.swing.JPanel();
        scheduleSC = new presentation.schedule1();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        month1 = new javax.swing.JLabel();
        year1 = new javax.swing.JLabel();
        scid = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        day = new javax.swing.JPanel();
        timetable1 = new presentation.schedule();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        year = new javax.swing.JLabel();
        month = new javax.swing.JLabel();
        scheduleid = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        newdate2 = new com.toedter.calendar.JDateChooser();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        profile = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        profilename = new javax.swing.JTextField();
        profileic = new javax.swing.JTextField();
        profilephone = new javax.swing.JTextField();
        functionala = new javax.swing.JTextField();
        profileid = new javax.swing.JTextField();
        functional = new javax.swing.JLabel();
        profileemail = new javax.swing.JTextField();
        scfunctional = new javax.swing.JTextField();
        lecimage = new presentation.ImageAvatar();
        jPanel19 = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(209, 211, 199));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecOut.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        profilepic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profilepicMouseClicked(evt);
            }
        });

        dashpanel.setBackground(new java.awt.Color(209, 211, 199));
        dashpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashpanelMouseClicked(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecLogo.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Bell MT", 1, 24)); // NOI18N
        jLabel1.setText("GOODBRAIN");

        javax.swing.GroupLayout dashpanelLayout = new javax.swing.GroupLayout(dashpanel);
        dashpanel.setLayout(dashpanelLayout);
        dashpanelLayout.setHorizontalGroup(
            dashpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashpanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dashpanelLayout.setVerticalGroup(
            dashpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashpanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(35, 35, 35))
            .addGroup(dashpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(dashpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 315, Short.MAX_VALUE)
                .addComponent(profilepic, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(dashpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(profilepic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 100));

        jPanel2.setBackground(new java.awt.Color(172, 190, 174));

        dashboardbar.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        dashboardbar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dashboardbar.setText("Dashboard");
        dashboardbar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardbarMouseClicked(evt);
            }
        });

        studentbar.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        studentbar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        studentbar.setText("Student");
        studentbar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentbarMouseClicked(evt);
            }
        });

        presentationbar.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        presentationbar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        presentationbar.setText("Presentation");
        presentationbar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                presentationbarMouseClicked(evt);
            }
        });

        reportbar.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        reportbar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reportbar.setText("Assesment report");
        reportbar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportbarMouseClicked(evt);
            }
        });

        timetable.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        timetable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timetable.setText("Timetable");
        timetable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                timetableMouseClicked(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/pmChangeToLec.png"))); // NOI18N
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(dashboardbar, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(studentbar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(presentationbar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(reportbar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(timetable, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dashboardbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(studentbar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(presentationbar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reportbar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timetable, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 900, 50));

        jTabbedPane1.setBackground(new java.awt.Color(239, 240, 234));

        dashboard.setBackground(new java.awt.Color(239, 240, 234));

        jLabel41.setFont(new java.awt.Font("Stencil", 1, 18)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Quick Access");

        jPanel8.setBackground(new java.awt.Color(226, 228, 216));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel44.setText("Presentation Request ");

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecNotification.png"))); // NOI18N

        notification.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(notification, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel44)
                .addGap(32, 32, 32))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(notification, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(226, 228, 216));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel46.setText("Supervisee List");

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecStudent.png"))); // NOI18N

        notification1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel46)
                .addGap(18, 18, 18)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(notification1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel46))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(notification1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        jLabel90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecBackground.png"))); // NOI18N

        jLabel91.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecWelcome.png"))); // NOI18N

        welcome.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        welcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout dashboardLayout = new javax.swing.GroupLayout(dashboard);
        dashboard.setLayout(dashboardLayout);
        dashboardLayout.setHorizontalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardLayout.createSequentialGroup()
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dashboardLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dashboardLayout.createSequentialGroup()
                        .addComponent(jLabel90)
                        .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dashboardLayout.createSequentialGroup()
                                .addGap(346, 346, 346)
                                .addComponent(jLabel39))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel91)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(welcome, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)))))
                .addGap(192, 192, 192))
            .addGroup(dashboardLayout.createSequentialGroup()
                .addGap(410, 410, 410)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dashboardLayout.setVerticalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dashboardLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(welcome, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addGap(42, 42, 42)
                .addComponent(jLabel41)
                .addGap(40, 40, 40)
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70))
        );

        jTabbedPane1.addTab("tab1", dashboard);

        student.setBackground(new java.awt.Color(239, 240, 234));

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Student ID", "Student Name", "Student Email", "Intake"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table);
        if (Table.getColumnModel().getColumnCount() > 0) {
            Table.getColumnModel().getColumn(0).setResizable(false);
            Table.getColumnModel().getColumn(0).setPreferredWidth(50);
            Table.getColumnModel().getColumn(2).setResizable(false);
            Table.getColumnModel().getColumn(2).setPreferredWidth(150);
        }

        intakecode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Intake Code :");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel38.setText("Student List");

        jPanel10.setBackground(new java.awt.Color(209, 211, 199));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel61.setText("Assesment Report");

        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecAss.png"))); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel61)
                .addGap(28, 28, 28))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(209, 211, 199));
        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
        });

        jLabel69.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel69.setText("Presentation");

        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecRequest.png"))); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel70)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel69)
                .addGap(28, 28, 28))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout studentLayout = new javax.swing.GroupLayout(student);
        student.setLayout(studentLayout);
        studentLayout.setHorizontalGroup(
            studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentLayout.createSequentialGroup()
                .addGap(380, 380, 380)
                .addComponent(jLabel38)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(studentLayout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(145, 145, 145))
            .addGroup(studentLayout.createSequentialGroup()
                .addGap(520, 520, 520)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(intakecode, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(138, Short.MAX_VALUE))
        );
        studentLayout.setVerticalGroup(
            studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intakecode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66))
        );

        jTabbedPane1.addTab("tab2", student);

        presentation.setBackground(new java.awt.Color(239, 240, 234));

        lecid.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setText("Lecturer ID : ");

        presentationtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Presentation ID", "Student ID", "Project Code", "Date", "Time", "Your ID", "Your Acceptance"
            }
        ));
        jScrollPane3.setViewportView(presentationtable);
        if (presentationtable.getColumnModel().getColumnCount() > 0) {
            presentationtable.getColumnModel().getColumn(2).setPreferredWidth(100);
            presentationtable.getColumnModel().getColumn(3).setPreferredWidth(100);
            presentationtable.getColumnModel().getColumn(6).setPreferredWidth(70);
        }

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel37.setText("Prersentation Request List");

        jPanel12.setBackground(new java.awt.Color(209, 211, 199));
        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });

        jLabel71.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel71.setText("Check Info");

        jLabel72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecCheck.png"))); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel72)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel71)
                .addGap(28, 28, 28))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout presentationLayout = new javax.swing.GroupLayout(presentation);
        presentation.setLayout(presentationLayout);
        presentationLayout.setHorizontalGroup(
            presentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(presentationLayout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addGroup(presentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, presentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(presentationLayout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lecid, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, presentationLayout.createSequentialGroup()
                            .addComponent(jLabel37)
                            .addGap(269, 269, 269)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, presentationLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, presentationLayout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110))))
        );
        presentationLayout.setVerticalGroup(
            presentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(presentationLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(presentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lecid, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        jTabbedPane1.addTab("tab3", presentation);

        report.setBackground(new java.awt.Color(239, 240, 234));

        reporttable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Assesment ID", "Project Code", "Student ID", "Status", "Grade"
            }
        ));
        jScrollPane2.setViewportView(reporttable);

        projectcode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Project Code : ");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel36.setText("Student's Assesment List");

        jPanel13.setBackground(new java.awt.Color(209, 211, 199));
        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
        });

        jLabel73.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel73.setText("Check Info");

        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecCheck.png"))); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel74)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel73)
                .addGap(28, 28, 28))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout reportLayout = new javax.swing.GroupLayout(report);
        report.setLayout(reportLayout);
        reportLayout.setHorizontalGroup(
            reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportLayout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addGroup(reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportLayout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addGap(291, 291, 291))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(projectcode, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportLayout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(135, 135, 135))))
        );
        reportLayout.setVerticalGroup(
            reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel36)
                .addGap(32, 32, 32)
                .addGroup(reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(projectcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", report);

        assesment.setBackground(new java.awt.Color(239, 240, 234));

        jLabel5.setText("Assesment ID :");

        jLabel8.setText("Project Code :");

        jLabel9.setText("Student ID :");

        jLabel10.setText("Grade : ");

        jLabel11.setText("Status :");

        jLabel12.setText("Assesment File :");

        jLabel13.setText("Assesment Name : ");

        jLabel14.setText("Lecturer Feedback : ");

        jLabel15.setText("Second Marker Feedback : ");

        lfeedback.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        scfeedback.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        assfile.setText("View Assesment File");
        assfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assfileActionPerformed(evt);
            }
        });

        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecExit1.png"))); // NOI18N
        jLabel68.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel68MouseClicked(evt);
            }
        });

        jPanel14.setBackground(new java.awt.Color(209, 211, 199));
        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel14MouseClicked(evt);
            }
        });

        jLabel75.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel75.setText("Generate Report");

        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecGenerate.png"))); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel76)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel75)
                .addGap(17, 17, 17))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel16.setBackground(new java.awt.Color(209, 211, 199));
        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel16MouseClicked(evt);
            }
        });

        jLabel77.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel77.setText("View Report");

        jLabel78.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecViewReport.png"))); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel78)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel77)
                .addGap(17, 17, 17))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel77, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel21.setBackground(new java.awt.Color(209, 211, 199));
        jPanel21.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel21MouseClicked(evt);
            }
        });

        jLabel87.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel87.setText("Edit");

        jLabel88.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecModify.png"))); // NOI18N

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel88)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel87)
                .addGap(17, 17, 17))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel87, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout assesmentLayout = new javax.swing.GroupLayout(assesment);
        assesment.setLayout(assesmentLayout);
        assesmentLayout.setHorizontalGroup(
            assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assesmentLayout.createSequentialGroup()
                .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assesmentLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))
                    .addGroup(assesmentLayout.createSequentialGroup()
                        .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(assesmentLayout.createSequentialGroup()
                                    .addGap(126, 126, 126)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assesmentLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(assesmentLayout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jLabel68)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assesmentLayout.createSequentialGroup()
                        .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(assesmentLayout.createSequentialGroup()
                                .addComponent(stuidtext, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                                .addComponent(jLabel14))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assesmentLayout.createSequentialGroup()
                                .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13))
                            .addGroup(assesmentLayout.createSequentialGroup()
                                .addComponent(grade, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(assesmentLayout.createSequentialGroup()
                                .addComponent(code, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(assname, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lfeedback, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(assfile, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55))
                    .addGroup(assesmentLayout.createSequentialGroup()
                        .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(assesmentLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(37, 37, 37)
                        .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(assesmentLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scfeedback, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        assesmentLayout.setVerticalGroup(
            assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assesmentLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assesmentLayout.createSequentialGroup()
                        .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(assname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assesmentLayout.createSequentialGroup()
                        .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(3, 3, 3)))
                .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(assesmentLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(assesmentLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel14))
                            .addGroup(assesmentLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(stuidtext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))))
                        .addGap(18, 18, 18)
                        .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(grade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(assesmentLayout.createSequentialGroup()
                        .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(assfile, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addComponent(lfeedback, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(assesmentLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addComponent(jLabel15)
                    .addComponent(scfeedback, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(assesmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        jTabbedPane1.addTab("tab6", assesment);

        timedate.setBackground(new java.awt.Color(239, 240, 234));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("Original date and time : ");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setText("New Date : ");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setText("New Time : ");

        newdate.setDateFormatString("yyyy-MM-dd");

        hour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hourActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setText(":");

        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecExit.png"))); // NOI18N
        jLabel67.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel67MouseClicked(evt);
            }
        });

        jPanel17.setBackground(new java.awt.Color(209, 211, 199));
        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel17MouseClicked(evt);
            }
        });

        jLabel79.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel79.setText("Approve");

        jLabel80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecApprove.png"))); // NOI18N

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel80)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel79)
                .addGap(17, 17, 17))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout timedateLayout = new javax.swing.GroupLayout(timedate);
        timedate.setLayout(timedateLayout);
        timedateLayout.setHorizontalGroup(
            timedateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, timedateLayout.createSequentialGroup()
                .addGroup(timedateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(timedateLayout.createSequentialGroup()
                        .addGroup(timedateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(timedateLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel29))
                            .addGroup(timedateLayout.createSequentialGroup()
                                .addContainerGap(118, Short.MAX_VALUE)
                                .addGroup(timedateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(timedateLayout.createSequentialGroup()
                                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(86, 86, 86)
                                        .addComponent(jLabel21))
                                    .addComponent(jLabel28))))
                        .addGap(18, 18, 18)
                        .addGroup(timedateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(newdate, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                            .addComponent(orgdate)))
                    .addGroup(timedateLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(timedateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(timedateLayout.createSequentialGroup()
                                .addComponent(hour, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(minute, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(278, 278, 278))
        );
        timedateLayout.setVerticalGroup(
            timedateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timedateLayout.createSequentialGroup()
                .addGroup(timedateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timedateLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(timedateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(orgdate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)))
                    .addGroup(timedateLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(timedateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timedateLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(newdate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, timedateLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28)
                        .addGap(41, 41, 41)))
                .addGroup(timedateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hour, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minute, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel29))
                .addGap(51, 51, 51)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab7", timedate);

        feedback.setBackground(new java.awt.Color(239, 240, 234));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("You are allow to modify assesment info in this page");

        newfb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newfbActionPerformed(evt);
            }
        });

        jLabel32.setText("Feedback : ");

        jLabel33.setText("Grade : ");

        newgrade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A+", "A", "B", "B+", "C", "D", "F" }));

        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecExit.png"))); // NOI18N
        jLabel66.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel66MouseClicked(evt);
            }
        });

        jPanel18.setBackground(new java.awt.Color(209, 211, 199));
        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel18MouseClicked(evt);
            }
        });

        jLabel81.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel81.setText("Modify");

        jLabel82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecModify.png"))); // NOI18N

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel82)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel81)
                .addGap(17, 17, 17))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout feedbackLayout = new javax.swing.GroupLayout(feedback);
        feedback.setLayout(feedbackLayout);
        feedbackLayout.setHorizontalGroup(
            feedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feedbackLayout.createSequentialGroup()
                .addGroup(feedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feedbackLayout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(jLabel33)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feedbackLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)))
                .addGroup(feedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feedbackLayout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(feedbackLayout.createSequentialGroup()
                        .addComponent(newgrade, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                        .addComponent(jLabel32)
                        .addGap(18, 18, 18)
                        .addComponent(newfb, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feedbackLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(354, 354, 354))
        );
        feedbackLayout.setVerticalGroup(
            feedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feedbackLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(feedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(feedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feedbackLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(feedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(newgrade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33)))
                    .addGroup(feedbackLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(newfb, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        jTabbedPane1.addTab("tab8", feedback);

        studentpre.setBackground(new java.awt.Color(239, 240, 234));

        studentid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentidActionPerformed(evt);
            }
        });

        jLabel16.setText("Student ID : ");

        jLabel17.setText("Presentation ID : ");

        jLabel18.setText("Project Code : ");

        jLabel19.setText("Date & Time : ");

        jLabel20.setText("Project Name : ");

        edit.setText("Reject");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });

        approve.setText("Approve");
        approve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                approveActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(226, 228, 216));

        jLabel26.setText("Second Marker Name : ");

        jLabel27.setText("Second Marker Acceptance : ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scapp)
                    .addComponent(jLabel27)
                    .addComponent(scname, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                    .addComponent(jLabel26))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scname, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel27)
                .addGap(18, 18, 18)
                .addComponent(scapp, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(226, 228, 216));

        jLabel24.setText("Supervisor Name : ");

        jLabel25.setText("Supervisor Acceptance : ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(supapp, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(supervisorname)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(supervisorname, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel25)
                .addGap(18, 18, 18)
                .addComponent(supapp, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(226, 228, 216));

        jLabel22.setText("Student Name : ");

        jLabel23.setText("Student Acceptance : ");

        studapp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studappActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(studapp, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(studentname)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(studentname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addComponent(studapp, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecExit1.png"))); // NOI18N
        jLabel65.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel65MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout studentpreLayout = new javax.swing.GroupLayout(studentpre);
        studentpre.setLayout(studentpreLayout);
        studentpreLayout.setHorizontalGroup(
            studentpreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentpreLayout.createSequentialGroup()
                .addGroup(studentpreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(studentpreLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(studentpreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(studentpreLayout.createSequentialGroup()
                                .addGroup(studentpreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(studentpreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(studentid, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(presentid, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, studentpreLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel65)))
                .addGroup(studentpreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentpreLayout.createSequentialGroup()
                        .addGroup(studentpreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(studentpreLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, studentpreLayout.createSequentialGroup()
                                .addGroup(studentpreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, studentpreLayout.createSequentialGroup()
                                        .addGap(33, 33, 33)
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(codeproject, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, studentpreLayout.createSequentialGroup()
                                        .addGap(35, 35, 35)
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(36, 36, 36)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(studentpreLayout.createSequentialGroup()
                        .addGroup(studentpreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(studentpreLayout.createSequentialGroup()
                                .addGap(309, 309, 309)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(projectname, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(studentpreLayout.createSequentialGroup()
                                .addGap(353, 353, 353)
                                .addComponent(edit)
                                .addGap(28, 28, 28)
                                .addComponent(approve)))
                        .addGap(0, 16, Short.MAX_VALUE)))
                .addGap(22, 22, 22))
        );
        studentpreLayout.setVerticalGroup(
            studentpreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentpreLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(studentpreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(studentid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(codeproject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(projectname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(27, 27, 27)
                .addGroup(studentpreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(presentid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(edit)
                    .addComponent(approve))
                .addGroup(studentpreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentpreLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(studentpreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(studentpreLayout.createSequentialGroup()
                            .addGap(31, 31, 31)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(studentpreLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(33, 33, 33))
        );

        jTabbedPane1.addTab("tab8", studentpre);

        cancelsc.setBackground(new java.awt.Color(239, 240, 234));

        canceltable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lecturer ID", "Date", "Status"
            }
        ));
        jScrollPane4.setViewportView(canceltable);
        if (canceltable.getColumnModel().getColumnCount() > 0) {
            canceltable.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        cancelid.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("Lecturer ID : ");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel35.setText("Timetable Information");

        jPanel3.setBackground(new java.awt.Color(209, 211, 199));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel42.setText("Unschedule");

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecUnschedule.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecExit.png"))); // NOI18N
        jLabel63.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel63MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout cancelscLayout = new javax.swing.GroupLayout(cancelsc);
        cancelsc.setLayout(cancelscLayout);
        cancelscLayout.setHorizontalGroup(
            cancelscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelscLayout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                .addComponent(jLabel35)
                .addGap(310, 310, 310))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelscLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(cancelscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelscLayout.createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelid, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(278, 278, 278))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelscLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelscLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(112, 112, 112))))
        );
        cancelscLayout.setVerticalGroup(
            cancelscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cancelscLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(cancelscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(cancelscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelid, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab9", cancelsc);

        scday.setBackground(new java.awt.Color(239, 240, 234));

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Lecright.png"))); // NOI18N
        jLabel50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel50MouseClicked(evt);
            }
        });

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecLeft.png"))); // NOI18N
        jLabel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel51MouseClicked(evt);
            }
        });

        month1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        month1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        year1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        year1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabel52.setText("Second Marker ID:");

        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecExit.png"))); // NOI18N
        jLabel64.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel64MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout scdayLayout = new javax.swing.GroupLayout(scday);
        scday.setLayout(scdayLayout);
        scdayLayout.setHorizontalGroup(
            scdayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scdayLayout.createSequentialGroup()
                .addGroup(scdayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(scdayLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(scheduleSC, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(scdayLayout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(month1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(year1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel52)
                .addGap(18, 18, 18)
                .addGroup(scdayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scid, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );
        scdayLayout.setVerticalGroup(
            scdayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scdayLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(scdayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(scdayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                        .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(month1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(year1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(scdayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(scdayLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(scheduleSC, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                        .addGap(60, 60, 60))
                    .addGroup(scdayLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(scdayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(scid, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("tab12", scday);

        day.setBackground(new java.awt.Color(239, 240, 234));

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Lecright.png"))); // NOI18N
        jLabel48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel48MouseClicked(evt);
            }
        });

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecLeft.png"))); // NOI18N
        jLabel49.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel49MouseClicked(evt);
            }
        });

        year.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        year.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        month.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        month.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel43.setText("Lecturer ID :");

        jPanel7.setBackground(new java.awt.Color(205, 190, 190));

        jLabel57.setText("New Date : ");

        newdate2.setDateFormatString("yyyy-MM-dd");

        jButton7.setText("Schedule Date");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton9.setText("Edit Schedule");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newdate2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(newdate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(jButton7)
                        .addGap(31, 31, 31)
                        .addComponent(jButton9)
                        .addGap(38, 38, 38))))
        );

        jPanel20.setBackground(new java.awt.Color(209, 211, 199));
        jPanel20.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel20MouseClicked(evt);
            }
        });

        jLabel85.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel85.setText("View Second Marker");

        jLabel86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecView.png"))); // NOI18N

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel86)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel85)
                .addGap(17, 17, 17))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel86, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel85, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout dayLayout = new javax.swing.GroupLayout(day);
        day.setLayout(dayLayout);
        dayLayout.setHorizontalGroup(
            dayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dayLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scheduleid, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dayLayout.createSequentialGroup()
                .addGroup(dayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dayLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(month, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dayLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(timetable1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(dayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dayLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dayLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28))
        );
        dayLayout.setVerticalGroup(
            dayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dayLayout.createSequentialGroup()
                .addGroup(dayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dayLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(dayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(scheduleid, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43))
                        .addGap(28, 28, 28)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dayLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(dayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(year, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(month, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(timetable1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(75, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab10", day);

        profile.setBackground(new java.awt.Color(239, 240, 234));

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel53.setText("Lecture Name :");

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel54.setText("Lecturer ID :");

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel55.setText("Lecturer IC: ");

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel56.setText("Lecturer Email : ");

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel58.setText("Lecturer Phone : ");

        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel59.setText("Profile Information");

        profilephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profilephoneActionPerformed(evt);
            }
        });

        functionala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                functionalaActionPerformed(evt);
            }
        });

        functional.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        functional.setText("Functional Area :");

        profileemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileemailActionPerformed(evt);
            }
        });

        scfunctional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scfunctionalActionPerformed(evt);
            }
        });

        jPanel19.setBackground(new java.awt.Color(209, 211, 199));
        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel19MouseClicked(evt);
            }
        });

        jLabel83.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel83.setText("Edit");

        jLabel84.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/LecModify.png"))); // NOI18N

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel84)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel83)
                .addGap(17, 17, 17))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout profileLayout = new javax.swing.GroupLayout(profile);
        profile.setLayout(profileLayout);
        profileLayout.setHorizontalGroup(
            profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profileLayout.createSequentialGroup()
                .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(profileLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(functional)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profileLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profileLayout.createSequentialGroup()
                                .addComponent(jLabel53)
                                .addGap(10, 10, 10))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profileLayout.createSequentialGroup()
                                .addComponent(jLabel54)
                                .addGap(18, 18, 18)))))
                .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(profileLayout.createSequentialGroup()
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(profileLayout.createSequentialGroup()
                                .addComponent(profileid, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel55))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profileLayout.createSequentialGroup()
                                .addComponent(functionala, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel56)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(profileLayout.createSequentialGroup()
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scfunctional, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(profileLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(profilename, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                        .addComponent(jLabel58)
                        .addGap(6, 6, 6)))
                .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(profilephone, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(profileic, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(profileemail, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profileLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profileLayout.createSequentialGroup()
                        .addComponent(lecimage, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(155, 155, 155))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profileLayout.createSequentialGroup()
                        .addComponent(jLabel59)
                        .addGap(139, 139, 139)))
                .addGap(33, 33, 33)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        profileLayout.setVerticalGroup(
            profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profileLayout.createSequentialGroup()
                .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(profileLayout.createSequentialGroup()
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(profileLayout.createSequentialGroup()
                                .addGap(192, 192, 192)
                                .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(profileid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(profileLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel59)
                                .addGap(18, 18, 18)
                                .addComponent(lecimage, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(7, 7, 7))
                    .addGroup(profileLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(profileic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55))
                        .addGap(11, 11, 11)))
                .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profileLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel53)
                            .addComponent(profilename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(functional)
                            .addComponent(functionala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(scfunctional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))
                    .addGroup(profileLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(profilephone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel56)
                            .addComponent(profileemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("tab12", profile);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 900, 480));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        int a = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout from GoodBrain ?", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0) {
//            System.exit(0);
            this.dispose();
            general_home generalHome = new general_home();
            generalHome.setVisible(true);
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void dashboardbarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardbarMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedComponent(dashboard);
    }//GEN-LAST:event_dashboardbarMouseClicked

    private void studentbarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentbarMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedComponent(student);
         ArrayList<String> ProjectList = test.getProjectInfo();
         String lecture = logID;
         
         // Flag to check if the lecturer is assigned to any project
        boolean isAssigned = false;

        for(String lines : ProjectList){
            String [] parts =  lines.split(";");
            if (parts.length >= 5 && (parts[3].equalsIgnoreCase(lecture) || parts[4].equalsIgnoreCase(lecture))) {
                String intake = parts[2];
                isAssigned = true;
                
                // Get the list of reports
                ArrayList<String> StudentList = test.getStudentInfo();

                // Check if the project has a report
                for (String student : StudentList) {
                    String[] part = student.split(";");
                    if (part.length >= 3 && part[3].equalsIgnoreCase(intake)) {

                        break;
                    }
                }
            
            
            
            
            
            }
        }
        // If the lecturer is not assigned to any project, notify the user
        if (!isAssigned) {
            JOptionPane.showMessageDialog(this, "No student assigned to your Intake.");
        }
        
    }//GEN-LAST:event_studentbarMouseClicked

    private void presentationbarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_presentationbarMouseClicked

        jTabbedPane1.setSelectedComponent(presentation);
        
        ArrayList<String> PresentList = test.getPresentationInfo();
        String lecture = logID;
         
         // Flag to check if the lecturer is assigned to any project
        boolean isAssigned = false;
        // Iterate through the project list
       for (String line : PresentList){
           String [] parts = line.split(";");
           // Check if the lecturer is assigned to the project as a supervisor or co-supervisor
            if (parts.length >= 9 && (parts[6].equalsIgnoreCase(lecture) || parts[8].equalsIgnoreCase(lecture))) {
                String present = parts[0];
                isAssigned = true;
                break;
            }
       }
if (!isAssigned) {
            JOptionPane.showMessageDialog(this, "You did not have any presentation requests.");
        }

        

    }//GEN-LAST:event_presentationbarMouseClicked

    private void reportbarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportbarMouseClicked
        // Select the 'report' tab
        jTabbedPane1.setSelectedComponent(report);

        // Get the list of projects
        ArrayList<String> ProjectList = test.getProjectInfo();
        String lec = logID;

        // Flag to check if the lecturer is assigned to any project
        boolean isAssigned = false;

        // Iterate through the project list
        for (String lines : ProjectList) {
            String[] parts = lines.split(";");

            // Check if the lecturer is assigned to the project as a supervisor or co-supervisor
            if (parts.length >= 5 && (parts[3].equalsIgnoreCase(lec) || parts[4].equalsIgnoreCase(lec))) {
                String project = parts[0];
                isAssigned = true;

                // Get the list of reports
                ArrayList<String> ReportList = test.getReportInfo();

                // Check if the project has a report
                for (String report : ReportList) {
                    String[] part = report.split(";");
                    if (part.length >= 8 && part[1].equalsIgnoreCase(project)) {

                        break;
                    }
                }

            }
        }

        // If the lecturer is not assigned to any project, notify the user
        if (!isAssigned) {
            JOptionPane.showMessageDialog(this, "You did not assign to supervise any assessment.");
        }
    }//GEN-LAST:event_reportbarMouseClicked

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        // TODO add your handling code here:
        int row = Table.getSelectedRow();
        if (row != -1) {
            String StuID = Table.getValueAt(row, 0).toString();

        }
    }//GEN-LAST:event_TableMouseClicked

    private void newfbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newfbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newfbActionPerformed

    private void studentidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentidActionPerformed
        // TODO add your handling code here:
            info();
        int row = presentationtable.getSelectedRow();
        String lec;
        String sc;
        String pcode;
        boolean found =false;
        if(row != -1){
            String StuID = presentationtable.getValueAt(row, 1).toString();
            //JOptionPane.showMessageDialog(this,StuID);
            jTabbedPane1.setSelectedComponent(studentpre);
            studentid.setText(StuID);
            
            ArrayList<String> PresentationList = test.getPresentationInfo();
            for (String lines : PresentationList) {
            String[] parts = lines.split(";");
            if (parts.length >= 9 && parts[1].equals(StuID)) {
                codeproject.setText(parts[2]);
                    presentid.setText(parts[0]);
                    studapp.setText(parts[5]);
                    supapp.setText(parts[7]);
                    scapp.setText(parts[9]);
                    date.setText(parts[3]+" "+parts[4]);

                    lec = parts[6];
                    sc = parts[8];
                    pcode = parts[2];

                 
                      ArrayList<String> StudentList = test.getStudentInfo();
                    for (String line : StudentList) {
                        String[] student = line.split(";");
                        if (student.length >= 4 && StuID.equalsIgnoreCase(student[0])){
                             studentname.setText(student[1]);
                             

                             ArrayList<String> LecturerList = test.getLecturerInfo();
                             
                             for (String lecturer : LecturerList) {
                                 String[] lecture = lecturer.split(";");
                                 
                                 if(lecture.length>=1 && lec.equalsIgnoreCase(lecture[0])){
                                     supervisorname.setText(lecture[1]);
                                      
                                     
                                 }

                                 if (lecture.length >= 1 && sc.equalsIgnoreCase(lecture[0])) {
                                     scname.setText(lecture[1]);

                                     ArrayList<String> ProjectList = test.getProjectInfo();
                                     for (String project : ProjectList) {
                                         String[] projectdata = project.split(";");
                                         
                                         if(projectdata.length >=1 && pcode.equals(projectdata[0])){
                                             projectname.setText(projectdata[1]);
                                             found = true;
                                             break;
                                         }
                                     
                                     
                                     }

                                     }

                                 }
                             }

                         }
            
            }

            }
         
                    
                  

                    
        

        }
        
    }//GEN-LAST:event_studentidActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        // TODO add your handling code here:
        String StuID = studentid.getText();
         JOptionPane.showMessageDialog(this, "Please select another time for  " +StuID + " presentation request.");
        boolean change = true;
          
        String stud = studapp.getText();
         String supp = supapp.getText();
         String scm = scapp.getText();
        if (stud.equalsIgnoreCase("Accept") && supp.equalsIgnoreCase("Accept") && scm.equalsIgnoreCase("Accept")){
             JOptionPane.showMessageDialog(this, "All participants have approved " +StuID + " presentation request. You can't change anything but only can cancel the presentation request.");
             change = false;
         }
        
        if (change){
             String dateTime = date.getText();
    
    // Set the retrieved date and time to the target JTextField
        orgdate.setText(dateTime);
        jTabbedPane1.setSelectedComponent(timedate);
        }
       
    }//GEN-LAST:event_editActionPerformed

    private void approveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_approveActionPerformed
        // TODO add your handling code here:
        boolean approve = true;
          String StuID = studentid.getText();
         String Date = date.getText();
         String stud = studapp.getText();
         String supp = supapp.getText();
         String scm = scapp.getText();
         
         if (stud.equalsIgnoreCase("Accept") && supp.equalsIgnoreCase("Accept") && scm.equalsIgnoreCase("Accept")){
             JOptionPane.showMessageDialog(this, "All participants have approved " +StuID + " presentation request. You can't change anything but only can cancel the presentation request.");
             approve = false;
         }
         
         if (approve){
                 int a = JOptionPane.showConfirmDialog(null, "Are you sure you want to approve " + Date + " as the presentation date ?", "Confirm Approval", JOptionPane.YES_NO_OPTION);
        if(a==0) {
            
            enable();
          showNewState();
          refresh();
          renew();
           JOptionPane.showMessageDialog(this, "You have approved " +StuID + " presentation request.");
    } 
         }
         
                                          

    }//GEN-LAST:event_approveActionPerformed

    private void studappActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studappActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studappActionPerformed

    private void assfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assfileActionPerformed
        // TODO add your handling code here:
        String stuID = stuidtext.getText();
        
        String directoryPath = "src/Assessment/"; // Replace with the actual path to the Assessment directory
        String fileName = assname.getText(); // The base name of the file to open (without extension)
        
        openFileByName(directoryPath, fileName);
    }//GEN-LAST:event_assfileActionPerformed

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedComponent(presentation);
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedComponent(student);
    }//GEN-LAST:event_jPanel9MouseClicked

    private void hourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hourActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hourActionPerformed

    private void jLabel48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseClicked
        // TODO add your handling code here:
        showNextMonth();
    }//GEN-LAST:event_jLabel48MouseClicked

    private void jLabel49MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseClicked
        // TODO add your handling code here:
        showPreviousMonth();
    }//GEN-LAST:event_jLabel49MouseClicked

    private void timetableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timetableMouseClicked
        // TODO add your handling code here:
        String lecID = logID;
        
        jTabbedPane1.setSelectedComponent(day);
         
        calendar(lecID);
        showPreviousMonth();
       
        
        
    }//GEN-LAST:event_timetableMouseClicked

    private void jLabel50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseClicked
        // TODO add your handling code here:
        showNextMonth1();
    }//GEN-LAST:event_jLabel50MouseClicked

    private void jLabel51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseClicked
        // TODO add your handling code here:
        showPreviousMonth1();
    }//GEN-LAST:event_jLabel51MouseClicked

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        
        jTabbedPane1.setSelectedComponent(cancelsc);
        
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Date formattedDate = newdate2.getDate();
        String formattedDate1 = null; // Initialize outside the if block

// If no date is selected, set the JDateChooser to the current date
        if (formattedDate == null) {
            newdate2.setDate(new Date());
            formattedDate = newdate2.getDate();
        }

// Format the selected date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate1 = dateFormat.format(formattedDate); // Set formattedDate1 here

        int a = JOptionPane.showConfirmDialog(null, "Are you sure you want " + formattedDate1 + " become scheduled ?", "Confirm Approval", JOptionPane.YES_NO_OPTION);
        if (a == 0) {
            addSchedule();
            Schedulerefresh();
            showPreviousMonth();
            JOptionPane.showMessageDialog(this, formattedDate1 + " has been scheduled.");
        }


    }//GEN-LAST:event_jButton7ActionPerformed

    private void functionalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_functionalaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_functionalaActionPerformed

    private void profilephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profilephoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_profilephoneActionPerformed

    private void profileemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_profileemailActionPerformed

    private void scfunctionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scfunctionalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_scfunctionalActionPerformed

    private void profilepicMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profilepicMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedComponent(profile);
        
    }//GEN-LAST:event_profilepicMouseClicked

    private void dashpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashpanelMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedComponent(dashboard);
    }//GEN-LAST:event_dashpanelMouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
           // TODO add your handling code here:
         int row = canceltable.getSelectedRow();
        if(row != -1){
            
            String date = canceltable.getValueAt(row, 1).toString();
             int a = JOptionPane.showConfirmDialog(null, "Are you sure you want " + date + " become unschedule ?", "Confirm Approval", JOptionPane.YES_NO_OPTION);
             if(a ==0){
                 cancelDate();
                 
                  Schedulerefresh();
                 showPreviousMonth();
                 JOptionPane.showMessageDialog(this, date + " has been unscheduled.");
                 jTabbedPane1.setSelectedComponent(day);
             }
        }
        
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jLabel63MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel63MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedComponent(day);
    }//GEN-LAST:event_jLabel63MouseClicked

    private void jLabel64MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel64MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedComponent(day);
    }//GEN-LAST:event_jLabel64MouseClicked

    private void jLabel65MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel65MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedComponent(presentation);
    }//GEN-LAST:event_jLabel65MouseClicked

    private void jLabel66MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel66MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedComponent(assesment);
    }//GEN-LAST:event_jLabel66MouseClicked

    private void jLabel67MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel67MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedComponent(studentpre);
    }//GEN-LAST:event_jLabel67MouseClicked

    private void jLabel68MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel68MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedComponent(report);
    }//GEN-LAST:event_jLabel68MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
          int row = Table.getSelectedRow();
        if (row != -1) {
            String StuID = Table.getValueAt(row, 0).toString();
            ArrayList<String> ReportList = test.getReportInfo();
            code.setText("");
            id.setText("");
            status.setText("");
            grade.setText("");
            assname.setText("");
            //assfile.setText("");
            lfeedback.setText("");
            scfeedback.setText("");
            boolean found = false;
            for (String lines : ReportList) {
                String[] parts = lines.split(";");
                if (parts.length >= 8 && parts[2].equals(StuID)) {
                    code.setText(parts[1]);
                    id.setText(parts[0]);
                    status.setText(parts[3]);
                    grade.setText(parts[4]);
                    assname.setText(parts[5]);
                    //assfile.setText(parts[6]);
                    lfeedback.setText(parts[7]);
                    scfeedback.setText(parts[8]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(this, StuID + " did not submit an assessment.");
                jTabbedPane1.setSelectedComponent(student);

            } else {
                jTabbedPane1.setSelectedComponent(assesment);
                stuidtext.setText(StuID);

            }
        }

    
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        // TODO add your handling code here:
        approve();
        int row = Table.getSelectedRow();
        String lec;
        String sc;
        String pcode;
        boolean found = false;
        if (row != -1) {
            String StuID = Table.getValueAt(row, 0).toString();
            

            ArrayList<String> PresentationList = test.getPresentationInfo();
            for (String lines : PresentationList) {
                String[] parts = lines.split(";");
                if (parts.length >= 9 && parts[1].equals(StuID)) {
                     
                    codeproject.setText(parts[2]);
                    presentid.setText(parts[0]);
                    studapp.setText(parts[5]);
                    supapp.setText(parts[7]);
                    scapp.setText(parts[9]);
                    date.setText(parts[3] + " " + parts[4]);

                    lec = parts[6];
                    sc = parts[8];
                   // JOptionPane.showMessageDialog(this,  sc);
                    pcode = parts[2];

                    ArrayList<String> ProjectList = test.getProjectInfo();
                    for (String project : ProjectList) {
                        String[] projectdata = project.split(";");

                        if (projectdata.length >= 1 && pcode.equals(projectdata[0])) {
                             
                            projectname.setText(projectdata[1]);

                            ArrayList<String> StudentList = test.getStudentInfo();
                            for (String line : StudentList) {
                                String[] student = line.split(";");
                                if (student.length >= 4 && StuID.equalsIgnoreCase(student[0])) {
                                    studentname.setText(student[1]);
                                     
                                    ArrayList<String> LecturerList = test.getLecturerInfo();

                                    for (String lecturer : LecturerList) {
                                        String[] lecture = lecturer.split(";");

                                        if (lecture.length >= 3 && lec.equalsIgnoreCase(lecture[0])) {
                                            supervisorname.setText(lecture[1]);
                                            //JOptionPane.showMessageDialog(this, lecture[1]);
                                            
                                            
                                            ArrayList<String> LecturerList1 = test.getLecturerInfo();
                                            for (String lecturer1 : LecturerList1) {
                                                String[] lecture1 = lecturer1.split(";");

                                                if (lecture1.length >= 3 && sc.equalsIgnoreCase(lecture1[0])) {
                                                    //JOptionPane.showMessageDialog(this, lecture1[1]);
                                                    scname.setText(lecture1[1]);

                                                    found = true;
                                                    break;

                                                } else if (lecture1.length >= 3 && sc.equalsIgnoreCase("-")) {
                                                    scname.setText("");

                                                    found = true;
                                                    break;

                                                }

                                            }


                                        }
                                    }

                                }

                        }
                    }

                }}

            }
            
             if (!found) {
                JOptionPane.showMessageDialog(this, StuID + " did not have a presentation request.");
                jTabbedPane1.setSelectedComponent(student);

            } else {
                jTabbedPane1.setSelectedComponent(studentpre);
                
                studentid.setText(StuID);

            }
        }
    }//GEN-LAST:event_jPanel11MouseClicked

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        // TODO add your handling code here:
         info();
        int row = presentationtable.getSelectedRow();
        String lec;
        String sc;
        String pcode;
        boolean found = false;
        if (row != -1) {
            String StuID = presentationtable.getValueAt(row, 1).toString();
            //JOptionPane.showMessageDialog(this,StuID);
            jTabbedPane1.setSelectedComponent(studentpre);
            studentid.setText(StuID);

            ArrayList<String> PresentationList = test.getPresentationInfo();
            for (String lines : PresentationList) {
                String[] parts = lines.split(";");
                if (parts.length >= 9 && parts[1].equals(StuID)) {
                    codeproject.setText(parts[2]);
                    presentid.setText(parts[0]);
                    studapp.setText(parts[5]);
                    supapp.setText(parts[7]);
                    scapp.setText(parts[9]);
                    date.setText(parts[3] + " " + parts[4]);

                    lec = parts[6];
                    sc = parts[8];
                    pcode = parts[2];

                    ArrayList<String> ProjectList = test.getProjectInfo();
                    for (String project : ProjectList) {
                        String[] projectdata = project.split(";");

                        if (projectdata.length >= 1 && pcode.equals(projectdata[0])) {
                            projectname.setText(projectdata[1]);

                            ArrayList<String> StudentList = test.getStudentInfo();
                            for (String line : StudentList) {
                                String[] student = line.split(";");
                                if (student.length >= 4 && StuID.equalsIgnoreCase(student[0])) {
                                    studentname.setText(student[1]);

                                    ArrayList<String> LecturerList = test.getLecturerInfo();

                                    for (String lecturer : LecturerList) {
                                        String[] lecture = lecturer.split(";");

                                        if (lecture.length >= 1 && lec.equalsIgnoreCase(lecture[0])) {
                                            supervisorname.setText(lecture[1]);

                                        }

                                        if (lecture.length >= 1 && sc.equalsIgnoreCase(lecture[0])) {
                                            scname.setText(lecture[1]);
                                            found = true;
                                            break;

                                        }

                                    }
                                }

                            }

                        }
                    }

                }

            }
        }

    }//GEN-LAST:event_jPanel12MouseClicked

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
        // TODO add your handling code here:
         int row = reporttable.getSelectedRow();
        if (row != -1) {
            String AssID = reporttable.getValueAt(row, 0).toString();
            //JOptionPane.showMessageDialog(this,StuID);
            jTabbedPane1.setSelectedComponent(assesment);
            id.setText(AssID);
            ArrayList<String> ReportList = test.getReportInfo();

            for (String lines : ReportList) {
                String[] parts = lines.split(";");

                if (parts.length >= 8 && parts[0].equals(AssID)) {
                    code.setText(parts[1]);
                    stuidtext.setText(parts[2]);
                    status.setText(parts[3]);
                    grade.setText(parts[4]);
                    assname.setText(parts[5]);
                    //assfile.setText(parts[6]);
                    lfeedback.setText(parts[7]);
                    scfeedback.setText(parts[8]);
                }
            }//System.o

        }
    }//GEN-LAST:event_jPanel13MouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
        // TODO add your handling code here:
          String StuID = stuidtext.getText(); // Assuming studentIdTextField is a JTextField where the user inputs the student ID
        generateReport(StuID);

    }//GEN-LAST:event_jPanel14MouseClicked

    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
        // TODO add your handling code here:
        String StuID = stuidtext.getText(); // Assuming studentIdTextField is a JTextField where the user inputs the student ID
        openReport(StuID);
    }//GEN-LAST:event_jPanel16MouseClicked

    private void jPanel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel17MouseClicked
        // TODO add your handling code here:
         String StuID = studentid.getText();
        Date selectedDate = newdate.getDate(); // Get the selected date from newdate

        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Please select a presentation date for " + StuID);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(selectedDate);
            // Set the formatted date to the text field
            String datenew = formattedDate;
            String Hour = (String) hour.getSelectedItem();
            String Minute = (String) minute.getSelectedItem();
            String time = Hour + ":" + Minute;
            String newTime = datenew + " " + time;

            int a = JOptionPane.showConfirmDialog(null, "Are you sure you want to approve " + newTime + " as the presentation date ?", "Confirm Approval", JOptionPane.YES_NO_OPTION);
            if (a == 0) {

                showNewDatetry();
                
                clean();
                refresh();
                renew();
                
                jTabbedPane1.setSelectedComponent(studentpre);

            }

        }
    }//GEN-LAST:event_jPanel17MouseClicked

    private void jPanel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseClicked
        // TODO add your handling code here:
             editAss();
        reloadass();
        newfresh();
        jTabbedPane1.setSelectedComponent(assesment);
    }//GEN-LAST:event_jPanel18MouseClicked

    private void jPanel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel19MouseClicked
        // TODO add your handling code here:
         String lecid = profileid.getText();
        String lecname = profilename.getText();
        String lecic = profileic.getText();
        String phone = profilephone.getText();
        String email = profileemail.getText();
        String fa = functionala.getText();
        String scfa = scfunctional.getText();
        
        ArrayList<String> List = test.getLecturerInfo();

        for (int i = 0; i < List.size(); i++) {
            String line = List.get(i);
            String[] parts = line.split(";");
            if (parts.length >= 7 && parts[0].equalsIgnoreCase(lecid)) {
                parts[1] = lecname;
                parts[2] = lecic;
                parts[3] = phone;
                parts[4] = email;
                parts[5] = fa;
                parts[6] = scfa;
                String updatedLine = String.join(";", parts);
                // Replace the original line with the updated line
                List.set(i, updatedLine);
            }

            String file = "lecturer.txt";
            // Write the updated presentation information back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String lines : List) {
                    writer.write(lines);
                    writer.newLine(); // Add newline after each line
                }
            } catch (IOException ex) {
                Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
            JOptionPane.showMessageDialog(this, lecid+ " , info edit successfully.");
            setProfile();
    }//GEN-LAST:event_jPanel19MouseClicked

    private void jPanel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseClicked
        // TODO add your handling code here:
        String lecid = secID;
         jTabbedPane1.setSelectedComponent(scday);
          
          calendar1(lecid);
        showPreviousMonth1();
        
        if(lecid == null){
            JOptionPane.showMessageDialog(this,  " Your project don't have a Second Marker.");
        }
    }//GEN-LAST:event_jPanel20MouseClicked

    private void jPanel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel21MouseClicked
        // TODO add your handling code here:
         String lecid = logID.toUpperCase();
        String student = stuidtext.getText();
        String pc = code.getText();
        String ass = id.getText();
        
        
        ArrayList<String> ProjectList = test.getProjectInfo();
        for (String lines : ProjectList) {
            String[] parts = lines.split(";");
            if (parts.length >= 5 && parts[0].equalsIgnoreCase(pc)) {
                String lec = parts[3];
                String sc = parts[4];
                ArrayList<String> ReportList = test.getReportInfo();
                for (String line : ReportList) {
                    String part[] = line.split(";");
                    
                    if ( part.length >=8 && lecid.equalsIgnoreCase(lec) && part[2].equalsIgnoreCase(student) && part[0].equalsIgnoreCase(ass)) {
                        newfb.setText(part[7]);
                        
                       

                    }
                    
                    if (part.length>=8 &&lecid.equalsIgnoreCase(sc) && part[2].equalsIgnoreCase(student) && part[0].equalsIgnoreCase(ass)) {
                        newfb.setText(part[8]);
                        

                    }
                }


            }
            
        }
        jTabbedPane1.setSelectedComponent(feedback);
        String Grade = grade.getText();
        newgrade.setSelectedItem(Grade);
        
    }//GEN-LAST:event_jPanel21MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        this.dispose();
        try {
            pmHome = new pmHomePage(logID);
        } catch (IOException ex) {
            Logger.getLogger(Lecturer.class.getName()).log(Level.SEVERE, null, ex);
        }
        pmHome.setVisible(true);
    }//GEN-LAST:event_jButton1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Lecturer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lecturer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lecturer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lecturer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String logID = "";
                if (args.length > 0) {
                    logID = args[0];
                    // Create an instance of the time class
                    time timeComponent = new time();

                    // Set the selected date in the time component
                    // For demonstration purposes, let's assume a date is selected
                    timeComponent.setSelectedDateString(new String());

                    

                } else {
                    System.out.println("Please enter your Lecturer ID.");
                    System.exit(1); // Terminate the program if no logID is provided
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table;
    private javax.swing.JButton approve;
    private javax.swing.JPanel assesment;
    private javax.swing.JButton assfile;
    private javax.swing.JTextField assname;
    private javax.swing.JTextField cancelid;
    private javax.swing.JPanel cancelsc;
    private javax.swing.JTable canceltable;
    private javax.swing.JTextField code;
    private javax.swing.JTextField codeproject;
    private javax.swing.JPanel dashboard;
    private javax.swing.JLabel dashboardbar;
    private javax.swing.JPanel dashpanel;
    private javax.swing.JTextField date;
    private javax.swing.JPanel day;
    private javax.swing.JButton edit;
    private javax.swing.JPanel feedback;
    private javax.swing.JLabel functional;
    private javax.swing.JTextField functionala;
    private javax.swing.JTextField grade;
    private javax.swing.JComboBox<String> hour;
    private javax.swing.JTextField id;
    private javax.swing.JComboBox<String> intakecode;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField lecid;
    private presentation.ImageAvatar lecimage;
    private javax.swing.JTextField lfeedback;
    private javax.swing.JComboBox<String> minute;
    private javax.swing.JLabel month;
    private javax.swing.JLabel month1;
    private com.toedter.calendar.JDateChooser newdate;
    private com.toedter.calendar.JDateChooser newdate2;
    private javax.swing.JTextField newfb;
    private javax.swing.JComboBox<String> newgrade;
    private javax.swing.JLabel notification;
    private javax.swing.JLabel notification1;
    private javax.swing.JTextField orgdate;
    private javax.swing.JPanel presentation;
    private javax.swing.JLabel presentationbar;
    private javax.swing.JTable presentationtable;
    private javax.swing.JTextField presentid;
    private javax.swing.JPanel profile;
    private javax.swing.JTextField profileemail;
    private javax.swing.JTextField profileic;
    private javax.swing.JTextField profileid;
    private javax.swing.JTextField profilename;
    private javax.swing.JTextField profilephone;
    private presentation.ImageAvatar profilepic;
    private javax.swing.JComboBox<String> projectcode;
    private javax.swing.JTextField projectname;
    private javax.swing.JPanel report;
    private javax.swing.JLabel reportbar;
    private javax.swing.JTable reporttable;
    private javax.swing.JTextField scapp;
    private javax.swing.JPanel scday;
    private javax.swing.JTextField scfeedback;
    private javax.swing.JTextField scfunctional;
    private presentation.schedule1 scheduleSC;
    private javax.swing.JTextField scheduleid;
    private javax.swing.JTextField scid;
    private javax.swing.JTextField scname;
    private javax.swing.JTextField status;
    private javax.swing.JTextField studapp;
    private javax.swing.JPanel student;
    private javax.swing.JLabel studentbar;
    private javax.swing.JTextField studentid;
    private javax.swing.JTextField studentname;
    private javax.swing.JPanel studentpre;
    private javax.swing.JTextField stuidtext;
    private javax.swing.JTextField supapp;
    private javax.swing.JTextField supervisorname;
    private javax.swing.JPanel timedate;
    private javax.swing.JLabel timetable;
    private presentation.schedule timetable1;
    private javax.swing.JLabel welcome;
    private javax.swing.JLabel year;
    private javax.swing.JLabel year1;
    // End of variables declaration//GEN-END:variables
}
