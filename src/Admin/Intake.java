/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Intake {
    private String intakeCode;
    private String studyLevel;
    private String intakeDuration;
    private String intakeRegisterStartDate;
    private String intakeRegisterEndDate;
    private String intakeStartDate;
    private String intakeEndDate;
    private String course;
    
    private String namePrefix;
    private String levelPrefix;
    private String datePrefix;
    private String courseAbbr;
    
    
    private DefaultTableModel table;
    
    //For validation purpose
    public Intake(String level, String course, String duration, String rStartDate, String rEndDate, String iStartDate, String iEndDate){
        this.studyLevel = level;
        this.course = course;
        this.intakeDuration = duration;
        this.intakeRegisterStartDate = rStartDate;
        this.intakeRegisterEndDate = rEndDate;
        this.intakeStartDate = iStartDate;
        this.intakeEndDate = iEndDate;       
    }
    
    //For adding intake purpose
    public Intake(String code, String level, String course, String duration, String rStartDate, String rEndDate, String iStartDate, String iEndDate){
        this.intakeCode = code;
        this.studyLevel = level;
        this.course = course;
        this.intakeDuration = duration;
        this.intakeRegisterStartDate = rStartDate;
        this.intakeRegisterEndDate = rEndDate;
        this.intakeStartDate = iStartDate;
        this.intakeEndDate = iEndDate;       
    }
    
    //Create intake code
    public Intake(String level, String startDate, String course){
        this.studyLevel = level;
        this.intakeStartDate = startDate;
        this.course = course;
    }
    
    public Intake(DefaultTableModel table){
        this.table = table;
    }
    
    public Intake(){
        
    }
    
    public Intake(String code){
        this.intakeCode = code;
    }
    
    public String getIntakeCode(){
        return intakeCode;
    }
    
    public void createIntakeCode() throws IOException {
        createNamePrefix();
        createLevelPrefix();
        createDatePrefix();
        createCoursePrefix();
        intakeCode = namePrefix + levelPrefix + datePrefix + courseAbbr;
    }
    
    private String createNamePrefix(){
        namePrefix = "GB";
        return namePrefix;
    }
    
    private void createLevelPrefix(){
        if (studyLevel.equals("Foundation")){
            levelPrefix = "F";
        }
        else if(studyLevel.equals("Diploma")){
            levelPrefix = "U";
        }
        else if(studyLevel.equals("Bachelor's Degrees")){
            levelPrefix = "D";
        }
        else if(studyLevel.equals("Master")){
            levelPrefix = "M";
        }
    }
    
    private void createDatePrefix(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate intakeStart = LocalDate.parse(intakeStartDate, formatter);
        YearMonth yearMonth = YearMonth.from(intakeStart);
        int yearLastTwoDigits = yearMonth.getYear() % 100;
        datePrefix = String.format("%02d%02d", yearLastTwoDigits, yearMonth.getMonthValue());
    }
    
    private void createCoursePrefix(){
        if(course.equals("Accounting")){
            courseAbbr = "AC";
        }
        else if(course.equals("Banking")){
            courseAbbr = "BK";
        }
        else if(course.equals("Cloud Engineering")){
            courseAbbr = "CE";
        }
        else if(course.equals("Cyber Security")){
            courseAbbr = "CYB";
        }
        else if(course.equals("Data Analytics")){
            courseAbbr = "DA";
        }
        else if(course.equals("Design")){
            courseAbbr = "DS";
        }
        else if(course.equals("Digital Forensic")){
            courseAbbr = "DF";
        }
        else if(course.equals("Digital Marketing")){
            courseAbbr = "DM";
        }
        else if(course.equals("Engineering")){
            courseAbbr = "EN";
        }
        else if(course.contains("Finanace")){
            courseAbbr = "FN";
        }
        else if(course.equals("FinTech")){
            courseAbbr = "FT";
        }
        else if(course.equals("Intelligent System")){
            courseAbbr = "IS";
        }
        else if(course.equals("Software Engineering")){
            courseAbbr = "SE";
        }
        else if(course.equals("Psychology")){
            courseAbbr = "PSY";
        }
    }
        
    private ArrayList<String[]> readIntake() throws IOException{
        ArrayList<String[]> intakes = new ArrayList<String[]>();
        BufferedReader br = new BufferedReader(new FileReader("intake.txt"));
        String data;
                
        while ((data = br.readLine()) != null){
            String [] intakeData = data.split(";");
            intakes.add(intakeData);
        }
        br.close();
        
        return intakes;
    }
    
        public boolean checkIsCodeCreated() throws IOException{
        ArrayList<String[]> intakes = readIntake();

        for (String[] intakeData : intakes) {
            String newIntakeCode = intakeData[0];
            
            if (intakeCode.equals(newIntakeCode)) {
                JOptionPane.showMessageDialog(null, "Duplicated intake code detected. Please re-create.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
    
    
    public void addIntake(){
        try{
            FileWriter writer = new FileWriter("intake.txt", true);            
            writer.write(intakeCode + ";" + studyLevel + ";" + course + ";" + intakeDuration + ";" + "-" + ";" + "-" + ";" +
                    intakeRegisterStartDate + "," + intakeRegisterEndDate + "," + intakeStartDate + "," + intakeEndDate + "\n");
            writer.close();

            Icon icon = new ImageIcon(getClass().getResource("/Icon/success.png"));
            JOptionPane.showMessageDialog(null, "New intake has been added! ", 
                                    "Notification", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "New intake creation failed! ", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void showIntake(){
        FileHandler fh = new FileHandler();
        fh.displayData("intake.txt", table);
    }
    
//    public void addIntakeIntoComboBox(JComboBox<String> cb, String currentIntakeCode) {
//        try {
//            cb.removeAllItems();
//            BufferedReader br = new BufferedReader(new FileReader("intake.txt"));
//            Object[] rows = br.lines().toArray();
//
//            LocalDate currentDate = LocalDate.now();
//
////             Add the current intake code to the combo box
//            if (currentIntakeCode != null) {
//                cb.addItem(currentIntakeCode);
//            }
//
//            for (int i = 0; i < rows.length; i++) {
//                String line = rows[i].toString();
//                String[] dataRow = line.split(",");
//                String intakeCode = dataRow[0];
//                String rStartDate = dataRow[4];
//                String rEndDate = dataRow[5];
//
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                LocalDate registrationStartDate = LocalDate.parse(rStartDate, formatter);
//                LocalDate registrationEndDate = LocalDate.parse(rEndDate, formatter);
//
//                if (currentDate.isAfter(registrationStartDate) && currentDate.isBefore(registrationEndDate.plusDays(1))) {
//                    cb.addItem(intakeCode);               
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void addIntakeIntoComboBox(JComboBox<String> cb, String currentIntakeCode) {
//    try {
//        cb.removeAllItems();
//        BufferedReader br = new BufferedReader(new FileReader("intake.txt"));
//        Object[] rows = br.lines().toArray();
//
//        LocalDate currentDate = LocalDate.now();
//
//        // Add the current intake code to the combo box if it doesn't already exist
//        if (!cb.getItemCount() > 0 && currentIntakeCode != null) {
//            cb.addItem(currentIntakeCode);
//        } else if (cb.getItemCount() > 0 && !cb.contains(currentIntakeCode)) {
//            cb.addItem(currentIntakeCode);
//        }
//
//        for (int i = 0; i < rows.length; i++) {
//            String line = rows[i].toString();
//            String[] dataRow = line.split(",");
//            String intakeCode = dataRow[0];
//            String rStartDate = dataRow[4];
//            String rEndDate = dataRow[5];
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate registrationStartDate = LocalDate.parse(rStartDate, formatter);
//            LocalDate registrationEndDate = LocalDate.parse(rEndDate, formatter);
//
//            if (currentDate.isAfter(registrationStartDate) && currentDate.isBefore(registrationEndDate.plusDays(1))) {
//                if (!cb.contains(intakeCode)) {
//                    cb.addItem(intakeCode);
//                }
//            }
//        }
//
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//}
    
    
    public void addIntakeIntoComboBox(JComboBox cb){
        try{
            cb.removeAllItems();
            cb.addItem("");
            BufferedReader br = new BufferedReader(new FileReader("intake.txt"));
            Object[] rows = br.lines().toArray();

            LocalDate currentDate = LocalDate.now();

            for (int i = 0; i < rows.length; i++){
                String line = rows[i].toString();
                String [] dataRow = line.split(";");
                String intakeCode = dataRow[0];
                String rStartDate = dataRow[5];
                String rEndDate = dataRow[6];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate registrationStartDate = LocalDate.parse(rStartDate, formatter);
                LocalDate registrationEndDate = LocalDate.parse(rEndDate, formatter);

                if (currentDate.isAfter(registrationStartDate) && currentDate.isBefore(registrationEndDate.plusDays(1))) {
                    cb.addItem(intakeCode);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addIntakeCodeForFilter(JComboBox cb){
        ArrayList<String> intakeCodes = new ArrayList<>();
        try (
            BufferedReader br = new BufferedReader(new FileReader("intake.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                intakeCodes.add(line.split(";")[0]);
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
        }
        cb.removeAllItems();
        cb.addItem("");
        for (String code : intakeCodes) {
            cb.addItem(code);
        }
    }

    
}
