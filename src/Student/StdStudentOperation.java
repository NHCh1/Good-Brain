package Student;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nightinggale
 */
public class StdStudentOperation {
    
    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    public static LocalDate stringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
        return LocalDate.parse(dateString,formatter);
    }
    
    public static LocalDate stringToLocalDatetime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
        return LocalDate.parse(dateString,formatter);
    }
    
    public static String localDateToString(LocalDate date) {
        return date.toString();
    }
    
    public static Date localDateToDate(LocalDate date) {
        return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public static StdStudentInfo readStudentRecord(String studentID) throws FileNotFoundException, IOException {
        FileReader stuFileReader = new FileReader("student.txt");
        try (BufferedReader stuBufferedReader = new BufferedReader(stuFileReader)) {
            String line;
            boolean found = false; // Flag to track if the student record is found
            while ((line = stuBufferedReader.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(";");
                if (parts[0].equals(studentID)) {
                    String intake = parts[5];
                    ArrayList<String> projectIDlist = readProjectID(intake);
                    String defaultProjectId = projectIDlist.get(0);
//                    System.out.println("Student ID: " + studentID); 
//                    System.out.println("Intake Code: " + intake);
//                    System.out.println("Project ID List: " + defaultProjectId);
//                    for (int i = 0; i < projectIDlist.size(); i++) {
//                        System.out.println(projectIDlist.get(i));
//                    }
                    found = true;
                    return new StdStudentInfo(studentID, intake, projectIDlist, defaultProjectId);
                }
                if (!found) {
                    System.out.println("Student record with ID " + studentID + " not found.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle file IO errors
            }
            return null; // Return null if the student record is not found
        }

        public static ArrayList<String> readProjectID(String intake) throws IOException {
            ArrayList<String> projectIDlist = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("project.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    String[] parts = line.split(";");
                    if (parts.length >= 3 && parts[2].equals(intake)) {
                      String projectID = parts[0];
                      //System.out.println("Project ID: " + projectID);
                      projectIDlist.add(projectID);
                    }
                }
            } catch (FileNotFoundException e) {
            System.out.println("Project record with Intake " + intake + " not found.");
        }
        return projectIDlist;
    }
  
    public static StdProjectInfo readProjectRecord(String selectedProjectId) throws FileNotFoundException {
        FileReader projFileReader = new FileReader("project.txt");
        try (BufferedReader projBufferedReader = new BufferedReader(projFileReader)) {
            String line;
            boolean found = false; // Flag to track if the project record is found
            while ((line = projBufferedReader.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(";");
                if (parts[0].equals(selectedProjectId)) {
                    String projName = parts[1];
                    String superId = parts[3];
                    String secondId = parts[4];
                    LocalDate dueDate = stringToLocalDate(parts[5]);
                    found = true;
                    return new StdProjectInfo(projName, superId, secondId, dueDate);
                }
            }
            if (!found) {
                System.out.println("Project record with ID " + selectedProjectId + " not found.");
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle file IO errors
        }
        return null;
    }
    
    public static StdSubmissionInfo readSubmissionRecord(String selectedProjectID, String studentID) throws FileNotFoundException {
        FileReader subFileReader = new FileReader("submission.txt");
        try (BufferedReader subReader = new BufferedReader(subFileReader)) {
            String line;
            boolean found = false;
            while ((line = subReader.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(";");
                if (parts.length >=9 && parts[2].equals(studentID) && parts[1].equals(selectedProjectID)) {
                    String subID = parts[0];
                    String subStatus = parts[3];
                    String subGrade = parts[4];
                    String subName = parts[5];
                    String subPath = parts[6];
                    String subSuper = parts[7];
                    String subSecond = parts[8];
                    found = true;
                    return new StdSubmissionInfo(subID, subStatus, subGrade, subName, subPath, subSuper, subSecond);
                }
            }
            if (!found) {
                System.out.println("Submission record for " + selectedProjectID + " not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static StdSubmissionInfo createSubmissionRecord(String selectedProjectID, String studentID) throws FileNotFoundException {
        try (BufferedWriter subWriter = new BufferedWriter(new FileWriter("submission.txt", true))) {
            String subID = selectedProjectID + studentID;
            String subStatus = "Pending";
            String subGrade = "-";
            String subName = "-";
            String subPath = "-";
            String subSuper = "Not available";
            String subSecond = "Not available";
            String record = subID +";"+ selectedProjectID +";"+ studentID +";"+ subStatus +";"+ subGrade +";"+ subName +";"+ subPath +";"+ subSuper +";"+ subSecond;
            System.out.println(record);
            subWriter.write(record);
            subWriter.newLine();
            return new StdSubmissionInfo(subID, subStatus, subGrade, subName, subPath, subSuper, subSecond);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        return null;
    }
    
    public static StdSubmissionInfo editSubmissionRecord(String selectedProjectID, String studentID, File subFile) throws IOException {
        List<String> record = new ArrayList<>();
        try (BufferedReader preReader = new BufferedReader(new FileReader("submission.txt"))) {
            String line;
            while ((line = preReader.readLine()) != null) {
                record.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Save uploaded file
        String subDirectory = "Submission/";
        File createDir = new File(subDirectory);
        if (!createDir.exists()) {
            if (createDir.mkdirs()) {
                System.out.println("Directory created successfully: " + subDirectory);
            } else {
                System.out.println("Failed to create directory: " + subDirectory);
                return null;
            }
        }
        File subDestination = new File(subDirectory + subFile.getName());
        try {
            Files.copy(subFile.toPath(), subDestination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied successfully to: " + subDestination.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error copying file to destination");
            return null;
        }
        // Update specific record
        boolean recordUpdated = false;
        for (int i = 0; i < record.size(); i++) {
            String[] parts = record.get(i).split(";");
            System.out.println("Checking record: " + Arrays.toString(parts)); // Debugging: print the parts array
            if (parts.length >= 9) {
                System.out.println("Comparing ProjectID: " + parts[1] + " with " + selectedProjectID); // Debugging: print ProjectID comparison
                System.out.println("Comparing StudentID: " + parts[2] + " with " + studentID); // Debugging: print StudentID comparison
                if (parts[1].equals(selectedProjectID) && parts[2].equals(studentID)) {
                    parts[3] = "pending";
                    parts[5] = subFile.getName();
                    parts[6] = subDirectory + subFile.getName();
                    record.set(i, String.join(";", parts));
                    System.out.println("Updated record: " + record.get(i));
                    recordUpdated = true;
                    break;
                }
            }
        }
        if (!recordUpdated) {
            System.out.println("No matching record found to update.");
        }
        // Write the updated list back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("submission.txt"))) {
            for (String line : record) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static StdPresentationInfo readPresentationRecord(String selectedProjectID, String studentID) throws FileNotFoundException {
        FileReader preFileReader = new FileReader("presentation.txt");
        try (BufferedReader preReader = new BufferedReader(preFileReader)) {
            String line;
            boolean found = false;
            while ((line = preReader.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(";");
                if (parts.length >=10 && parts[2].equals(selectedProjectID) && parts[1].equals(studentID)) {
                    String preID = parts[0];
                    LocalDate preDate = stringToLocalDate(parts[3]);
                    String preTime = parts[4];
                    String preStu = parts[5];
                    String preSuper = parts[7];
                    String preSecond = parts[9];
                    found = true;
                    return new StdPresentationInfo(preID, preDate, preTime, preStu, preSuper, preSecond);
                }
            }
            if (!found) {
                System.out.println("Submission record for " + selectedProjectID + " not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static StdPresentationInfo createPresentationRecord(String selectedProjectID, String studentID, LocalDate selectDate, String selectTime) throws FileNotFoundException {
        String line;
        int recCount = 1;
        try (BufferedReader preReader = new BufferedReader(new FileReader("presentation.txt"))) {
            while ((line = preReader.readLine()) != null) {
                recCount += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter preWriter = new BufferedWriter(new FileWriter("presentation.txt", true))) {
            StdProjectInfo getLecID = StdStudentOperation.readProjectRecord(selectedProjectID);
            System.out.println(recCount);
            String preID = "PR" + Integer.toString(recCount);
            String preDateStr = localDateToString(selectDate);
            String preStu = "Accept";
            String superID = getLecID.getSupervisorId();
            String preSuper = "Pending";
            String secondID = getLecID.getSecondMarkerId();
            String preSecond = "Pending";
            String record = preID +";"+ studentID +";"+ selectedProjectID +";"+ preDateStr +";"+ selectTime +";"+ preStu +";"+ superID +";"+ preSuper +";"+ secondID +";"+ preSecond;
            System.out.println(record);
            preWriter.write(record);
            preWriter.newLine(); // Add a new line after the record
            return new StdPresentationInfo(preID, selectDate, selectTime, preStu, preSuper,preSecond);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        return null;
    }
    
    public static StdSubmissionInfo editPresentationDateRecord(String selectedProjectID, String studentID, LocalDate selectedDate, String selectedTime) throws IOException {
        List<String> record = new ArrayList<>();
        try (BufferedReader preReader = new BufferedReader(new FileReader("presentation.txt"))) {
            String line;
            while ((line = preReader.readLine()) != null) {
                record.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Update specific record
        boolean recordUpdated = false;
        for (int i = 0; i < record.size(); i++) {
            String[] parts = record.get(i).split(";");
            System.out.println("Checking record: " + Arrays.toString(parts));
            if (parts.length >= 10) {
                System.out.println("Comparing ProjectID: " + parts[1] + " with " + selectedProjectID);
                System.out.println("Comparing StudentID: " + parts[2] + " with " + studentID);
                if (parts[2].equals(selectedProjectID) && parts[1].equals(studentID)) {
                    parts[3] = StdStudentOperation.localDateToString(selectedDate);
                    parts[4] = selectedTime;
                    parts[5] = "Accept";
                    parts[7] = "Pending";
                    parts[9] = "Pending";
                    record.set(i, String.join(";", parts));
                    System.out.println("Updated record: " + record.get(i));
                    recordUpdated = true;
                    break;
                }
            }
        }
        if (!recordUpdated) {
            System.out.println("No matching record found to update.");
        }
        // Write the updated list back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("presentation.txt"))) {
            for (String line : record) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static StdPresentationInfo updateStudentAcceptance(String selectedProjectID, String studentID) throws FileNotFoundException {
        List<String> record = new ArrayList<>();
        try (BufferedReader preReader = new BufferedReader(new FileReader("presentation.txt"))) {
            String line;
            while ((line = preReader.readLine()) != null) {
                record.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Update specific record
        for (int i = 0; i < record.size(); i++) {
            String[] parts = record.get(i).split(";");
            if (parts.length >=10 && parts[2].equals(selectedProjectID) && parts[1].equals(studentID)) {
                String preID = parts[0];
                parts[5] = "Accept";
                record.set(i, String.join(";", parts));
                break;
            }
        }
        // Write the updated list back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("presentation.txt"))) {
            for (String line : record) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static StdResultInfo readProjectResult(String stuID) throws IOException {
        StdStudentInfo student = readStudentRecord(stuID);
        return null;
    }
    
    public static StdFeedbackInfo readFeedbackRecord(String selectedProjectID, String studentID) throws FileNotFoundException {
        FileReader preFileReader = new FileReader("feedback.txt");
        try (BufferedReader preReader = new BufferedReader(preFileReader)) {
            String line;
            boolean found = false;
            while ((line = preReader.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(";");
                if (parts[2].equals(selectedProjectID) && parts[1].equals(studentID)) {
                    String feedID = parts[0];
                    String content = parts[3];
                    found = true;
                    return new StdFeedbackInfo(feedID, studentID, selectedProjectID, content);
                }
            }
            if (!found) {
                System.out.println("Feedback record for " + studentID + selectedProjectID + " not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static StdFeedbackInfo createFeedbackRecord(String stuID, String projID, String content) {
        String line;
        int recCount = 1;
        try (BufferedReader feedReader = new BufferedReader(new FileReader("feedback.txt"))) {
            while ((line = feedReader.readLine()) != null) {
                recCount += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter feedWriter = new BufferedWriter(new FileWriter("feedback.txt", true))) {
            String feedID = "FD" + Integer.toString(recCount); //autogenerated
            String record = feedID +";"+ stuID +";"+ projID +";"+ content;
            System.out.println(record);
            feedWriter.write(record);
            feedWriter.newLine(); // Add a new line after the record
            return new StdFeedbackInfo(feedID, stuID, projID, content);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        return null;
    }
}
