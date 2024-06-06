/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package presentation;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.JOptionPane;


/**
 *
 * @author Owx
 */
public class test {
    
    public static ArrayList<String> getLoginInfo(){
        
     String file = "C:\\Users\\Owx\\Documents\\CYB Degree Y2\\NetBeansProjects\\Presentation\\src\\presentation\\user.txt\\";
        ArrayList<String> InfoList = new ArrayList<>();
        try {
            BufferedReader read = new BufferedReader(new FileReader(file));

            String line;

            while ((line = read.readLine()) != null) {
                String[] parts = line.split(";");
                String ID = parts[0];
                String pass = parts[1];
                String role = parts[2];
                String info = ID+";"+pass+";"+role;
                InfoList.add(info);
                
                
            } 
            
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return InfoList;

        
    
        
        
    }
    
    
    

public String collectData(String stuid) {
    String fileLocate = "C:\\Users\\Owx\\Documents\\CYB Degree Y2\\NetBeansProjects\\Presentation\\src\\presentation\\submission.txt\\";
    String studentFileLocate = "C:\\Users\\Owx\\Documents\\CYB Degree Y2\\NetBeansProjects\\Presentation\\src\\presentation\\Student.txt\\";
    String LecFileLocate = "C:\\Users\\Owx\\Documents\\CYB Degree Y2\\NetBeansProjects\\Presentation\\src\\presentation\\lecturer.txt\\";
    String ProjectFileLocate = "C:\\Users\\Owx\\Documents\\CYB Degree Y2\\NetBeansProjects\\Presentation\\src\\presentation\\project.txt\\";
    
    File submissionFile = new File(fileLocate);
    File studentFile = new File(studentFileLocate);
    File LecFile = new File(LecFileLocate);
    File IntakeFile = new File (ProjectFileLocate);

    try (BufferedReader reader = new BufferedReader(new FileReader(submissionFile))) {
        String line;
        String studentline;
        String lecline;
        String lecline1;
        String projectline;
        String info ="";
        String list="";
        String data ="";
        String first="";
       
        // Search in submission file
        while ((line = reader.readLine()) != null) {
            String[] rowData = line.split(";");
            if (rowData.length >= 8 && rowData[2].equals(stuid)) {
                
                // Now, search student file to get student name and IC
                try (BufferedReader studentReader = new BufferedReader(new FileReader(studentFile))) {
                    while ((studentline = studentReader.readLine()) != null) {
                        String[] studentData = studentline.split(";");
                        if (studentData.length >= 3 && studentData[0].equals(stuid)) {
                            // Student info found, return it
                            String name = studentData[1];
                            String IC = studentData[2];
                            String intake = studentData[5];
                            info = line + ";"+ name+";"+IC;
                            //System.out.println(info); 
                            //return info;
                            
                            try (BufferedReader intakeReader = new BufferedReader(new FileReader(IntakeFile))) {
                                while((projectline = intakeReader.readLine())!=null){
                                    String [] projectData = projectline.split(";");
                                    if (projectData.length >= 5 && intake.equalsIgnoreCase(projectData[2])){
                                        String lecturer = projectData[3];
                                        
                                        String secondmarker = projectData[4];
                                        list = info + ";" + lecturer + ";" + secondmarker;
                                        

                                        try (BufferedReader lecReader = new BufferedReader(new FileReader(LecFile))) {
                                            while ((lecline = lecReader.readLine()) != null) {
                                                String[] lecData = lecline.split(";");
                                                if (lecData.length >= 2 && lecturer.equalsIgnoreCase(lecData[0])) {
                                                    String lectureName = lecData[1];
                                                    first = list + ";" + lectureName;

                                                    try (BufferedReader lecReader1 = new BufferedReader(new FileReader(LecFile))) {
                                                        while ((lecline1 = lecReader1.readLine()) != null) {
                                                            String[] lec1Data = lecline1.split(";");
                                                            if (lec1Data.length >=2 && secondmarker.equalsIgnoreCase(lec1Data[0])) {
                                                                String secondName = lec1Data[1];
                                                                data = first + ";" + secondName;
                                                                System.out.println(data);
                                                                return data;
                                                            }

                                                   
                                                    


                                                    }
                                                }}
                                            }
                                        }
                                    }
                                    
                                   
                                    
                                    
                                }

}
                            
                            
                            
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


        
  

  
   
   
   
 void openReport(String stuid) {
        String fileLocate = "C:\\Users\\Owx\\Documents\\CYB Degree Y2\\NetBeansProjects\\Presentation\\src\\Report\\"+stuid+" Assesment Report"+".pdf";
        File file = new File(fileLocate);

        if (file.exists()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error.Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Report does not exist.");
        }
    }
 

    
      void generateReport(String stuid){
        try {
            
            String report = collectData(stuid);
            String[]fields = report.split(";");
            
            
            String file = "C:\\Users\\Owx\\Documents\\CYB Degree Y2\\NetBeansProjects\\Presentation\\src\\Report\\"+stuid+" Assesment Report"+".pdf" ;
            Document document = new Document() {};
           PdfWriter.getInstance(document, new FileOutputStream(file));
           document.open();
    

            // Add Image
            String imagePath = "C:\\Users\\Owx\\Documents\\CYB Degree Y2\\NetBeansProjects\\Presentation\\src\\Icon\\LecReport.jpg\\";
            // Load image
            Image img = Image.getInstance(imagePath);
            img.setAlignment(Image.ALIGN_CENTER);
            img.scaleToFit(30, 30);  // Adjust the size as needed
            Paragraph imagee = new Paragraph();
            imagee.add(img);
            document.add(imagee);
              


// Create a Paragraph combining the image and text
            Paragraph p = new Paragraph();
            
           // p.add(new Chunk(img, 0, 0, true));
            p.add(new Chunk("   GoodBrain", new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD)));
            p.setAlignment(Element.ALIGN_CENTER);

            document.add(p);
            document.add(Chunk.NEWLINE); // Add a line break
            document.add(Chunk.NEWLINE); // Add a line break
         // Add a line break

            // Create a Paragraph for the title
            Paragraph title = new Paragraph();
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            title.add(new Chunk("Assessment Report", titleFont));
            title.setAlignment(Element.ALIGN_CENTER); // Set alignment to center
            document.add(title);

            

            // Add a separator line
            LineSeparator separator = new LineSeparator();
            separator.setLineColor(BaseColor.BLACK);
            document.add(new Chunk(separator));

            document.add(Chunk.NEWLINE); // Add a line break
            
    

            document.add(Chunk.NEWLINE); // Add a line break

            // Content font styles
            Font boldFont = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);

            // Creating Paragraphs with mixed fonts using Chunk
            Paragraph p1 = new Paragraph();
            p1.add(new Chunk("Student Name: ", boldFont));
            p1.add(new Chunk(fields[9], normalFont));
            document.add(p1);

            Paragraph p2 = new Paragraph();
            p2.add(new Chunk("Student ID: ", boldFont));
            p2.add(new Chunk(fields[2], normalFont));
            document.add(p2);

            Paragraph p3 = new Paragraph();
            p3.add(new Chunk("Student IC: ", boldFont));
            p3.add(new Chunk(fields[10], normalFont));
            document.add(p3);

            Paragraph p4 = new Paragraph();
            p4.add(new Chunk("Intake: ", boldFont));
            p4.add(new Chunk(fields[1], normalFont));
            document.add(p4);
            
            Paragraph p7 = new Paragraph();
            p7.add(new Chunk("Assessment Name: ", boldFont));
            p7.add(new Chunk(fields[5], normalFont));
            document.add(p7);

            Paragraph p5 = new Paragraph();
            p5.add(new Chunk("Status: ", boldFont));
            p5.add(new Chunk(fields[3], normalFont));
            document.add(p5);

            Paragraph p6 = new Paragraph();
            p6.add(new Chunk("Grade: ", boldFont));
            p6.add(new Chunk(fields[4], normalFont));
            document.add(p6);

            
            
            document.add(Chunk.NEWLINE);
            // Add another separator line
            document.add(new Chunk(separator));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            
           // Subheading for Feedback with Helvetica BoldItalic
            Font subheadingFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph subheading = new Paragraph("Feedback", subheadingFont);
            subheading.setAlignment(Element.ALIGN_LEFT);
            document.add(subheading);
            document.add(Chunk.NEWLINE);

            Paragraph p8 = new Paragraph();
            p8.add(new Chunk("Lecture Name: ",boldFont));
            p8.add(new Chunk(fields[13], normalFont));
            p8.add(new Chunk("\n")); // Add a line break
            p8.add(new Chunk("Lecture Feedback: ", boldFont));
            p8.add(new Chunk(fields[7], normalFont));
            document.add(p8);
            
            document.add(Chunk.NEWLINE);

            Paragraph p9 = new Paragraph();
             p9.add(new Chunk("Second Marker Name: ", boldFont));
            p9.add(new Chunk(fields[14], normalFont));
            p9.add(new Chunk("\n")); // Add a line break
            p9.add(new Chunk("Second Marker Feedback: ", boldFont));
            p9.add(new Chunk(fields[8], normalFont));
            document.add(p9);

            document.add(Chunk.NEWLINE); // Add a line break

           

            document.close();

            JOptionPane.showMessageDialog(null, stuid + " Report generated successfully!");
           
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
      
       public static ArrayList<String> getScheduleInfo(){
        
     String file = "C:\\Users\\Owx\\Documents\\CYB Degree Y2\\NetBeansProjects\\Presentation\\src\\presentation\\schedule.txt\\";
        ArrayList<String> ScheduleList = new ArrayList<>();
        try {
            BufferedReader read = new BufferedReader(new FileReader(file));

            String line;

            while ((line = read.readLine()) != null) {
                String[] parts = line.split(";");
                
                String lecid = parts[0];
                String scdate = parts[1];
                if (parts[2].equalsIgnoreCase("Schedule")){
                    String status =parts[2];
                    String info = lecid+";"+scdate+";"+status;      
                ScheduleList.add(info);
                }                 
            } 
           
           
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ScheduleList;

        
    
        
        
    }
      
      
          public static ArrayList<String> getIntakeInfo(){
        
     String file = "C:\\Users\\Owx\\Documents\\CYB Degree Y2\\NetBeansProjects\\Presentation\\src\\presentation\\intake.txt\\";
        ArrayList<String> IntakeList = new ArrayList<>();
        try {
            BufferedReader read = new BufferedReader(new FileReader(file));

            String line;

            while ((line = read.readLine()) != null) {
                String[] parts = line.split(";");
                String intakecode = parts[0];
                String level = parts[1];
                String name = parts[2];
                String duration = parts[3];
                String registerstart =parts[4];
                String registerend =parts[5];
                String classstart =parts[6];
                String classend =parts[7];
                String info = intakecode+";"+level+";"+name+";"+duration+";"+registerstart+";"+registerend+";"+classstart+";"+classend;
                IntakeList.add(info);
                
                
            } 
            
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return IntakeList;

        
    
        
        
    }
    
 


          
          
          
           
    public static ArrayList<String> getPresentationInfo(){
        
     String file = "C:\\Users\\Owx\\Documents\\CYB Degree Y2\\NetBeansProjects\\Presentation\\src\\presentation\\presentation.txt\\";
        ArrayList<String> PresentationList = new ArrayList<>();
        try {
            BufferedReader read = new BufferedReader(new FileReader(file));

            String line;

            while ((line = read.readLine()) != null) {
                String[] parts = line.split(";");
                String presentationid = parts[0];
                String studentid = parts[1];
                String projectcode = parts[2];
                String day = parts[3];
                String time =parts[4];
                String stuapp =parts[5];
                String lecid =parts[6];
                String lecapp =parts[7];
                String scid =parts[8];
                String scapp = parts[9];
                String info = presentationid+";"+studentid+";"+projectcode+";"+day+";"+time+";"+stuapp+";"+lecid+";"+lecapp+";"+scid+";"+scapp;
                PresentationList.add(info);
                
                
            } 
            
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return PresentationList;

        
    
        
        
    }
        
     public static ArrayList<String> getProjectInfo(){
        
     String file = "C:\\Users\\Owx\\Documents\\CYB Degree Y2\\NetBeansProjects\\Presentation\\src\\presentation\\project.txt\\";
        ArrayList<String> ProjectList = new ArrayList<>();
        try {
            BufferedReader read = new BufferedReader(new FileReader(file));

            String line;

            while ((line = read.readLine()) != null) {
                String[] parts = line.split(";");
               
                String projectcode = parts[0];
                String projectname = parts[1];
                String intakecode = parts[2];
                String lecturer =parts[3];
                String sc = parts[4];
                String date = parts[5];
                String info = projectcode+";"+projectname+";"+intakecode+";"+lecturer+";"+sc +";"+date;
                ProjectList.add(info);
                
                
            } 
            
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ProjectList;

        
    
        
        
    }
    
     public static ArrayList<String> getReportInfo(){
        
     String file = "C:\\Users\\Owx\\Documents\\CYB Degree Y2\\NetBeansProjects\\Presentation\\src\\presentation\\submission.txt\\";
        ArrayList<String> ReportList = new ArrayList<>();
        String stuid;
        try {
            BufferedReader read = new BufferedReader(new FileReader(file));

            String line;

            while ((line = read.readLine()) != null) {
                String[] parts = line.split(";");
                String id = parts[0];
                String projectcode = parts[1];
                stuid = parts[2];
                String status = parts[3];
                String grade =parts[4];
                String assname = parts[5];
                String assfile = parts[6];
                String lfeedback =parts[7];
                String scfeedback = parts[8];
                String info = id+";"+projectcode+";"+stuid+";"+status+";"+grade+";"+assname+";"+assfile+";"+lfeedback+";"+scfeedback;
                ReportList.add(info);
                
                
            } 
            
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ReportList;

        
    
        
        
    }
    
    
    public static ArrayList<String> getStudentInfo(){
        
     String file = "C:\\Users\\Owx\\Documents\\CYB Degree Y2\\NetBeansProjects\\Presentation\\src\\presentation\\Student.txt\\";
        ArrayList<String> StudentList = new ArrayList<>();
        try {
            BufferedReader read = new BufferedReader(new FileReader(file));

            String line;

            while ((line = read.readLine()) != null) {
                String[] parts = line.split(";");
                String StudentID = parts[0];
                String StudentName = parts[1];
                String Email = parts[4];
                String Intake = parts[5];
                
                String info = StudentID+";"+StudentName+";"+Email+";"+Intake;
                StudentList.add(info);
                
                
            } 
           
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return StudentList;

        
    
        
        
    }

 public static ArrayList<String> getLecturerInfo(){
        
     String file = "C:\\Users\\Owx\\Documents\\CYB Degree Y2\\NetBeansProjects\\Presentation\\src\\presentation\\lecturer.txt\\";
        ArrayList<String> LecturerList = new ArrayList<>();
        try {
            BufferedReader read = new BufferedReader(new FileReader(file));

            String line;

            while ((line = read.readLine()) != null) {
                String[] parts = line.split(";");
                String LecID = parts[0];
                String LecName = parts[1];
                String LecIC = parts[2];
                String LecPhone = parts[3];
                String LecEmail = parts[4];
                String FA1 = parts[5];
                String FA2 = parts[6];
                String PM= parts[7];
                
                String info = LecID+";"+LecName+";"+LecIC+";"+LecPhone+";"+LecEmail+";"+FA1+";"+FA2+";"+PM;
                LecturerList.add(info);
                
                
            } 
           
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return LecturerList;

        
    
        
        
    }

   

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        //RewritePresentationInfo(stuid);
    }
}
