/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import presentation.Lecturer;

/**
 *
 * @author Owx
 */
public class time1 extends JButton {
    private Date selectedDate; 
    private boolean title;
    private String selectedDateString; // String to hold the selected date
    
    public time1(){
         setContentAreaFilled(false);
        setBorder(new LineBorder(Color.decode("#F6F5F3")));
        setHorizontalAlignment(JLabel.CENTER);
        setBackground(Color.decode("#DFDFDF"));
      //showschedule();
       
        setOpaque(true);
        addMouseListener(new MouseAdapter() {
            @Override
           public void mouseClicked(MouseEvent e) {
                // Handle the click event here
                if (selectedDate != null) {
                    try {
                        // Convert selected date to string
                        setBackground(Color.decode("#E5DD80"));
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        selectedDateString = sdf.format(selectedDate);
                        // Pass the selected date string to the main class method
                        String logID="";
                        Lecturer Lecturer = new Lecturer(logID);
                        Lecturer.setSelectedDateString(selectedDateString);
                    } catch (IOException ex) {
                        Logger.getLogger(time1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    

                   
                }
            }
        });
    
    }
    
    // Method to set the selected date string
    public void setSelectedDateString(String selectedDateString) {
        this.selectedDateString = selectedDateString;
       
    }
    

    
       public void setDate(Date date) {
        this.selectedDate = date;
    }
    
     public void asTitle(){
        title = true;
        setBackground(Color.decode("#658B73"));
        
    }
    
    public boolean isTitle(){
        return title;
    }
    
    public void currentMonth1(boolean act){
        if (act){
            setForeground(new Color(0,0,0));
            
        }else{
            setForeground(new Color(107,107,107));
        }
        
    }

   
            
}
