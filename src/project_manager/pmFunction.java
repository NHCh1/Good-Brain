/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class pmFunction {
    public static ArrayList<String[]> readFile (String fileName) {
        ArrayList<String[]> dataList = new ArrayList<>();
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split(",");
                dataList.add(parts);
            }
            
        } catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return dataList;
    }
    
    public void setTable (String fileName, JTable table) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
//        ArrayList<String[]> dataList = new ArrayList<>();
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split(",");
                model.addRow(parts);
                System.out.println(Arrays.toString(parts));
//                dataList.add(parts);
            }
            
        } catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    public void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.setRowCount(0);
    }

        
//        String projID = projectinfo.projectID;
//        String projName;
//        String projIntake;
//        String projSupervisor;
//        String projSecondMarker;
//        Date projDueDate;


        
//        int selectedRow = jTable1.getSelectedRow();
//        int modelRow = jTable1.convertRowIndexToModel(selectedRow);
//        
//        String tbBookingid = model.getValueAt(modelRow, 0).toString();
//        String tbRoom = model.getValueAt(modelRow, 1).toString();
//        String tbName = model.getValueAt(modelRow, 2).toString();
//        String tbIC = model.getValueAt(modelRow, 3).toString();
//        String tbContact = model.getValueAt(modelRow, 4).toString();
//        String tbEmail = model.getValueAt(modelRow, 5).toString();
//        String tbFrom = model.getValueAt(modelRow, 6).toString();
//        String tbTo = model.getValueAt(modelRow, 7).toString();
//        String tbNights = model.getValueAt(modelRow, 8).toString();
//        String tbTotal=  model.getValueAt(modelRow, 9).toString();
//        String tbStatus =  model.getValueAt(modelRow, 10).toString();
//        String tbOut = model.getValueAt(modelRow, 11).toString();
//        String tbPmethod = model.getValueAt(modelRow, 12).toString();
//        String tbPstatus = model.getValueAt(modelRow, 13).toString();
//        String tbPtime = model.getValueAt(modelRow, 14).toString();
//        
//        roomid.setText(tbRoom);
//        name.setText(tbName);
//        ic.setText(tbIC);
//        contact.setText(tbContact);
//        email.setText(tbEmail);
//        in.setText(tbFrom);
//        out.setText(tbTo);
//        nights.setText(tbNights);
//        total.setText(tbTotal);
//        bookingid.setText(tbBookingid);
//        status.setSelectedItem(tbStatus);
//        checkoutTime.setText(tbOut);
//        Pmethod.setSelectedItem(tbPmethod);
//        Pstatus.setSelectedItem(tbPstatus);
//        Ptime.setText(tbPtime);
//        
//        bookingid.setEditable(false);
//        roomid.setEditable(false);
//        in.setEditable(false);
//        out.setEditable(false);
//        nights.setEditable(false);
//        total.setEditable(false);
//        
//        if (tbStatus.equals("Checked-out")){
//            status.setEnabled(false);
//        }
//        else{
//            status.setEnabled(true);
//        }
//        
//        checkoutTime.setEditable(false);
//        
//        Pstatus.setEnabled(false);
//        
//        Ptime.setEditable(false);
//
//        if (tbPstatus.equals("paid")){
//            paybtn.setEnabled(false);
//        }else{
//            paybtn.setEnabled(true);
//        }
//    }                  
    }

