/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Admin;

import Swing.CardModel;
import ChartFrame.ModelChart;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import TableController.TableActionCellRender;
import TableController.TableActionCellEditor;
import TableController.TableActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import TableController.TableAlignment;
import TableController.TableGradientCell;
import java.util.HashSet;
import java.util.Set;



public class AdminPages extends javax.swing.JFrame {

    private final JFileChooser openFileChooser;
    private final JFileChooser saveFileChooser;
    
    private Dimension originalSize;
    private Timer zoomTimer;
    private double scaleFactor = 1.1; 
    
    private BufferedImage originalBI;
    private BufferedImage newBI;
    
    public AdminPages() {
        initComponents();
        displayUserCount();

        adminProfile.setIcon(new ImageIcon("src/Profile/A001.jpg"));
        //set gradient color to cell
//        TableGradientCell tableGradient = new TableGradientCell(new Color(23,161,115), new Color(12,12,14));
//        studentJTable.setDefaultRenderer(Object.class, new TableGradientCell());
//        lecturerTable.setDefaultRenderer(Object.class, new TableGradientCell1());
//        intakeTable.setDefaultRenderer(Object.class, new TableGradientCell1());

        studentJTable.setDefaultRenderer(Object.class, new TableGradientCell());
        studentTablePanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:1,1,1,1,$TableHeader.bottomSeparatorColor,,10");
        studentJTable.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
                + "hoverBackground:null;"
                + "pressedBackground:null;"
                + "separatorColor:$TableHeader.background");
        studentScrollPane.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:3,0,3,0,$Table.background,10,10");
        studentScrollPane.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "hoverTrackColor:null");
        
        chart.addLegend("Foundation", new Color(58, 38, 24), new Color(58, 38, 24));
        chart.addLegend("Diploma", new Color(117, 64, 67), new Color(117, 64, 67));
        chart.addLegend("Degree", new Color(154, 136, 115), new Color(154, 136, 115));
        chart.addLegend("Master", new Color(55, 66, 61), new Color(55, 66, 61));
        chart.addData(new ModelChart("2019", new double[]{2800, 2950, 3012, 2650}));
        chart.addData(new ModelChart("2020", new double[]{2600, 2750, 2900, 3150}));
        chart.addData(new ModelChart("2019", new double[]{2905, 3500, 2330,2200}));
        chart.addData(new ModelChart("2022", new double[]{2480, 2150, 2500, 2100}));
        chart.addData(new ModelChart("2023", new double[]{3500, 3200, 3000, 2150}));
        chart.addData(new ModelChart("2024", new double[]{1900, 2800, 3810, 2200}));
        chart.start();
        
        originalSize = new Dimension(215, 110);
        cardPanel1.setPreferredSize(originalSize);
        // Create a Timer for zoom effect
        zoomTimer = new Timer(20, new ActionListener() {
            double scale = 0.8;

            @Override
            public void actionPerformed(ActionEvent e) {
                scale *= scaleFactor;
                if (scale >= 1.05) { // Adjust the maximum scale as needed
                    scale = 1.05;
                    zoomTimer.stop(); // Stop the timer when maximum scale is reached
                }
                Dimension newSize = new Dimension((int) (originalSize.width * scale), (int) (originalSize.height * scale));
                cardPanel1.setPreferredSize(newSize);
                cardPanel1.revalidate(); // Ensure layout updates
                cardPanel1.repaint(); // Repaint the panel
            }
        });
        
        
        openFileChooser = new JFileChooser();
        saveFileChooser = new JFileChooser();      
        
        addPlaceholder(studentPageSearchField);
        addPlaceholder(lecturePageSearchField);
        addPlaceholder(intakePageSearchField);
        addPlaceholder(userPageSearchField);
        
        DefaultTableModel table = (DefaultTableModel) studentJTable.getModel();
        DefaultTableModel lecTable = (DefaultTableModel) lecturerTable.getModel();
        DefaultTableModel usertbl = (DefaultTableModel) userTable.getModel();
        
        //Call action button funtion
        TableActionEvent studentActionEvent = new TableActionEvent(){
            @Override
            public void onEdit(int row) {
                Student edit = new Student(table);
                String studID = studentJTable.getValueAt(studentJTable.getSelectedRow(),0).toString();
                String name = studentJTable.getValueAt(studentJTable.getSelectedRow(), 1).toString();
                String ic = studentJTable.getValueAt(studentJTable.getSelectedRow(), 2).toString();
                String contact = studentJTable.getValueAt(studentJTable.getSelectedRow(), 3).toString();
                String email = studentJTable.getValueAt(studentJTable.getSelectedRow(), 4).toString();                             
                String intake = studentJTable.getValueAt(studentJTable.getSelectedRow(), 5).toString();
//                String lecturer = studentJTable.getValueAt(studentJTable.getSelectedRow(), 6).toString();

                jTabbedPane1.setSelectedIndex(4);

                //add the intake code from txt into combo box
                Intake intakeClass = new Intake();
                intakeClass.addIntakeIntoComboBox(newIntakeCodeComboBox);

                File imageFile = new File("src/Profile/" + studID + ".jpg");

                if (imageFile.exists()) {
                    studentNewAvatar1.setIcon(new ImageIcon(imageFile.getAbsolutePath()));
                }
                 else {
                    studentNewAvatar1.setIcon(null);
                }
                
                studentIDLabel.setText(studID);
                studentnNameLabel.setText(name);
                studentICLabel.setText(ic);
                studentNewContactField.setText(contact);
                studentEmailLabel.setText(email);       
                newIntakeCodeComboBox.setSelectedItem(intake);    
            }

            @Override
            public void onDelete(int row) {
                if(studentJTable.isEditing()){
                    studentJTable.getCellEditor().stopCellEditing();
                }

                int decision = JOptionPane.showConfirmDialog(null, "Are you sure to remove this student? \n This action is irreversible"
                                   ,"Alert", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(decision == JOptionPane.YES_OPTION){
                    String id = studentJTable.getValueAt(studentJTable.getSelectedRow(), 0).toString();
                    table.removeRow(row);
                    Student delete = new Student(id);
                    delete.deleteStudent();
                    Icon icon = new ImageIcon(getClass().getResource("/Icon/success.png"));
                    JOptionPane.showMessageDialog(null, "Student has been removed.", "Notification", JOptionPane.INFORMATION_MESSAGE, icon);
                    
                    displayUserCount();
                }
                else if(decision == JOptionPane.NO_OPTION){
                    Icon icon = new ImageIcon(getClass().getResource("/Icon/shield.png"));
                    JOptionPane.showMessageDialog(null, "No changes has been made.", "Notification", JOptionPane.INFORMATION_MESSAGE,icon);                    
                }
            }
        };
        
        TableActionEvent lecturerActionEvent = new TableActionEvent(){
            @Override
            public void onEdit(int row) {
                String id = lecturerTable.getValueAt(lecturerTable.getSelectedRow(), 0).toString();
                String name = lecturerTable.getValueAt(lecturerTable.getSelectedRow(), 1).toString();
                String ic = lecturerTable.getValueAt(lecturerTable.getSelectedRow(), 2).toString();
                String contact = lecturerTable.getValueAt(lecturerTable.getSelectedRow(), 3).toString();
                String email = lecturerTable.getValueAt(lecturerTable.getSelectedRow(), 4).toString();
                String major = lecturerTable.getValueAt(lecturerTable.getSelectedRow(), 5).toString();
                String minor = lecturerTable.getValueAt(lecturerTable.getSelectedRow(), 6).toString();
                String pmRole = lecturerTable.getValueAt(lecturerTable.getSelectedRow(), 7).toString();
                
                jTabbedPane1.setSelectedIndex(8);
                
                addCourseIntoComboBoxForUpdate();
                
                File imageFile = new File("src/Profile/" + id + ".jpg");

                if (imageFile.exists()) {
                    lecturerAvatarNew.setIcon(new ImageIcon(imageFile.getAbsolutePath()));
                }
                 else {
                    lecturerAvatarNew.setIcon(null);
                }
                
                lectureIDLabel.setText(id);
                lcNameLabel.setText(name);
                lcICLabel1.setText(ic);
                lcNewContactField.setText(contact);
                lcEmailLabel.setText(email);
                lecNewMajorComboBox.setSelectedItem(major);
                lecNewMinorComboBox.setSelectedItem(minor);
                yesNoGroup.setSelected(null, true);
                //button set
//                if(pmRole.equals("Yes")){
//                    newYesCheckBox.setSelected(true);
//                }
//                else{
//                    newNoCheckBox.setSelected(true);
//                }
            }

            @Override
            public void onDelete(int row) {
                if(lecturerTable.isEditing()){
                    lecturerTable.getCellEditor().stopCellEditing();
                }

                int decision = JOptionPane.showConfirmDialog(null, "Are you sure to remove this lecturer?" + " \n This action is irreversible" ,"Alert", 
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(decision == JOptionPane.YES_OPTION){
                    String id = lecturerTable.getValueAt(lecturerTable.getSelectedRow(), 0).toString();
                    lecTable.removeRow(row);
                    Lecturer delete = new Lecturer(id);
                    delete.deleteLecturer();
                    Icon icon = new ImageIcon(getClass().getResource("/Icon/success.png"));
                    JOptionPane.showMessageDialog(null, "Lecturer has been removed.", "Notification", JOptionPane.INFORMATION_MESSAGE, icon);
                    displayUserCount();
                }
                else if(decision == JOptionPane.NO_OPTION){
                    Icon icon = new ImageIcon(getClass().getResource("/Icon/shield.png"));
                    JOptionPane.showMessageDialog(null, "No changes has been made.", "Notification", 
                            JOptionPane.INFORMATION_MESSAGE,icon);                    
                }
            }
        };
        
        TableActionEvent userActionEvent = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                String id = userTable.getValueAt(userTable.getSelectedRow(), 0).toString();
                String password = userTable.getValueAt(userTable.getSelectedRow(), 1).toString();
                int roleno = Integer.parseInt(userTable.getValueAt(userTable.getSelectedRow(), 2).toString());
                
                String role = "";
                
                switch(roleno){
                    case(1):{
                        role = "Admin";
                        break;
                    }
                    case(2):{
                        role = "Lecturer";
                        break;
                    }
                    case(3):{
                        role="Student";
                        break;
                    }
                    case(4):{
                        role = "Project Manager";
                        break;
                    }
                }
                
                jTabbedPane1.setSelectedIndex(12);
                
                File imageFile = new File("src/Profile/" + id + ".jpg");
                if (imageFile.exists()) {
                    userNewAvatar1.setIcon(new ImageIcon(imageFile.getAbsolutePath()));
                }
                 else {
                    userNewAvatar1.setIcon(null);
                }
                
                userIDLabel.setText(id);
                userNewPasswordField.setText(password);
                roleNoLabel.setText(String.valueOf(roleno));
                roleLabel.setText(role);
            }

            @Override
            public void onDelete(int row) {
                if(userTable.isEditing()){
                    userTable.getCellEditor().stopCellEditing();
                }

                int decision = JOptionPane.showConfirmDialog(null, "Are you sure to remove this user? \n This action is irreversible"
                                   ,"Alert", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(decision == JOptionPane.YES_OPTION){
                    String id = userTable.getValueAt(userTable.getSelectedRow(), 0).toString();
                    
                    usertbl.removeRow(row);
                    User delete = new User(id);
                    delete.deleteUser();
                    Icon icon = new ImageIcon(getClass().getResource("/Icon/success.png"));
                    JOptionPane.showMessageDialog(null, "User has been removed.", "Notification", JOptionPane.INFORMATION_MESSAGE, icon);
                    
                    displayUserCount();
                }
                else if(decision == JOptionPane.NO_OPTION){
                    Icon icon = new ImageIcon(getClass().getResource("/Icon/shield.png"));
                    JOptionPane.showMessageDialog(null, "No changes has been made.", "Notification", JOptionPane.INFORMATION_MESSAGE,icon);                    
                }
            }
        };
        
        //Edit the action column by adjusting the size
        studentJTable.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        studentJTable.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(studentActionEvent));
        
        lecturerTable.getColumnModel().getColumn(8).setCellRenderer(new TableActionCellRender());
        lecturerTable.getColumnModel().getColumn(8).setCellEditor(new TableActionCellEditor(lecturerActionEvent));
    
        userTable.getColumnModel().getColumn(3).setCellRenderer(new TableActionCellRender());
        userTable.getColumnModel().getColumn(3).setCellEditor(new TableActionCellEditor(userActionEvent));
    }
    
    
    private void addPlaceholder(JTextField textField){
        Font font = textField.getFont();
        font = font.deriveFont(Font.ITALIC);
        textField.setFont(font);
        textField.setForeground(Color.GRAY);
    }
    
    private void removePlaceholder(JTextField textField){
        Font font = textField.getFont();
        font = font.deriveFont(Font.PLAIN);
        textField.setFont(font);
        textField.setForeground(Color.BLACK);
    }       
    
    
    private void displayUserCount(){
        FileHandler fh = new FileHandler();
        String totalLecturer = String.valueOf(fh.countTotalLecturer());
        String totalStudent = String.valueOf(fh.countTotalStudent());
        String totalIntake = String.valueOf(fh.countTotalIntake());
        
        cardPanel1.setData(new CardModel(new ImageIcon(getClass().getResource("/Icon/lecturer.png")), "Lecturer", totalLecturer));
        cardPanel2.setData(new CardModel(new ImageIcon(getClass().getResource("/Icon/student.png")), "Student", totalStudent));
        cardPanel3.setData(new CardModel(new ImageIcon(getClass().getResource("/Icon/intake.png")), "Intake", totalIntake));
        
    }
    
    private void displayStudentTable(){
        DefaultTableModel table = (DefaultTableModel) studentJTable.getModel();
        table.setRowCount(0);            
        Student show = new Student(table);
        show.showStudent();
        
        TableAlignment alignment = new TableAlignment();
        alignment.alignTable(studentJTable);
    }
    
    private void displayLecturerTable(){
        DefaultTableModel table = (DefaultTableModel) lecturerTable.getModel();
        table.setRowCount(0);            
        Lecturer showLC = new Lecturer(table);
        showLC.showLecturer();
        
        TableAlignment alignment = new TableAlignment();
        alignment.alignTable(lecturerTable);
    }
    
    private void displayIntakeTable(){
        DefaultTableModel table = (DefaultTableModel) intakeTable.getModel();
        table.setRowCount(0);
        Intake intake = new Intake(table);
        intake.showIntake();
        
        TableAlignment alignment = new TableAlignment();
        alignment.alignTable(intakeTable);
    }
    
    private void displayUserTable(){
        DefaultTableModel table = (DefaultTableModel) userTable.getModel();
        table.setRowCount(0);
        User user = new User(table);
        user.showUser();
        
        TableAlignment alignment = new TableAlignment();
        alignment.alignTable(userTable);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roleGroup = new javax.swing.ButtonGroup();
        yesNoGroup = new javax.swing.ButtonGroup();
        genderGroup = new javax.swing.ButtonGroup();
        header = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        adminProfile = new Admin.SetUserProfile();
        menubar = new javax.swing.JPanel();
        menu1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        exitLabel = new javax.swing.JLabel();
        menu2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        menu3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        menu4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        menu5 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        dashboard = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        chart = new ChartFrame.Chart();
        cardPanel1 = new Swing.CardPanel();
        cardPanel2 = new Swing.CardPanel();
        cardPanel3 = new Swing.CardPanel();
        student = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        studentPageSearchField = new javax.swing.JTextField();
        studentPageFilterBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        printNameListBtn = new javax.swing.JButton();
        addStudentButton = new javax.swing.JButton();
        studentTablePanel = new javax.swing.JPanel();
        studentScrollPane = new javax.swing.JScrollPane();
        studentJTable = new javax.swing.JTable();
        addStudentByIntakeButton = new javax.swing.JButton();
        registerStudent = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        icField = new javax.swing.JTextField();
        addStudentBtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        contactField = new javax.swing.JTextField();
        intakeCodeComboBox = new javax.swing.JComboBox<>();
        jLabel47 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        studentPicTextField = new javax.swing.JTextField();
        uploadStudentPicBtn = new javax.swing.JButton();
        studentRegisterPic = new Admin.SetUserProfile();
        registerStudentByIntake = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        addStudentFromListBtn = new javax.swing.JButton();
        loadStudentIntoTableButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        studentListTable = new javax.swing.JTable();
        intakeForGroupComboBox = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        editStudent = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        studentIDLabel = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        studentNewContactField = new javax.swing.JTextField();
        studentnNameLabel = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        updateStudentBtn = new javax.swing.JButton();
        cancelEditStudentBtn = new javax.swing.JButton();
        newIntakeCodeComboBox = new javax.swing.JComboBox<>();
        studentICLabel = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        studentEmailLabel = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        newStudentProfileTextField = new javax.swing.JTextField();
        uploadStudentNewProfileBtn = new javax.swing.JButton();
        studentNewAvatar1 = new Admin.SetUserProfile();
        lecturer = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lecturerTable = new javax.swing.JTable();
        lecturePageSearchField = new javax.swing.JTextField();
        addLecturerByGtoupButton = new javax.swing.JButton();
        addLecturerButton = new javax.swing.JButton();
        registerLecturer = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        lcNameField = new javax.swing.JTextField();
        lecICField = new javax.swing.JTextField();
        lecContactField = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        yesCheckBox = new javax.swing.JCheckBox();
        noCheckBox = new javax.swing.JCheckBox();
        addLecturerBtn = new javax.swing.JButton();
        cancelButton2 = new javax.swing.JButton();
        lecMajorComboBox = new javax.swing.JComboBox<>();
        jLabel50 = new javax.swing.JLabel();
        lecMinorComboBox = new javax.swing.JComboBox<>();
        updateLecturerPictureBtn = new javax.swing.JButton();
        lectureProfileName = new javax.swing.JLabel();
        setLecturerRegisterImage = new Admin.SetUserProfile();
        registerLecturerByGroup = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        addLecturerFromListBtn = new javax.swing.JButton();
        loadLecturerIntoTableButton = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        lectureListTable = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        editLecturer = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        lcNameLabel = new javax.swing.JLabel();
        lectureIDLabel = new javax.swing.JLabel();
        lcEmailLabel = new javax.swing.JLabel();
        lcNewContactField = new javax.swing.JTextField();
        lcICLabel1 = new javax.swing.JLabel();
        updateLecBtn = new javax.swing.JButton();
        clearLecDetails = new javax.swing.JButton();
        newYesCheckBox = new javax.swing.JCheckBox();
        newNoCheckBox = new javax.swing.JCheckBox();
        lecNewMajorComboBox = new javax.swing.JComboBox<>();
        jLabel51 = new javax.swing.JLabel();
        lecNewMinorComboBox = new javax.swing.JComboBox<>();
        uploadLecturerNewProfileBtn = new javax.swing.JButton();
        lecturerAvatarNew = new Admin.SetUserProfile();
        intake = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        intakeTable = new javax.swing.JTable();
        addIntakeButton = new javax.swing.JLabel();
        intakePageSearchField = new javax.swing.JTextField();
        addIntake = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        saveIntakeBtn = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        intakeDurationComboBox = new javax.swing.JComboBox<>();
        courseComboBox = new javax.swing.JComboBox<>();
        studyLevelComboBox = new javax.swing.JComboBox<>();
        intakeRegisterStartDateChooser = new com.toedter.calendar.JDateChooser();
        intakeRegisterEndDateChooser = new com.toedter.calendar.JDateChooser();
        intakeStartDateChooser = new com.toedter.calendar.JDateChooser();
        intakeEndDateChooser = new com.toedter.calendar.JDateChooser();
        userPage = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        userPageSearchField = new javax.swing.JTextField();
        editUser = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        roleNoLabel = new javax.swing.JLabel();
        userNewPasswordField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        userIDLabel = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        roleLabel = new javax.swing.JLabel();
        userNewAvatar1 = new Admin.SetUserProfile();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(870, 600));
        setSize(new java.awt.Dimension(870, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        header.setBackground(new java.awt.Color(209, 211, 199));

        jLabel5.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jLabel5.setText("GoOdBrAiN");

        jLabel6.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jLabel6.setText("Admin");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(headerLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(adminProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(229, 229, 229)
                        .addComponent(jLabel5)))
                .addContainerGap(427, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)))
                    .addGroup(headerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(adminProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(89, 89, 89)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 60));

        menubar.setBackground(new java.awt.Color(172, 190, 174));

        menu1.setBackground(new java.awt.Color(172, 190, 174));
        menu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu1MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jLabel3.setText("Dashboard");

        javax.swing.GroupLayout menu1Layout = new javax.swing.GroupLayout(menu1);
        menu1.setLayout(menu1Layout);
        menu1Layout.setHorizontalGroup(
            menu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        menu1Layout.setVerticalGroup(
            menu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        exitLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/logout.png"))); // NOI18N
        exitLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitLabelMouseClicked(evt);
            }
        });

        menu2.setBackground(new java.awt.Color(172, 190, 174));
        menu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menu2MouseEntered(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jLabel7.setText("Student");

        javax.swing.GroupLayout menu2Layout = new javax.swing.GroupLayout(menu2);
        menu2.setLayout(menu2Layout);
        menu2Layout.setHorizontalGroup(
            menu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel7)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        menu2Layout.setVerticalGroup(
            menu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menu3.setBackground(new java.awt.Color(172, 190, 174));
        menu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu3MouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jLabel10.setText("Lecturer");

        javax.swing.GroupLayout menu3Layout = new javax.swing.GroupLayout(menu3);
        menu3.setLayout(menu3Layout);
        menu3Layout.setHorizontalGroup(
            menu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel10)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        menu3Layout.setVerticalGroup(
            menu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menu4.setBackground(new java.awt.Color(172, 190, 174));
        menu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu4MouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jLabel16.setText("Intake");

        javax.swing.GroupLayout menu4Layout = new javax.swing.GroupLayout(menu4);
        menu4.setLayout(menu4Layout);
        menu4Layout.setHorizontalGroup(
            menu4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel16)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        menu4Layout.setVerticalGroup(
            menu4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menu5.setBackground(new java.awt.Color(172, 190, 174));
        menu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu5MouseClicked(evt);
            }
        });

        jLabel58.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jLabel58.setText("User");

        javax.swing.GroupLayout menu5Layout = new javax.swing.GroupLayout(menu5);
        menu5.setLayout(menu5Layout);
        menu5Layout.setHorizontalGroup(
            menu5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel58)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        menu5Layout.setVerticalGroup(
            menu5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout menubarLayout = new javax.swing.GroupLayout(menubar);
        menubar.setLayout(menubarLayout);
        menubarLayout.setHorizontalGroup(
            menubarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menubarLayout.createSequentialGroup()
                .addContainerGap(204, Short.MAX_VALUE)
                .addComponent(menu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(menu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(menu3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(menu4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(menu5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(exitLabel)
                .addContainerGap())
        );
        menubarLayout.setVerticalGroup(
            menubarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menubarLayout.createSequentialGroup()
                .addGroup(menubarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(menubarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(exitLabel))
                    .addComponent(menu3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menu4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menu5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menu2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(menubar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 860, 40));

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(865, 500));

        dashboard.setBackground(new java.awt.Color(239, 240, 234));

        jLabel1.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel1.setText("Dashboard");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout dashboardLayout = new javax.swing.GroupLayout(dashboard);
        dashboard.setLayout(dashboardLayout);
        dashboardLayout.setHorizontalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardLayout.createSequentialGroup()
                        .addComponent(cardPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(cardPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(cardPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dashboardLayout.createSequentialGroup()
                        .addGap(405, 405, 405)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        dashboardLayout.setVerticalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cardPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );

        jTabbedPane1.addTab("tab1", dashboard);

        student.setBackground(new java.awt.Color(239, 240, 234));

        jLabel4.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel4.setText("Student");

        studentPageSearchField.setText("Search here");
        studentPageSearchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                studentPageSearchFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                studentPageSearchFieldFocusLost(evt);
            }
        });
        studentPageSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                studentPageSearchFieldKeyReleased(evt);
            }
        });

        studentPageFilterBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentPageFilterBoxActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/search.png"))); // NOI18N

        printNameListBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/printer.png"))); // NOI18N
        printNameListBtn.setContentAreaFilled(false);
        printNameListBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printNameListBtnActionPerformed(evt);
            }
        });

        addStudentButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/addstudent.png"))); // NOI18N
        addStudentButton.setContentAreaFilled(false);
        addStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentButtonActionPerformed(evt);
            }
        });

        studentTablePanel.setLayout(new java.awt.BorderLayout());

        studentJTable.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        studentJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Name", "IC Number", "Contact", "Email", "Intake Code", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        studentJTable.setRowHeight(40);
        studentJTable.setSelectionBackground(new java.awt.Color(143, 199, 163));
        studentScrollPane.setViewportView(studentJTable);
        if (studentJTable.getColumnModel().getColumnCount() > 0) {
            studentJTable.getColumnModel().getColumn(0).setPreferredWidth(40);
            studentJTable.getColumnModel().getColumn(5).setPreferredWidth(50);
            studentJTable.getColumnModel().getColumn(6).setPreferredWidth(35);
        }

        studentTablePanel.add(studentScrollPane, java.awt.BorderLayout.CENTER);

        addStudentByIntakeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/addStudentGroup.png"))); // NOI18N
        addStudentByIntakeButton.setContentAreaFilled(false);
        addStudentByIntakeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentByIntakeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout studentLayout = new javax.swing.GroupLayout(student);
        student.setLayout(studentLayout);
        studentLayout.setHorizontalGroup(
            studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentLayout.createSequentialGroup()
                .addGap(387, 387, 387)
                .addComponent(jLabel4)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(studentLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(studentTablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 794, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(studentLayout.createSequentialGroup()
                        .addComponent(studentPageFilterBox, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(studentPageSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addGap(84, 84, 84)
                        .addComponent(printNameListBtn)
                        .addGap(18, 18, 18)
                        .addComponent(addStudentButton)
                        .addGap(18, 18, 18)
                        .addComponent(addStudentByIntakeButton)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        studentLayout.setVerticalGroup(
            studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentLayout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addComponent(jLabel4)
                .addGap(40, 40, 40)
                .addGroup(studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentLayout.createSequentialGroup()
                        .addGroup(studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addStudentButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(printNameListBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(studentLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(studentPageSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(studentPageFilterBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel2))))
                        .addGap(18, 18, 18)
                        .addComponent(studentTablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(addStudentByIntakeButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", student);

        registerStudent.setBackground(new java.awt.Color(239, 240, 234));

        jLabel12.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel12.setText("Student Registration (Individual)");

        jLabel13.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel13.setText("Name : ");

        jLabel14.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel14.setText("IC Number :");

        addStudentBtn.setText("Add");
        addStudentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentBtnActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel17.setText("Contact :");

        jLabel47.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel47.setText("Intake Code :");

        jLabel56.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel56.setText("Profile Picture :");

        uploadStudentPicBtn.setText("Upload");
        uploadStudentPicBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadStudentPicBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout registerStudentLayout = new javax.swing.GroupLayout(registerStudent);
        registerStudent.setLayout(registerStudentLayout);
        registerStudentLayout.setHorizontalGroup(
            registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerStudentLayout.createSequentialGroup()
                .addContainerGap(226, Short.MAX_VALUE)
                .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel56)
                    .addComponent(jLabel14)
                    .addGroup(registerStudentLayout.createSequentialGroup()
                        .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel47)
                            .addComponent(jLabel13))
                        .addGap(49, 49, 49)
                        .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(registerStudentLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(icField, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(registerStudentLayout.createSequentialGroup()
                                        .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(registerStudentLayout.createSequentialGroup()
                                                .addComponent(jButton2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(addStudentBtn))
                                            .addComponent(contactField, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(intakeCodeComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(studentPicTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addComponent(uploadStudentPicBtn))))
                            .addComponent(jLabel12))))
                .addGap(183, 183, 183))
            .addGroup(registerStudentLayout.createSequentialGroup()
                .addGap(351, 351, 351)
                .addComponent(studentRegisterPic, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(342, Short.MAX_VALUE))
        );
        registerStudentLayout.setVerticalGroup(
            registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerStudentLayout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(studentRegisterPic, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(icField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(contactField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(intakeCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(studentPicTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uploadStudentPicBtn))
                .addGap(18, 18, 18)
                .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addStudentBtn)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", registerStudent);

        registerStudentByIntake.setBackground(new java.awt.Color(239, 240, 234));

        jLabel59.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel59.setText("Student Registration (Group)");

        addStudentFromListBtn.setText("Add");
        addStudentFromListBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentFromListBtnActionPerformed(evt);
            }
        });

        loadStudentIntoTableButton.setText("Load");
        loadStudentIntoTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadStudentIntoTableButtonActionPerformed(evt);
            }
        });

        studentListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "IC", "Contact"
            }
        ));
        studentListTable.setRowHeight(25);
        jScrollPane1.setViewportView(studentListTable);

        jButton5.setText("Cancel");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout registerStudentByIntakeLayout = new javax.swing.GroupLayout(registerStudentByIntake);
        registerStudentByIntake.setLayout(registerStudentByIntakeLayout);
        registerStudentByIntakeLayout.setHorizontalGroup(
            registerStudentByIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerStudentByIntakeLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(registerStudentByIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(registerStudentByIntakeLayout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addStudentFromListBtn))
                    .addGroup(registerStudentByIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(registerStudentByIntakeLayout.createSequentialGroup()
                            .addComponent(intakeForGroupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(loadStudentIntoTableButton))))
                .addContainerGap(72, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, registerStudentByIntakeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel59)
                .addGap(328, 328, 328))
        );
        registerStudentByIntakeLayout.setVerticalGroup(
            registerStudentByIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerStudentByIntakeLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jLabel59)
                .addGap(18, 18, 18)
                .addGroup(registerStudentByIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intakeForGroupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loadStudentIntoTableButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(registerStudentByIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addStudentFromListBtn)
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", registerStudentByIntake);

        editStudent.setBackground(new java.awt.Color(239, 240, 234));

        jLabel18.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel18.setText("Student Details");

        jLabel19.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel19.setText("Student ID:");

        jLabel20.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel20.setText("Name:");

        jLabel21.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel21.setText("IC Number:");

        studentIDLabel.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel23.setText("Contact:");

        studentnNameLabel.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel33.setText("Intake Code:");

        updateStudentBtn.setText("Save");
        updateStudentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateStudentBtnActionPerformed(evt);
            }
        });

        cancelEditStudentBtn.setText("Cancel");
        cancelEditStudentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelEditStudentBtnActionPerformed(evt);
            }
        });

        studentICLabel.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel22.setText("Email:");

        studentEmailLabel.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N

        jLabel57.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel57.setText("New Profile ");

        uploadStudentNewProfileBtn.setText("Upload");
        uploadStudentNewProfileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadStudentNewProfileBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout editStudentLayout = new javax.swing.GroupLayout(editStudent);
        editStudent.setLayout(editStudentLayout);
        editStudentLayout.setHorizontalGroup(
            editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editStudentLayout.createSequentialGroup()
                .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(editStudentLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancelEditStudentBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateStudentBtn))
                    .addGroup(editStudentLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(editStudentLayout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                                .addComponent(studentNewContactField, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(editStudentLayout.createSequentialGroup()
                                .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel21))
                                .addGap(38, 38, 38)
                                .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(studentnNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                                    .addComponent(studentICLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(studentIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(62, 62, 62)
                        .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(editStudentLayout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(studentEmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(editStudentLayout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(newIntakeCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(editStudentLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel57)
                                .addGap(26, 26, 26)
                                .addComponent(newStudentProfileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(114, Short.MAX_VALUE))
            .addGroup(editStudentLayout.createSequentialGroup()
                .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editStudentLayout.createSequentialGroup()
                        .addGap(366, 366, 366)
                        .addComponent(jLabel18))
                    .addGroup(editStudentLayout.createSequentialGroup()
                        .addGap(333, 333, 333)
                        .addComponent(studentNewAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(uploadStudentNewProfileBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        editStudentLayout.setVerticalGroup(
            editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editStudentLayout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(uploadStudentNewProfileBtn)
                    .addComponent(studentNewAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editStudentLayout.createSequentialGroup()
                        .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(editStudentLayout.createSequentialGroup()
                                .addComponent(studentIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(studentnNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, editStudentLayout.createSequentialGroup()
                                .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel22)
                                    .addComponent(studentEmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel33)
                                    .addComponent(newIntakeCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(studentICLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel57)
                                .addComponent(newStudentProfileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(40, 40, 40))
                    .addGroup(editStudentLayout.createSequentialGroup()
                        .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(editStudentLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel20))
                            .addComponent(jLabel19))
                        .addGap(22, 22, 22)
                        .addComponent(jLabel21)
                        .addGap(18, 18, 18)
                        .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(studentNewContactField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(5, 5, 5)
                .addGroup(editStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateStudentBtn)
                    .addComponent(cancelEditStudentBtn))
                .addContainerGap(159, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab5", editStudent);

        lecturer.setBackground(new java.awt.Color(239, 240, 234));

        jLabel36.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel36.setText("Lecturer");

        lecturerTable.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        lecturerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Lecturer ID", "Name", "IC Number", "Contact", "Email", "Major Study", "Minor Study", "Project Manager", "Action"
            }
        ));
        lecturerTable.setRowHeight(40);
        lecturerTable.setSelectionBackground(new java.awt.Color(143, 199, 163));
        jScrollPane2.setViewportView(lecturerTable);
        if (lecturerTable.getColumnModel().getColumnCount() > 0) {
            lecturerTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            lecturerTable.getColumnModel().getColumn(8).setPreferredWidth(50);
        }

        lecturePageSearchField.setText("Search here");
        lecturePageSearchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lecturePageSearchFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lecturePageSearchFieldFocusLost(evt);
            }
        });
        lecturePageSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lecturePageSearchFieldKeyReleased(evt);
            }
        });

        addLecturerByGtoupButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/addLecturerGroup.png"))); // NOI18N
        addLecturerByGtoupButton.setContentAreaFilled(false);
        addLecturerByGtoupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLecturerByGtoupButtonActionPerformed(evt);
            }
        });

        addLecturerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/addLecturer.png"))); // NOI18N
        addLecturerButton.setContentAreaFilled(false);
        addLecturerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLecturerButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout lecturerLayout = new javax.swing.GroupLayout(lecturer);
        lecturer.setLayout(lecturerLayout);
        lecturerLayout.setHorizontalGroup(
            lecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lecturerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lecturePageSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(addLecturerButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addLecturerByGtoupButton)
                .addGap(67, 67, 67))
            .addGroup(lecturerLayout.createSequentialGroup()
                .addGroup(lecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lecturerLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 819, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(lecturerLayout.createSequentialGroup()
                        .addGap(392, 392, 392)
                        .addComponent(jLabel36)))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        lecturerLayout.setVerticalGroup(
            lecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lecturerLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel36)
                .addGap(31, 31, 31)
                .addGroup(lecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addLecturerByGtoupButton, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(addLecturerButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(lecturerLayout.createSequentialGroup()
                            .addComponent(lecturePageSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(134, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab3", lecturer);

        registerLecturer.setBackground(new java.awt.Color(239, 240, 234));

        jLabel24.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel24.setText("Lecturer Registration");

        jLabel25.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel25.setText("Name:");

        jLabel26.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel26.setText("IC Number :");

        jLabel30.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel30.setText("Contact:");

        jLabel31.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel31.setText("Major Functional Area:");

        jLabel32.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel32.setText("Project Manager:");

        yesNoGroup.add(yesCheckBox);
        yesCheckBox.setText("Yes");

        yesNoGroup.add(noCheckBox);
        noCheckBox.setText("No");

        addLecturerBtn.setText("Confirm");
        addLecturerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLecturerBtnActionPerformed(evt);
            }
        });

        cancelButton2.setText("Cancel");
        cancelButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButton2ActionPerformed(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel50.setText("Minor Functional Area:");

        updateLecturerPictureBtn.setText("Upload");
        updateLecturerPictureBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateLecturerPictureBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout registerLecturerLayout = new javax.swing.GroupLayout(registerLecturer);
        registerLecturer.setLayout(registerLecturerLayout);
        registerLecturerLayout.setHorizontalGroup(
            registerLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerLecturerLayout.createSequentialGroup()
                .addGap(339, 339, 339)
                .addComponent(jLabel24)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(registerLecturerLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(registerLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(registerLecturerLayout.createSequentialGroup()
                        .addGroup(registerLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(registerLecturerLayout.createSequentialGroup()
                                .addGroup(registerLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel30))
                                .addGap(18, 18, 18)
                                .addGroup(registerLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lecContactField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lecICField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lcNameField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(48, 48, 48)
                                .addGroup(registerLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel50))
                                .addGap(18, 18, 18))
                            .addGroup(registerLecturerLayout.createSequentialGroup()
                                .addGap(231, 231, 231)
                                .addComponent(setLecturerRegisterImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(83, 83, 83)))
                        .addGroup(registerLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(registerLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lecMajorComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lecMinorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(registerLecturerLayout.createSequentialGroup()
                                .addComponent(yesCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(noCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(registerLecturerLayout.createSequentialGroup()
                                .addComponent(updateLecturerPictureBtn)
                                .addGap(18, 18, 18)
                                .addComponent(lectureProfileName, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11))
                    .addGroup(registerLecturerLayout.createSequentialGroup()
                        .addComponent(cancelButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addLecturerBtn)))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        registerLecturerLayout.setVerticalGroup(
            registerLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerLecturerLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(registerLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(registerLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(updateLecturerPictureBtn)
                        .addComponent(lectureProfileName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(setLecturerRegisterImage, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(registerLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(registerLecturerLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(23, 23, 23)
                        .addComponent(jLabel50)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel32))
                    .addGroup(registerLecturerLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30))
                    .addGroup(registerLecturerLayout.createSequentialGroup()
                        .addComponent(lcNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lecICField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lecContactField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(registerLecturerLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lecMajorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lecMinorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(registerLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(yesCheckBox)
                            .addComponent(noCheckBox))))
                .addGap(18, 18, 18)
                .addGroup(registerLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addLecturerBtn)
                    .addComponent(cancelButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab6", registerLecturer);

        registerLecturerByGroup.setBackground(new java.awt.Color(239, 240, 234));

        jLabel67.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel67.setText("Lecturer Registration (Group)");

        addLecturerFromListBtn.setText("Add");
        addLecturerFromListBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLecturerFromListBtnActionPerformed(evt);
            }
        });

        loadLecturerIntoTableButton.setText("Load");
        loadLecturerIntoTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadLecturerIntoTableButtonActionPerformed(evt);
            }
        });

        lectureListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "IC", "Contact", "Major Area", "Minor Area"
            }
        ));
        lectureListTable.setRowHeight(25);
        jScrollPane5.setViewportView(lectureListTable);

        jButton6.setText("Cancel");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout registerLecturerByGroupLayout = new javax.swing.GroupLayout(registerLecturerByGroup);
        registerLecturerByGroup.setLayout(registerLecturerByGroupLayout);
        registerLecturerByGroupLayout.setHorizontalGroup(
            registerLecturerByGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, registerLecturerByGroupLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel67)
                .addGap(328, 328, 328))
            .addGroup(registerLecturerByGroupLayout.createSequentialGroup()
                .addGroup(registerLecturerByGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(registerLecturerByGroupLayout.createSequentialGroup()
                        .addGap(654, 654, 654)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addLecturerFromListBtn))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, registerLecturerByGroupLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(registerLecturerByGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(loadLecturerIntoTableButton)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 801, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        registerLecturerByGroupLayout.setVerticalGroup(
            registerLecturerByGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerLecturerByGroupLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jLabel67)
                .addGap(24, 24, 24)
                .addComponent(loadLecturerIntoTableButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(registerLecturerByGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addLecturerFromListBtn)
                    .addComponent(jButton6))
                .addContainerGap(122, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", registerLecturerByGroup);

        editLecturer.setBackground(new java.awt.Color(239, 240, 234));

        jLabel35.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel35.setText("Lecturer Details");

        jLabel15.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel15.setText("Lecture ID :");

        jLabel27.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel27.setText("Name :");

        jLabel28.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel28.setText("IC :");

        jLabel29.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel29.setText("Contact :");

        jLabel37.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel37.setText("Email :");

        jLabel48.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel48.setText("Major Functional Area :");

        jLabel49.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel49.setText("Project Manager :");

        lcNameLabel.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N

        lectureIDLabel.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N

        lcEmailLabel.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N

        lcICLabel1.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N

        updateLecBtn.setText("Save");
        updateLecBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateLecBtnActionPerformed(evt);
            }
        });

        clearLecDetails.setText("Cancel");
        clearLecDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearLecDetailsActionPerformed(evt);
            }
        });

        yesNoGroup.add(newYesCheckBox);
        newYesCheckBox.setText("Yes");

        yesNoGroup.add(newNoCheckBox);
        newNoCheckBox.setText("No");

        jLabel51.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel51.setText("Minor Functional Area :");

        uploadLecturerNewProfileBtn.setText("Upload");
        uploadLecturerNewProfileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadLecturerNewProfileBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout editLecturerLayout = new javax.swing.GroupLayout(editLecturer);
        editLecturer.setLayout(editLecturerLayout);
        editLecturerLayout.setHorizontalGroup(
            editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editLecturerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lecturerAvatarNew, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(uploadLecturerNewProfileBtn)
                .addGap(295, 295, 295))
            .addGroup(editLecturerLayout.createSequentialGroup()
                .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editLecturerLayout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29))
                        .addGap(32, 32, 32)
                        .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lcNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lectureIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lcNewContactField)
                            .addComponent(lcICLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editLecturerLayout.createSequentialGroup()
                                .addComponent(jLabel49)
                                .addGap(63, 63, 63))
                            .addGroup(editLecturerLayout.createSequentialGroup()
                                .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel48)
                                    .addComponent(jLabel51)
                                    .addComponent(jLabel37))
                                .addGap(27, 27, 27)))
                        .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lcEmailLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(editLecturerLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(editLecturerLayout.createSequentialGroup()
                                        .addComponent(newYesCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(newNoCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(editLecturerLayout.createSequentialGroup()
                                            .addComponent(clearLecDetails)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(updateLecBtn))
                                        .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lecNewMinorComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lecNewMajorComboBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                    .addGroup(editLecturerLayout.createSequentialGroup()
                        .addGap(355, 355, 355)
                        .addComponent(jLabel35)))
                .addContainerGap(122, Short.MAX_VALUE))
        );
        editLecturerLayout.setVerticalGroup(
            editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editLecturerLayout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(uploadLecturerNewProfileBtn)
                    .addComponent(lecturerAvatarNew, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(editLecturerLayout.createSequentialGroup()
                            .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel37)
                                .addComponent(lcEmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel48)
                                .addComponent(lecNewMajorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel51)
                                .addComponent(lecNewMinorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel49)
                                .addComponent(newYesCheckBox)
                                .addComponent(newNoCheckBox)))
                        .addGroup(editLecturerLayout.createSequentialGroup()
                            .addComponent(jLabel15)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel27)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel28)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel29)))
                    .addGroup(editLecturerLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(lectureIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lcNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lcICLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lcNewContactField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(editLecturerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateLecBtn)
                    .addComponent(clearLecDetails))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab7", editLecturer);

        intake.setBackground(new java.awt.Color(239, 240, 234));

        jLabel38.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel38.setText("Intake");

        intakeTable.setFont(new java.awt.Font("Serif", 0, 13)); // NOI18N
        intakeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Intake Code", "Study Level", "Course", "Duration", "Registration Start Date", "Registration End Date", "Start Date", "End Date"
            }
        ));
        intakeTable.setRowHeight(40);
        intakeTable.setSelectionBackground(new java.awt.Color(143, 199, 163));
        jScrollPane3.setViewportView(intakeTable);
        if (intakeTable.getColumnModel().getColumnCount() > 0) {
            intakeTable.getColumnModel().getColumn(0).setPreferredWidth(60);
            intakeTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        addIntakeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/addIntake.png"))); // NOI18N
        addIntakeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addIntakeButtonMouseClicked(evt);
            }
        });

        intakePageSearchField.setText("Search here");
        intakePageSearchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                intakePageSearchFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                intakePageSearchFieldFocusLost(evt);
            }
        });
        intakePageSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                intakePageSearchFieldKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout intakeLayout = new javax.swing.GroupLayout(intake);
        intake.setLayout(intakeLayout);
        intakeLayout.setHorizontalGroup(
            intakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, intakeLayout.createSequentialGroup()
                .addContainerGap(281, Short.MAX_VALUE)
                .addComponent(intakePageSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(addIntakeButton)
                .addGap(164, 164, 164))
            .addGroup(intakeLayout.createSequentialGroup()
                .addGroup(intakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(intakeLayout.createSequentialGroup()
                        .addGap(378, 378, 378)
                        .addComponent(jLabel38))
                    .addGroup(intakeLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        intakeLayout.setVerticalGroup(
            intakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(intakeLayout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(jLabel38)
                .addGap(31, 31, 31)
                .addGroup(intakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addIntakeButton)
                    .addComponent(intakePageSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab8", intake);

        addIntake.setBackground(new java.awt.Color(239, 240, 234));

        jLabel39.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel39.setText("New Intake");

        jLabel40.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel40.setText("Study Level :");

        jLabel41.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel41.setText("Intake Duration (Months) :");

        jLabel42.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel42.setText("Intake Registration Opening Date :");

        jLabel43.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel43.setText("Intake Registration Closing Date :");

        jLabel44.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel44.setText("Intake  Starting Date :");

        jLabel45.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel45.setText("Intake  Ending Date :");

        jLabel46.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel46.setText("Course:");

        saveIntakeBtn.setText("Save");
        saveIntakeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveIntakeBtnActionPerformed(evt);
            }
        });

        jButton3.setText("Cancel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        intakeDurationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "12", "24", "36" }));

        courseComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Accounting", "Banking", "Cloud Engineering", "Cyber Security", "Data Analytics", "Design", "Digital Forensic", "Digital Marketing", "Engineering", "Finance", "FinTech", "Intelligent System", "Software Engineering", "Psychology" }));

        studyLevelComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Foundation", "Diploma", "Degree", "Master" }));

        javax.swing.GroupLayout addIntakeLayout = new javax.swing.GroupLayout(addIntake);
        addIntake.setLayout(addIntakeLayout);
        addIntakeLayout.setHorizontalGroup(
            addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addIntakeLayout.createSequentialGroup()
                .addGroup(addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addIntakeLayout.createSequentialGroup()
                        .addGap(328, 328, 328)
                        .addComponent(jLabel39))
                    .addGroup(addIntakeLayout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addGroup(addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(addIntakeLayout.createSequentialGroup()
                                .addGroup(addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel43)
                                    .addComponent(jLabel44)
                                    .addComponent(jLabel45))
                                .addGroup(addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(addIntakeLayout.createSequentialGroup()
                                        .addGap(157, 157, 157)
                                        .addComponent(jButton3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(saveIntakeBtn))
                                    .addGroup(addIntakeLayout.createSequentialGroup()
                                        .addGap(69, 69, 69)
                                        .addGroup(addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(intakeRegisterEndDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(intakeStartDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(intakeEndDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(2, 2, 2))
                            .addGroup(addIntakeLayout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(63, 63, 63)
                                .addGroup(addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(intakeDurationComboBox, 0, 240, Short.MAX_VALUE)
                                    .addComponent(intakeRegisterStartDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(addIntakeLayout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addGroup(addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(addIntakeLayout.createSequentialGroup()
                                .addComponent(jLabel46)
                                .addGap(225, 225, 225)
                                .addComponent(courseComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addIntakeLayout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addGap(191, 191, 191)
                                .addComponent(studyLevelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel41))))
                .addContainerGap(248, Short.MAX_VALUE))
        );
        addIntakeLayout.setVerticalGroup(
            addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addIntakeLayout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addComponent(jLabel39)
                .addGap(32, 32, 32)
                .addGroup(addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(studyLevelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(courseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(intakeDurationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addComponent(intakeRegisterStartDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addComponent(intakeRegisterEndDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addComponent(intakeStartDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45)
                    .addComponent(intakeEndDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addIntakeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveIntakeBtn)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab9", addIntake);

        userPage.setBackground(new java.awt.Color(239, 240, 234));

        jLabel11.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel11.setText("User");

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User ID", "Password", "Role", "Action"
            }
        ));
        userTable.setRowHeight(40);
        userTable.setSelectionBackground(new java.awt.Color(143, 199, 163));
        jScrollPane4.setViewportView(userTable);

        userPageSearchField.setText("Search here");
        userPageSearchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                userPageSearchFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                userPageSearchFieldFocusLost(evt);
            }
        });
        userPageSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                userPageSearchFieldKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout userPageLayout = new javax.swing.GroupLayout(userPage);
        userPage.setLayout(userPageLayout);
        userPageLayout.setHorizontalGroup(
            userPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPageLayout.createSequentialGroup()
                .addGroup(userPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(userPageLayout.createSequentialGroup()
                        .addGap(401, 401, 401)
                        .addComponent(jLabel11))
                    .addGroup(userPageLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(userPageLayout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(userPageSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        userPageLayout.setVerticalGroup(
            userPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPageLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(32, 32, 32)
                .addComponent(userPageSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(154, 154, 154))
        );

        jTabbedPane1.addTab("tab10", userPage);

        editUser.setBackground(new java.awt.Color(239, 240, 234));

        jLabel34.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel34.setText("User ID:");

        jLabel52.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel52.setText("Password:");

        jLabel53.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel53.setText("Role No:");

        jLabel54.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel54.setText("User Details");

        roleNoLabel.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setText("Cancel");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        userIDLabel.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N

        jLabel55.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel55.setText("Role:");

        roleLabel.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N

        javax.swing.GroupLayout editUserLayout = new javax.swing.GroupLayout(editUser);
        editUser.setLayout(editUserLayout);
        editUserLayout.setHorizontalGroup(
            editUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editUserLayout.createSequentialGroup()
                .addContainerGap(274, Short.MAX_VALUE)
                .addGroup(editUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editUserLayout.createSequentialGroup()
                        .addGroup(editUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel52)
                            .addComponent(jLabel34)
                            .addComponent(jLabel53))
                        .addGroup(editUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(editUserLayout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addGroup(editUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(userNewPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editUserLayout.createSequentialGroup()
                                        .addGroup(editUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(roleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(roleNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(50, 50, 50))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editUserLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(userIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54))))
                    .addComponent(jLabel55)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editUserLayout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addGap(285, 285, 285))
            .addGroup(editUserLayout.createSequentialGroup()
                .addGroup(editUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editUserLayout.createSequentialGroup()
                        .addGap(373, 373, 373)
                        .addComponent(jLabel54))
                    .addGroup(editUserLayout.createSequentialGroup()
                        .addGap(364, 364, 364)
                        .addComponent(userNewAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        editUserLayout.setVerticalGroup(
            editUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editUserLayout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userNewAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addGroup(editUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(editUserLayout.createSequentialGroup()
                        .addGroup(editUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userNewPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel53))
                    .addComponent(roleNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(editUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel55)
                    .addComponent(roleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(editUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab11", editUser);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -57, 900, 710));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu2MouseClicked
        Intake intake = new Intake();
        intake.addIntakeCodeForFilter(studentPageFilterBox);
        jTabbedPane1.setSelectedIndex(1);
        menu2.setBackground(new Color(200, 219, 202));
        menu1.setBackground(new Color(172,190,174));
        menu3.setBackground(new Color(172,190,174));
        menu4.setBackground(new Color(172,190,174));
        menu5.setBackground(new Color(172,190,174));
        
        displayStudentTable();
    }//GEN-LAST:event_menu2MouseClicked

    private void menu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu3MouseClicked
        jTabbedPane1.setSelectedIndex(5);
        menu3.setBackground(new Color(200, 219, 202));
        menu1.setBackground(new Color(172,190,174));
        menu2.setBackground(new Color(172,190,174));
        menu4.setBackground(new Color(172,190,174));
        menu5.setBackground(new Color(172,190,174));

        displayLecturerTable();
    }//GEN-LAST:event_menu3MouseClicked

    private void menu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu1MouseClicked
        jTabbedPane1.setSelectedIndex(0);
        menu1.setBackground(new Color(200, 219, 202));
        menu2.setBackground(new Color(172,190,174));
        menu3.setBackground(new Color(172,190,174));
        menu4.setBackground(new Color(172,190,174));
        menu5.setBackground(new Color(172,190,174));
    }//GEN-LAST:event_menu1MouseClicked

    private void exitLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitLabelMouseClicked
        int result = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION){
           System.exit(0);
        }
        else if (result == JOptionPane.NO_OPTION){
            JOptionPane.showMessageDialog(null, "You may continue with your work.", "Notification", JOptionPane.INFORMATION_MESSAGE);
        }  
    }//GEN-LAST:event_exitLabelMouseClicked

    private void saveNewImage(String id) throws IOException {
        File sourceFile = openFileChooser.getSelectedFile();

        if (sourceFile != null) {
            File destFolder = new File("src/Profile/");
            File destFile = new File(destFolder, id + ".jpg");

            // Delete the old image file
            if (destFile.exists()) {
                destFile.delete();
            }

            Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            updateProfile(id);
        }
    }
    
    private void updateProfile(String id) {
        File imageFile = new File("src/Profile/" + id + ".jpg");

        if (imageFile.exists()) {
            String imagePath = imageFile.getAbsolutePath() + "?" + System.currentTimeMillis();
            if (id.startsWith("S")) {
                // Update student profile picture
                studentNewAvatar1.setIcon(new ImageIcon(imagePath));
            }
            else if (id.startsWith("L")) {
                // Update lecturer profile picture
                lecturerAvatarNew.setIcon(new ImageIcon(imagePath));
            }
            else {
                // Handle invalid ID or set a default image
                studentNewAvatar1.setIcon(null);
                lecturerAvatarNew.setIcon(null);
            }
        } else {
            // Handle the case where the image file doesn't exist
            if (id.startsWith("S")) {
                studentNewAvatar1.setIcon(null); // or set a default student image
            } else if (id.startsWith("L")) {
                lecturerAvatarNew.setIcon(null); // or set a default lecturer image
            }
        }
    }


    private void addStudentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentBtnActionPerformed
        String name = nameField.getText().trim();
        String ic = icField.getText();
        String contact = contactField.getText();        
        String intake = (String) intakeCodeComboBox.getSelectedItem();
        
        //Validate data entered
        Student validate = new Student(name, ic, contact);
        List<String> validationErrors = validate.studentValidation();
        String errorMesssage = "";
        
        if (!validationErrors.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("The following errors are detected:\n");
            for (String error : validationErrors) {
                errorMessage.append("- ").append(error).append("\n");
            }
            JOptionPane.showMessageDialog(null, errorMessage.toString(), "Errors Found", JOptionPane.ERROR_MESSAGE);
            clearField();           
        }
        else {
            Student newStudent = new Student();
            String id = newStudent.getStudentID();
            String password = newStudent.getPassword(ic);
            String email = newStudent.getEmail();

            //update user.txt file
            Student newAccount = new Student(id, password);
            newAccount.createStudentAccount();
            
            //update student.txt file
            Student finalDetails = new Student(id, name, ic, contact, email, intake); 
            finalDetails.addStudent();
            
            saveImageToFolder(id);
            
            clearField();
            
            DefaultTableModel table = (DefaultTableModel) studentJTable.getModel();
            //reset the table & display the latest data into table
            table.setRowCount(0);            
            Student show = new Student(table);
            show.showStudent();
            jTabbedPane1.setSelectedIndex(1);   //return to student page
        }
        displayUserCount();
    }//GEN-LAST:event_addStudentBtnActionPerformed

    private void saveImageToFolder(String id) {
        File sourceFile = openFileChooser.getSelectedFile();
        if (sourceFile != null) {
            try {
                File destFolder = new File("src/Profile/");
                if (!destFolder.exists()) {
                    destFolder.mkdir();
                }
                File destFile = new File(destFolder, id + ".jpg");
                Files.copy(sourceFile.toPath(), destFile.toPath());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error while saving image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearField(){
        nameField.setText(null);
        icField.setText(null);
        contactField.setText(null);
        genderGroup.clearSelection();
//        intakeCodeComboBox.removeAllItems();
        studentRegisterPic.setIcon(null);
        studentPicTextField.setText(null);
    }
        
    private void updateStudentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateStudentBtnActionPerformed
        String studID = studentIDLabel.getText();
        String name = studentnNameLabel.getText();
        String ic = studentICLabel.getText();
        String contact = studentNewContactField.getText();        
        String email = studentEmailLabel.getText();
        String intakeCode = (String)newIntakeCodeComboBox.getSelectedItem();
//        String lecturer = (String) lectureAssignedComboBox.getSelectedItem();

        Student edit = new Student(studID, name, ic, contact, email, intakeCode);
        try {
            edit.editStudent();
        }catch (IOException ex) {
            Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            saveNewImage(studID);
        }catch (IOException ex) {
            Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        displayStudentTable();
        jTabbedPane1.setSelectedIndex(1);
        
    }//GEN-LAST:event_updateStudentBtnActionPerformed

    private void cancelButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButton2ActionPerformed
        clearLecturerRegisterField();
    }//GEN-LAST:event_cancelButton2ActionPerformed

    private void clearLecturerRegisterField(){
        lcNameField.setText(null);
        lecICField.setText(null);
        lecContactField.setText(null);
        lecMajorComboBox.removeAllItems();
        lecMinorComboBox.removeAllItems();
        yesNoGroup.clearSelection();
        lectureProfileName.setText(null);
        setLecturerRegisterImage.setIcon(null);
    }
    
    private void studentPageSearchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_studentPageSearchFieldFocusGained
        if (studentPageSearchField.getText().equals("Search here")){
            studentPageSearchField.setText(null);
            studentPageSearchField.requestFocus();
            removePlaceholder(studentPageSearchField);
        }
    }//GEN-LAST:event_studentPageSearchFieldFocusGained

    private void lecturePageSearchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lecturePageSearchFieldFocusGained
        if (lecturePageSearchField.getText().equals("Search here")){
            lecturePageSearchField.setText(null);
            lecturePageSearchField.requestFocus();
            removePlaceholder(lecturePageSearchField);
        }
    }//GEN-LAST:event_lecturePageSearchFieldFocusGained

    private void studentPageSearchFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_studentPageSearchFieldFocusLost
        if (studentPageSearchField.getText().length() == 0){
            addPlaceholder(studentPageSearchField);
            studentPageSearchField.setText("Search here");
        }
    }//GEN-LAST:event_studentPageSearchFieldFocusLost

    private void lecturePageSearchFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lecturePageSearchFieldFocusLost
        if (lecturePageSearchField.getText().length() == 0){
            addPlaceholder(lecturePageSearchField);
            lecturePageSearchField.setText("Search here");
        }
    }//GEN-LAST:event_lecturePageSearchFieldFocusLost

    private void addCourseIntoComboBox(){
        String[] area = {"", "Accounting", "Cloud Engineering" , "Cyber Security", "Data Analytic", "Digital Forensic","Digital Marketing",
            "Finance", "Intelligent System", "Software Engineering" , "Psychology"};
        for (String major : area){
            lecMajorComboBox.addItem(major);
        } 
        for (String minor : area){
            lecMinorComboBox.addItem(minor);
        }
    }
    
    private void addCourseIntoComboBoxForUpdate(){
        lecNewMajorComboBox.removeAllItems();
        lecNewMinorComboBox.removeAllItems();
        
        String[] area = {"", "Accounting", "Cloud Engineering" , "Cyber Security", "Data Analytic", "Digital Forensic","Digital Marketing",
            "Finance", "Intelligent System", "Software Engineering" , "Psychology"};
        for (String major : area){
            lecNewMajorComboBox.addItem(major);
        } 
        for (String minor : area){
            lecNewMinorComboBox.addItem(minor);
        }
    }
    
    private void addLecturerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLecturerBtnActionPerformed
        String name = lcNameField.getText();
        String ic = lecICField.getText();
        String contact = lecContactField.getText().trim();
        String major = (String) lecMajorComboBox.getSelectedItem();
        String minor = (String) lecMinorComboBox.getSelectedItem();
        
        // Check if minor is null or empty, set it to "-"
        if (minor == null || minor.isEmpty()) {
            minor = "-";
        }
        
//        ButtonModel selectedYesNoButton = yesNoGroup.getSelection();
        yesCheckBox.setActionCommand("Yes");
        noCheckBox.setActionCommand("No");
        
        ButtonModel selectedYesNoButton = yesNoGroup.getSelection();
        String pm = selectedYesNoButton != null ? selectedYesNoButton.getActionCommand() : "";

//        String pm = selectedYesNoButton.getActionCommand();
        
        Lecturer lc = new Lecturer(name, ic, contact);
        List<String> validationErrors = lc.lecturerValidation();
        if (!validationErrors.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("The following errors are detected:\n");
            for (String error : validationErrors) {
                errorMessage.append("- ").append(error).append("\n");
            }
            JOptionPane.showMessageDialog(null, errorMessage.toString(), "Errors Found", JOptionPane.ERROR_MESSAGE);
        } else {
            Lecturer lec = new Lecturer();
            String lecID = lec.getLectureID();
            String email = lec.getEmail();
            String password = lec.getPassword(ic);
            Lecturer createAcc = new Lecturer(lecID, password);
            createAcc.createLectureAccount();
            Lecturer add = new Lecturer(lecID, name, ic, contact, email, major, minor);
            
            saveImageToFolder(lecID);
            
            add.addLecturer();
            
            clearLecturerRegisterField();
            displayUserCount();
            displayLecturerTable();
            jTabbedPane1.setSelectedIndex(5);
        }
    }//GEN-LAST:event_addLecturerBtnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void menu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu4MouseClicked
        jTabbedPane1.setSelectedIndex(9);
        menu4.setBackground(new Color(200, 219, 202));
        menu1.setBackground(new Color(172,190,174));
        menu2.setBackground(new Color(172,190,174));
        menu3.setBackground(new Color(172,190,174));
        menu5.setBackground(new Color(172,190,174));
        displayIntakeTable();
    }//GEN-LAST:event_menu4MouseClicked

    private void saveIntakeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveIntakeBtnActionPerformed
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        String level = (String) studyLevelComboBox.getSelectedItem();
        String course = (String) courseComboBox.getSelectedItem();
        String duration = (String) intakeDurationComboBox.getSelectedItem();
        Date intakeRegisterStartDate = intakeRegisterStartDateChooser.getDate();
        Date intakeRegisterEndDate = intakeRegisterEndDateChooser.getDate();
        Date intakeStartDate = intakeStartDateChooser.getDate();
        Date intakeEndDate = intakeEndDateChooser.getDate();
        
        
        //Intake registration start & end date
        String registerStartDate = dateFormat.format(intakeRegisterStartDate);
        String registerEndDate = dateFormat.format(intakeRegisterEndDate);
        
        //Intake start & end date
        String startDate = dateFormat.format(intakeStartDate);
        String endDate = dateFormat.format(intakeEndDate);
        
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate chosenIntakeRegisterStartDate = LocalDate.parse(registerStartDate, formatter);
        LocalDate chosenIntakeRegisterEndDate = LocalDate.parse(registerEndDate, formatter);
        
        LocalDate chosenIntakeStartDate = LocalDate.parse(startDate, formatter);
        LocalDate chosenIntakeEndDate = LocalDate.parse(endDate, formatter);
        
        
        if(chosenIntakeRegisterEndDate.isBefore(chosenIntakeRegisterStartDate)){
            JOptionPane.showMessageDialog(null, "Registration end date could not be earlier than registration start date!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(chosenIntakeEndDate.isBefore(chosenIntakeStartDate)){
            JOptionPane.showMessageDialog(null, "Intake end date could not be earlier than intake start date!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Initialize the variables
        Intake getDetails = new Intake(level, course, duration, registerStartDate, registerEndDate, startDate, endDate);
        Intake generateCode = new Intake(level, startDate, course);
        
        try {
            generateCode.createIntakeCode();
        } catch (IOException ex) {
            Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String code = generateCode.getIntakeCode();
        Intake checkCode = new Intake(code);
        
        boolean isCreated = false;
        try {
            isCreated = checkCode.checkIsCodeCreated();
        }
        catch (IOException ex) {
            Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(isCreated){
            Intake intake = new Intake(code, level, course, duration, registerStartDate, registerEndDate, startDate, endDate);
            intake.addIntake();

            clearRegisterIntakeField();
            jTabbedPane1.setSelectedIndex(9);
            displayIntakeTable();
        }    
    }//GEN-LAST:event_saveIntakeBtnActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearRegisterIntakeField();
        jTabbedPane1.setSelectedIndex(9);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void clearRegisterIntakeField(){
        studyLevelComboBox.setSelectedIndex(0);
        courseComboBox.setSelectedIndex(0);
        intakeDurationComboBox.setSelectedIndex(0);
        intakeRegisterStartDateChooser.setDate(null);
        intakeRegisterEndDateChooser.setDate(null);
        intakeStartDateChooser.setDate(null);
        intakeEndDateChooser.setDate(null);  
    }
    
    
    private void addIntakeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addIntakeButtonMouseClicked
        jTabbedPane1.setSelectedIndex(10);
        displayIntakeTable();
    }//GEN-LAST:event_addIntakeButtonMouseClicked

    private void clearStudentDetails(){
        studentIDLabel.setText(null);
        studentnNameLabel.setText(null);
        studentICLabel.setText(null);
        studentNewContactField.setText(null);
        studentEmailLabel.setText(null);
        newIntakeCodeComboBox.setSelectedIndex(0);
//        lectureAssignedComboBox.setSelectedIndex(0);
    }
    
    
    private void cancelEditStudentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelEditStudentBtnActionPerformed
//        clearStudentDetails();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_cancelEditStudentBtnActionPerformed

    private void updateLecBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateLecBtnActionPerformed
        String lecID = lectureIDLabel.getText();
        String name = lcNameLabel.getText();
        String ic = lcICLabel1.getText();
        String contact = lcNewContactField.getText();
        String email = lcEmailLabel.getText();
        String major = (String) lecNewMajorComboBox.getSelectedItem();
        String minor = (String) lecNewMinorComboBox.getSelectedItem();
        ButtonModel selectedYesNoButton = yesNoGroup.getSelection();
        newYesCheckBox.setActionCommand("Yes");
        newNoCheckBox.setActionCommand("No");
        String pm = selectedYesNoButton.getActionCommand();
        
        Lecturer lecturer = new Lecturer(lecID, name, ic, contact, email, major, minor, pm);

        try {
            lecturer.updateLecturer();
        }
        catch (IOException ex) {
            Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            saveNewImage(lecID);
        }
        catch (IOException ex) {
            Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        jTabbedPane1.setSelectedIndex(5);
        displayLecturerTable();
    }//GEN-LAST:event_updateLecBtnActionPerformed

    private void studentPageSearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_studentPageSearchFieldKeyReleased
        DefaultTableModel table = (DefaultTableModel) studentJTable.getModel();
        TableRowSorter<DefaultTableModel> table1 = new TableRowSorter<>(table);
        studentJTable.setRowSorter(table1);
        table1.setRowFilter(RowFilter.regexFilter(studentPageSearchField.getText()));
    }//GEN-LAST:event_studentPageSearchFieldKeyReleased

    private void clearLecDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearLecDetailsActionPerformed
//        lectureIDLabel.setText(null);
//        lcNameLabel.setText(null);
//        lcICLabel1.setText(null);
//        lcNewContactField.setText(null);
//        lcEmailLabel.setText(null);
//        lecNewMajorComboBox.setSelectedIndex(0);
//        lecNewMinorComboBox.setSelectedIndex(0);
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_clearLecDetailsActionPerformed

    private void lecturePageSearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lecturePageSearchFieldKeyReleased
        DefaultTableModel table = (DefaultTableModel) lecturerTable.getModel();
        TableRowSorter<DefaultTableModel> table1 = new TableRowSorter<>(table);
        lecturerTable.setRowSorter(table1);
        table1.setRowFilter(RowFilter.regexFilter(lecturePageSearchField.getText()));
    }//GEN-LAST:event_lecturePageSearchFieldKeyReleased

    private void intakePageSearchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_intakePageSearchFieldFocusGained
        if (intakePageSearchField.getText().equals("Search here")){
            intakePageSearchField.setText(null);
            intakePageSearchField.requestFocus();
            removePlaceholder(intakePageSearchField);
        }
    }//GEN-LAST:event_intakePageSearchFieldFocusGained

    private void intakePageSearchFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_intakePageSearchFieldFocusLost
        if (intakePageSearchField.getText().length() == 0){
            addPlaceholder(intakePageSearchField);
            intakePageSearchField.setText("Search here");
        }
    }//GEN-LAST:event_intakePageSearchFieldFocusLost

    private void intakePageSearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_intakePageSearchFieldKeyReleased
        DefaultTableModel table = (DefaultTableModel) intakeTable.getModel();
        TableRowSorter<DefaultTableModel> table1 = new TableRowSorter<>(table);
        intakeTable.setRowSorter(table1);
        table1.setRowFilter(RowFilter.regexFilter(intakePageSearchField.getText()));
    }//GEN-LAST:event_intakePageSearchFieldKeyReleased

    private void menu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu5MouseClicked
        jTabbedPane1.setSelectedIndex(11);
        menu5.setBackground(new Color(200, 219, 202));
        menu1.setBackground(new Color(172,190,174));
        menu2.setBackground(new Color(172,190,174));
        menu3.setBackground(new Color(172,190,174));
        menu4.setBackground(new Color(172,190,174));
        
        displayUserTable();
    }//GEN-LAST:event_menu5MouseClicked

    private void uploadStudentPicBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadStudentPicBtnActionPerformed
//        openFileChooser.setCurrentDirectory(new File("image/"));
        File downloadFolder = new File(System.getProperty("user.home"), "Downloads");
        File profileFolder = new File(downloadFolder, "profile");
        openFileChooser.setCurrentDirectory(profileFolder);
        int returnValue = openFileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION){
            studentRegisterPic.setIcon(new ImageIcon(openFileChooser.getSelectedFile().toString()));
            studentPicTextField.setText(openFileChooser.getSelectedFile().getName());
        }
    }//GEN-LAST:event_uploadStudentPicBtnActionPerformed

    private void uploadStudentNewProfileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadStudentNewProfileBtnActionPerformed
        File downloadFolder = new File(System.getProperty("user.home"), "Downloads");
        File profileFolder = new File(downloadFolder, "profile");
        openFileChooser.setCurrentDirectory(profileFolder);
        int returnValue = openFileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION){
            studentNewAvatar1.setIcon(new ImageIcon(openFileChooser.getSelectedFile().toString()));
            newStudentProfileTextField.setText(openFileChooser.getSelectedFile().getName());
        }
    }//GEN-LAST:event_uploadStudentNewProfileBtnActionPerformed

    private void updateLecturerPictureBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateLecturerPictureBtnActionPerformed
        File downloadFolder = new File(System.getProperty("user.home"), "Downloads");
        File profileFolder = new File(downloadFolder, "profile");
        openFileChooser.setCurrentDirectory(profileFolder);
        int returnValue = openFileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION){
            setLecturerRegisterImage.setIcon(new ImageIcon(openFileChooser.getSelectedFile().toString()));
            lectureProfileName.setText(openFileChooser.getSelectedFile().getName());
//            lectureProfileName.setText("Upload success");
        }
    }//GEN-LAST:event_updateLecturerPictureBtnActionPerformed

    private void uploadLecturerNewProfileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadLecturerNewProfileBtnActionPerformed
        File downloadFolder = new File(System.getProperty("user.home"), "Downloads");
        File profileFolder = new File(downloadFolder, "profile");
        openFileChooser.setCurrentDirectory(profileFolder);
        int returnValue = openFileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION){
            lecturerAvatarNew.setIcon(new ImageIcon(openFileChooser.getSelectedFile().toString()));
        }
    }//GEN-LAST:event_uploadLecturerNewProfileBtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String id = userIDLabel.getText();
        String newPassword = userNewPasswordField.getText();
        String role = roleNoLabel.getText();
        
        User user = new User(id, newPassword, role);
        
        try {
            user.updateUser();
        }
        catch (IOException ex) {
            Logger.getLogger(AdminPages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jTabbedPane1.setSelectedIndex(11);
        displayUserTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void userPageSearchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_userPageSearchFieldFocusGained
        if (userPageSearchField.getText().equals("Search here")){
            userPageSearchField.setText(null);
            userPageSearchField.requestFocus();
            removePlaceholder(userPageSearchField);
        }
    }//GEN-LAST:event_userPageSearchFieldFocusGained

    private void userPageSearchFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_userPageSearchFieldFocusLost
        if(userPageSearchField.getText().length() == 0){
            addPlaceholder(userPageSearchField);
            userPageSearchField.setText("Search here");
        }
    }//GEN-LAST:event_userPageSearchFieldFocusLost

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jTabbedPane1.setSelectedIndex(11);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void userPageSearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userPageSearchFieldKeyReleased
        DefaultTableModel table = (DefaultTableModel) userTable.getModel();
        TableRowSorter<DefaultTableModel> table1 = new TableRowSorter<>(table);
        userTable.setRowSorter(table1);
        table1.setRowFilter(RowFilter.regexFilter(userPageSearchField.getText()));
    }//GEN-LAST:event_userPageSearchFieldKeyReleased

    private void studentPageFilterBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentPageFilterBoxActionPerformed
        String selectedIntake = (String) studentPageFilterBox.getSelectedItem();
        if(selectedIntake != null){
            filterTable();
        }
    }//GEN-LAST:event_studentPageFilterBoxActionPerformed

    private void cardPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cardPanel1MouseEntered
        zoomTimer.start();
    }//GEN-LAST:event_cardPanel1MouseEntered

    private void cardPanel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cardPanel1MouseExited
        zoomTimer.stop();
        cardPanel1.setPreferredSize(originalSize);
        cardPanel1.revalidate();
        cardPanel1.repaint();
    }//GEN-LAST:event_cardPanel1MouseExited

    private void addStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentButtonActionPerformed
        jTabbedPane1.setSelectedIndex(2);
        Intake intakeList = new Intake();
        intakeList.addIntakeIntoComboBox(intakeCodeComboBox);
    }//GEN-LAST:event_addStudentButtonActionPerformed

    private void printNameListBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printNameListBtnActionPerformed
        if (studentPageFilterBox.getSelectedItem() != null) {
            String intakeCode = (String) studentPageFilterBox.getSelectedItem();
            DefaultTableModel model = (DefaultTableModel) studentJTable.getModel();
            TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) studentJTable.getRowSorter();

            // Filter the table based on the selected intake code
            filterTable();

            List<Object[]> data = new ArrayList<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                int modelRowIndex = sorter.convertRowIndexToView(i);
                if (modelRowIndex >= 0) { // Check if the row is visible after filtering
                    Object id = model.getValueAt(i, 0);
                    Object name = model.getValueAt(i, 1);
                    data.add(new Object[]{id, name});
                }
            }

//            System.out.println("Contents of the data list:");
//            for (Object[] row : data) {
//                System.out.println("Row: ID=" + row[0] + ", Name=" + row[1]);
//            }

//            PrintNameList print = new PrintNameList();
//            print.showForm(data.toArray(new Object[0][0]), intakeCode);
//            print.printPanel();

            PrintNameList print = new PrintNameList();
            print.showForm(data.toArray(new Object[0][0]), intakeCode);
            print.generatePdf();
        }
    }//GEN-LAST:event_printNameListBtnActionPerformed

    private void addStudentByIntakeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentByIntakeButtonActionPerformed
        jTabbedPane1.setSelectedIndex(3);
        Intake intake = new Intake();
        intake.addIntakeIntoComboBox(intakeForGroupComboBox);
    }//GEN-LAST:event_addStudentByIntakeButtonActionPerformed

    private void addStudentFromListBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentFromListBtnActionPerformed
        Student student = new Student();
        
        List<String[]> previousRecord = student.loadExistingRecords();
        List<String> studentRecords = new ArrayList<>();
        List<String> userRecords = new ArrayList<>();
        
        student.initializeLastStudentID();
        
        String intakeCode = (String) intakeForGroupComboBox.getSelectedItem();
        List<Student> studentsToRegister = new ArrayList<>();
    
        for (int i = 0; i < studentListTable.getRowCount(); i++) {
            String name = (String) studentListTable.getValueAt(i, 0);
            String ic = (String) studentListTable.getValueAt(i, 1);
            String contact = (String) studentListTable.getValueAt(i, 2);

            if (isDuplicate(ic, contact, previousRecord, studentRecords)) {
                JOptionPane.showMessageDialog(this, "Duplicate data found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String studentID = student.createStudentIDForGroup();
            String email = student.createEmailForGroup(studentID);
            String password = student.createPasswordForGroup(ic, studentID);
            //add grouping here
            
            Student newStudent = new Student(studentID, name, ic, contact, email, intakeCode);
            newStudent.setPassword(password);
            studentsToRegister.add(newStudent);
        }
        
            List<Student> groupedStudents = student.groupStudent(studentsToRegister, intakeCode);
            for (Student individual : groupedStudents) {
                String record = String.join(";", individual.getId(), individual.getName(), individual.getIc(), individual.getContact(), individual.getEmail(), individual.getIntakeCode());
                studentRecords.add(record);

                String userRecord = String.join(";", individual.getId(), individual.getPassword(), "3");
                userRecords.add(userRecord);
            }
            
        student.addStudent(studentRecords); 
        student.createStudentAccount(userRecords);
        Intake intake = new Intake();
        intake.updateIntake(intakeCode, groupedStudents.size() / 20 + 1);
        displayUserCount();
        displayStudentTable();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_addStudentFromListBtnActionPerformed

    private boolean isDuplicate(String ic, String contact, List<String[]>previousRecord, List<String>studentRecord){
        for(String[] rec : previousRecord){
            if(rec[2].equals(ic) & rec[3].equals(contact)){
                return true;
            }
        }
        for(String rec : studentRecord){
            String[] data = rec.split(";");
            if(data[2].equals(ic) & data[3].equals(contact)){
                return true;
            }
        }
        return false;
    }
    
    
    private void loadStudentIntoTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadStudentIntoTableButtonActionPerformed
        DefaultTableModel table = (DefaultTableModel) studentListTable.getModel();
        table.setRowCount(0);
        FileHandler fh = new FileHandler();
        fh.displayData("studentList.txt", table);
        
        TableAlignment alignment = new TableAlignment();
        alignment.alignTable(studentListTable);
    }//GEN-LAST:event_loadStudentIntoTableButtonActionPerformed

    private void menu2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_menu2MouseEntered

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        DefaultTableModel table = (DefaultTableModel) studentListTable.getModel();
        table.setRowCount(0);
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void addLecturerFromListBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLecturerFromListBtnActionPerformed
        Lecturer lecturer = new Lecturer();
        
        List<String[]> previousRecord = lecturer.loadExistingRecords();
        List<String> lectureRecords = new ArrayList<>();
        List<String> userRecords = new ArrayList<>();
        
        lecturer.initializeLastLecturerID();
        
        for (int i = 0; i < lectureListTable.getRowCount(); i++) {
            String name = (String) lectureListTable.getValueAt(i, 0);
            String ic = (String) lectureListTable.getValueAt(i, 1);
            String contact = (String) lectureListTable.getValueAt(i, 2);
            String major = (String) lectureListTable.getValueAt(i, 3);
            String minor = (String) lectureListTable.getValueAt(i, 4);

            if (isDuplicate(ic, contact, previousRecord, lectureRecords)) {
                JOptionPane.showMessageDialog(this, "Duplicate data found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String lecturerID = lecturer.createLectureIDForGroup();
            String email = lecturer.createEmailForGroup(lecturerID);
            String password = lecturer.createPasswordForGroup(ic, lecturerID);

            String record = String.join(";", lecturerID, name, ic, contact, email, major, minor, "-");
            lectureRecords.add(record);
            
            String userRecord = String.join(";", lecturerID, password, "2");
            userRecords.add(userRecord);
        }
        lecturer.addLecturer(lectureRecords); 
        lecturer.createLectureAccount(userRecords);
        
        displayUserCount();
        displayLecturerTable();

        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_addLecturerFromListBtnActionPerformed

    private void loadLecturerIntoTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadLecturerIntoTableButtonActionPerformed
        DefaultTableModel table = (DefaultTableModel) lectureListTable.getModel();
        table.setRowCount(0);
        FileHandler fh = new FileHandler();
        fh.displayData("lecturerList.txt", table);
        
        TableAlignment alignment = new TableAlignment();
        alignment.alignTable(lectureListTable);
    }//GEN-LAST:event_loadLecturerIntoTableButtonActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void addLecturerByGtoupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLecturerByGtoupButtonActionPerformed
       jTabbedPane1.setSelectedIndex(7);
    }//GEN-LAST:event_addLecturerByGtoupButtonActionPerformed

    private void addLecturerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLecturerButtonActionPerformed
        jTabbedPane1.setSelectedIndex(6);
        addCourseIntoComboBox();
    }//GEN-LAST:event_addLecturerButtonActionPerformed

     private void filterTable() {
        DefaultTableModel table = (DefaultTableModel) studentJTable.getModel();
        String selectedItem = (String) studentPageFilterBox.getSelectedItem();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(table);
        studentJTable.setRowSorter(sorter);
        if (selectedItem != null) {
            sorter.setRowFilter(RowFilter.regexFilter(selectedItem));
        } else {
            sorter.setRowFilter(null); // Show all rows when no selection
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatLightLaf.setup();
        FlatLaf.registerCustomDefaultsSource("flatlafProperties");
        
        
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(AdminPages.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(AdminPages.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AdminPages.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(AdminPages.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPages().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addIntake;
    private javax.swing.JLabel addIntakeButton;
    private javax.swing.JButton addLecturerBtn;
    private javax.swing.JButton addLecturerButton;
    private javax.swing.JButton addLecturerByGtoupButton;
    private javax.swing.JButton addLecturerFromListBtn;
    private javax.swing.JButton addStudentBtn;
    private javax.swing.JButton addStudentButton;
    private javax.swing.JButton addStudentByIntakeButton;
    private javax.swing.JButton addStudentFromListBtn;
    private Admin.SetUserProfile adminProfile;
    private javax.swing.JButton cancelButton2;
    private javax.swing.JButton cancelEditStudentBtn;
    private Swing.CardPanel cardPanel1;
    private Swing.CardPanel cardPanel2;
    private Swing.CardPanel cardPanel3;
    private ChartFrame.Chart chart;
    private javax.swing.JButton clearLecDetails;
    private javax.swing.JTextField contactField;
    private javax.swing.JComboBox<String> courseComboBox;
    private javax.swing.JPanel dashboard;
    private javax.swing.JPanel editLecturer;
    private javax.swing.JPanel editStudent;
    private javax.swing.JPanel editUser;
    private javax.swing.JLabel exitLabel;
    private javax.swing.ButtonGroup genderGroup;
    private javax.swing.JPanel header;
    private javax.swing.JTextField icField;
    private javax.swing.JPanel intake;
    private javax.swing.JComboBox<String> intakeCodeComboBox;
    private javax.swing.JComboBox<String> intakeDurationComboBox;
    private com.toedter.calendar.JDateChooser intakeEndDateChooser;
    private javax.swing.JComboBox<String> intakeForGroupComboBox;
    private javax.swing.JTextField intakePageSearchField;
    private com.toedter.calendar.JDateChooser intakeRegisterEndDateChooser;
    private com.toedter.calendar.JDateChooser intakeRegisterStartDateChooser;
    private com.toedter.calendar.JDateChooser intakeStartDateChooser;
    private javax.swing.JTable intakeTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
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
    private javax.swing.JLabel jLabel40;
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
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lcEmailLabel;
    private javax.swing.JLabel lcICLabel1;
    private javax.swing.JTextField lcNameField;
    private javax.swing.JLabel lcNameLabel;
    private javax.swing.JTextField lcNewContactField;
    private javax.swing.JTextField lecContactField;
    private javax.swing.JTextField lecICField;
    private javax.swing.JComboBox<String> lecMajorComboBox;
    private javax.swing.JComboBox<String> lecMinorComboBox;
    private javax.swing.JComboBox<String> lecNewMajorComboBox;
    private javax.swing.JComboBox<String> lecNewMinorComboBox;
    private javax.swing.JLabel lectureIDLabel;
    private javax.swing.JTable lectureListTable;
    private javax.swing.JTextField lecturePageSearchField;
    private javax.swing.JLabel lectureProfileName;
    private javax.swing.JPanel lecturer;
    private Admin.SetUserProfile lecturerAvatarNew;
    private javax.swing.JTable lecturerTable;
    private javax.swing.JButton loadLecturerIntoTableButton;
    private javax.swing.JButton loadStudentIntoTableButton;
    private javax.swing.JPanel menu1;
    private javax.swing.JPanel menu2;
    private javax.swing.JPanel menu3;
    private javax.swing.JPanel menu4;
    private javax.swing.JPanel menu5;
    private javax.swing.JPanel menubar;
    private javax.swing.JTextField nameField;
    private javax.swing.JComboBox<String> newIntakeCodeComboBox;
    private javax.swing.JCheckBox newNoCheckBox;
    private javax.swing.JTextField newStudentProfileTextField;
    private javax.swing.JCheckBox newYesCheckBox;
    private javax.swing.JCheckBox noCheckBox;
    private javax.swing.JButton printNameListBtn;
    private javax.swing.JPanel registerLecturer;
    private javax.swing.JPanel registerLecturerByGroup;
    private javax.swing.JPanel registerStudent;
    private javax.swing.JPanel registerStudentByIntake;
    private javax.swing.ButtonGroup roleGroup;
    private javax.swing.JLabel roleLabel;
    private javax.swing.JLabel roleNoLabel;
    private javax.swing.JButton saveIntakeBtn;
    private Admin.SetUserProfile setLecturerRegisterImage;
    private javax.swing.JPanel student;
    private javax.swing.JLabel studentEmailLabel;
    private javax.swing.JLabel studentICLabel;
    private javax.swing.JLabel studentIDLabel;
    private javax.swing.JTable studentJTable;
    private javax.swing.JTable studentListTable;
    private Admin.SetUserProfile studentNewAvatar1;
    private javax.swing.JTextField studentNewContactField;
    private javax.swing.JComboBox<String> studentPageFilterBox;
    private javax.swing.JTextField studentPageSearchField;
    private javax.swing.JTextField studentPicTextField;
    private Admin.SetUserProfile studentRegisterPic;
    private javax.swing.JScrollPane studentScrollPane;
    private javax.swing.JPanel studentTablePanel;
    private javax.swing.JLabel studentnNameLabel;
    private javax.swing.JComboBox<String> studyLevelComboBox;
    private javax.swing.JButton updateLecBtn;
    private javax.swing.JButton updateLecturerPictureBtn;
    private javax.swing.JButton updateStudentBtn;
    private javax.swing.JButton uploadLecturerNewProfileBtn;
    private javax.swing.JButton uploadStudentNewProfileBtn;
    private javax.swing.JButton uploadStudentPicBtn;
    private javax.swing.JLabel userIDLabel;
    private Admin.SetUserProfile userNewAvatar1;
    private javax.swing.JTextField userNewPasswordField;
    private javax.swing.JPanel userPage;
    private javax.swing.JTextField userPageSearchField;
    private javax.swing.JTable userTable;
    private javax.swing.JCheckBox yesCheckBox;
    private javax.swing.ButtonGroup yesNoGroup;
    // End of variables declaration//GEN-END:variables
}
