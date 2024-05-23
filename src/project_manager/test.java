/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_manager;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class test {
//    public static ArrayList<String> getLoginInfo() throws IOException{
//        
//     String file = "User.txt";
//        ArrayList<String> InfoList = new ArrayList<>();
//        try {
//            BufferedReader read = new BufferedReader(new FileReader(file));
//
//            String line;
//
//            while ((line = read.readLine()) != null) {
//                String[] parts = line.split(",");
//                String ID = parts[0];
//                String pass = parts[1];
//                String role = parts[2];
//                String info = ID+","+pass+","+role;
//                InfoList.add(info);
//                
//                
//            } 
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return InfoList;   
//    }
//    
    public String collectData(String stuid) {
        String fileLocate = "submission.txt";
        String studentFileLocate = "student.txt";

        File submissionFile = new File(fileLocate);
        File studentFile = new File(studentFileLocate);

        try (BufferedReader reader = new BufferedReader(new FileReader(submissionFile))) {
            String line;
            String studentline;
            String info ="";

            // Search in submission file
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                if (rowData.length >= 8 && rowData[2].equals(stuid)) {

                    // Now, search student file to get student name and IC
                    try (BufferedReader studentReader = new BufferedReader(new FileReader(studentFile))) {
                        while ((studentline = studentReader.readLine()) != null) {
                            String[] studentData = studentline.split(",");
                            if (studentData.length >= 3 && studentData[0].equals(stuid)) {
                                // Student info found, return it
                                String name = studentData[1];
                                String IC = studentData[2];
                                info = line + ","+ name+","+IC;
                                System.out.println(info); 
                                return info;
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // No matching student ID found
        return null;
    }
    
    void generateReport(String stuid) throws DocumentException{
        try {
            String report = collectData(stuid);
            if (report == null){
                JOptionPane.showMessageDialog(null, "No data found for student ID: " + stuid);
                return;
            }
            
            String[]fields = report.split(",");
            
            String file = "C:\\Users\\Owx\\Documents\\NetBeansProjects\\Lecturer\\src\\main\\java\\com\\mycompany\\Report\\"+stuid+".pdf" ;
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            
            // Add content to PDF
            Paragraph header = new Paragraph("Assesment Transcript");
            header.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(header);
            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
            document.add(new Paragraph("Student Name: " + fields[9]));
            document.add(new Paragraph("Student ID: " + fields[2]));
            document.add(new Paragraph("Student IC: " + fields[10]));
            document.add(new Paragraph("Intake: " + fields[1]));
            document.add(new Paragraph("Status: " + fields[3]));
            document.add(new Paragraph("Grade: " + fields[4]));
            document.add(new Paragraph("Assesment Name: " + fields[5]));
            document.add(new Paragraph("Lecture Feedback: " + fields[7]));
            document.add(new Paragraph("Second Marker Feedback: " + fields[8]));
            
            document.close();

            JOptionPane.showMessageDialog(null, stuid + " Report generated successfully!");
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (DocumentException ex) {
//            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } 
   }
}
