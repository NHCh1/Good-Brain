/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package project_manager;

import MainProgram.general_home;
import actionCell.tableActionCellEditor;
import actionCell.tableActionCellEditor2;
import actionCell.tableActionCellRender;
import actionCell.tableActionEvent;
import actionCell.tableActionEvent2;
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javaswingdev.chart.ModelPieChart;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author User
 */
public class pmHomePage extends javax.swing.JFrame {

    private pmFunction func;
    private pmProjectInfo pjInfo;
    private pmIntakeInfo intInfo;
    private pmFeedbackInfo fdInfo;
    private pmStudentInfo stuInfo;
    private pmPresentationInfo preInfo;
    private pmSubmissionInfo subInfo;
    private Component previousProjectComponent;
    private Component previousIntakeComponent;
    private Component previousPresentationComponent;
    private Component previous2Component;
    private pmHomePage homepage;
    private String pmID;
    private String selectedIntakeCode;
//    private general_home generalHome;
    
    /**
     * Creates new form pm_homepage
     */
    
    public void backButtonActionPerformed() {                                         
        // Switch back to the previous component
        if (previousProjectComponent != null) {
            jTabbedPane2.setComponentAt(0, previousProjectComponent);
            jTabbedPane2.revalidate();
            jTabbedPane2.repaint();
        }
    }  
    
    public void backToIntakeButtonActionPerformed() {                                         
        // Switch back to the previous component
        if (previousIntakeComponent != null) {
            jTabbedPane2.setComponentAt(2, previousIntakeComponent);
            jTabbedPane2.revalidate();
            jTabbedPane2.repaint();
        }
    }  
    
    public void showPmName(String id){
        List<String> pmData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("lecturer.txt"))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(";");
                if (parts[0].equals(pmID)){
                    final String loginLec = parts[1];
                    displayName.setText(loginLec);
                    br.close();
                    break;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public pmHomePage(String id) throws IOException {
        initComponents();
        this.homepage = this;
        this.pmID = id;

        showPmName(pmID);
        header h = new header();
        jPanel4.add(h);
        profileNav.setIcon(new ImageIcon("src/Profile/"+ id +".jpg"));
        
        pieChart1.clearData();
        int foundationCount = showPieData("student.txt", "GBF");
        pieChart1.addData(new ModelPieChart("Foundation", foundationCount, Color.red));
        int diplomaCount = showPieData("student.txt", "GBU");
        pieChart1.addData(new ModelPieChart("Diploma", diplomaCount, Color.yellow));
        int degreeCount = showPieData("student.txt", "GBD");
        pieChart1.addData(new ModelPieChart("Degree", degreeCount, Color.blue));
        int masterCount = showPieData("student.txt", "GBM");
        pieChart1.addData(new ModelPieChart("Master", masterCount, Color.green));
//        int foundationCount = showPieData("student.txt", "GBD");
//        showPieData("student.txt");
        
        pjInfo = new pmProjectInfo();
        intInfo = new pmIntakeInfo();
        fdInfo = new pmFeedbackInfo();
        stuInfo = new pmStudentInfo();
        func = new pmFunction();
        
        jTabbedPane2.setSelectedIndex(7);
        
        tableActionEvent event = new tableActionEvent(){
            @Override
            public void onEdit(int row){
                System.out.println("Edit row : " + row);
                int modelRow = projectTable.convertRowIndexToModel(row);
                DefaultTableModel model = (DefaultTableModel) projectTable.getModel();

                if (modelRow != -1) {
                    pjInfo.setProjectID(model.getValueAt(modelRow, 0).toString());
                    pjInfo.setProjectName(model.getValueAt(modelRow, 1).toString());
                    pjInfo.setIntake(model.getValueAt(modelRow, 2).toString());
                    System.out.println("this is from home page: " + model.getValueAt(modelRow, 2).toString());
                    pjInfo.setSupervisor(model.getValueAt(modelRow, 3).toString());
                    pjInfo.setSecondMarker(model.getValueAt(modelRow, 4).toString());
                    pjInfo.setDueDate(model.getValueAt(modelRow, 5).toString());
                }
                
                pmNewProject project = new pmNewProject(homepage, pjInfo, true);
                previousProjectComponent = jTabbedPane2.getSelectedComponent();                
                jTabbedPane2.setComponentAt(0, project);
                jTabbedPane2.revalidate();
                jTabbedPane2.repaint();
            }

            @Override
            public void onDelete(int row){
                if(projectTable.isEditing()){
                    projectTable.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) projectTable.getModel();

                int modelRow = projectTable.convertRowIndexToModel(row);
                
                if(modelRow != -1){
                    String projectID = model.getValueAt(modelRow, 0).toString();
                    
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to delete this project?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                    
                    if(confirm == JOptionPane.YES_OPTION){
                        
                        try {
                            func.deleteData("project.txt", projectID);
                            model.removeRow(modelRow);
                            JOptionPane.showMessageDialog(homepage,"Project deleted successfully!");
                        } catch(IOException e){
                            JOptionPane.showMessageDialog(null, "Error deleting project: " + e.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                }
            }
        };
        
        tableActionEvent2 event2 = new tableActionEvent2() {
            @Override
            public void onEdit(int row) {
                System.out.println("this is row: " + row);
                int modelRow2 = feedbackTable.convertRowIndexToModel(row);
                DefaultTableModel model2 = (DefaultTableModel) feedbackTable.getModel();

                if (modelRow2 != -1) {
                    fdInfo.setFdID(model2.getValueAt(modelRow2, 0).toString());
                    fdInfo.setFdStuID(model2.getValueAt(modelRow2, 1).toString());
                    fdInfo.setFdProjID(model2.getValueAt(modelRow2, 2).toString());
//                    fdInfo.setFdLecID(model2.getValueAt(modelRow2, 3).toString());
                    fdInfo.setFdContent(model2.getValueAt(modelRow2, 3).toString());
                }
                
                pmViewFeedback viewFeedback = new pmViewFeedback(fdInfo);
                viewFeedback.setVisible(true);
            }
        };
        
        projectTable.getColumnModel().getColumn(6).setCellRenderer(new tableActionCellRender(false));
        projectTable.getColumnModel().getColumn(6).setCellEditor(new tableActionCellEditor(event));
        feedbackTable.getColumnModel().getColumn(4).setCellRenderer(new tableActionCellRender(true));
        feedbackTable.getColumnModel().getColumn(4).setCellEditor(new tableActionCellEditor2(event2));
            
            
        // Panel 1
        customPanel1.setTitle("PROJECT");
        String projectFilePath = "project.txt";
        int totalProject = countLines(projectFilePath);
        customPanel1.setData(String.valueOf(totalProject) + " in total");
        customPanel1.setIcon(new ImageIcon("src/Icon/pmProject.png"));
        customPanel1.setStartColor(Color.decode("#000000"));
        customPanel1.setEndColor(Color.decode("#ffffff"));
        
        // Panel 2
        customPanel2.setTitle("INTAKE");
        String intakeFilePath = "intake.txt";
        int totalIntake = countLines(intakeFilePath);
        customPanel2.setData(String.valueOf(totalIntake) + " in total");
        customPanel2.setIcon(new ImageIcon("src/Icon/pmIntake.png"));
        customPanel2.setStartColor(Color.decode("#000000"));
        customPanel2.setEndColor(Color.decode("#ffffff"));
        
        // Panel 3
        customPanel3.setTitle("STUDENT");
        String studentFilePath = "student.txt";
        int totalStudent = countLines(studentFilePath);
        customPanel3.setData(String.valueOf(totalStudent) + " in total");
        customPanel3.setIcon(new ImageIcon("src/Icon/pmStudent.png"));
        customPanel3.setStartColor(Color.decode("#000000"));
        customPanel3.setEndColor(Color.decode("#ffffff"));
        
    }
    
    public static int countLines(String filePath) throws IOException {
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        }
        return lineCount;
    }
    
    private List<String> getAllIntakes(String filename){
        List<String> allIntakes = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(";");
                String intakeID = parts[0];
                allIntakes.add(intakeID);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return allIntakes;
    }
    
    private void populateIntakeComboBox(String filename, JComboBox<String> intakeComboBox) {
        List<String> availableIntakes = getAllIntakes(filename);
        for (String intake : availableIntakes) {
            intakeComboBox.addItem(intake);
        }
    }
    
    private int showPieData(String filename, String type) {
//        String filename
        int Count = 0;
//        int foundationCount = 0;
//        int diplomaCount = 0;
//        int degreeCount = 0;
//        int masterCount = 0;
//        List<String> intakePie = new ArrayList<>();
//        Pattern pattern = Pattern.compile("GB[UFDM][0-9]+");
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(";");
                if (parts.length >= 6){
                    String intakeCode = parts[5];
//                    Matcher matcher = pattern.matcher(intakeCode);
                    if(intakeCode.startsWith(type)){
//                        type = matcher.group(1);
                        Count++;
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return Count;
//        pieChart1.addData(new ModelPieChart("Foundation",foundationCount,Color.red));
//        pieChart1.addData(new ModelPieChart("Diploma",diplomaCount,Color.yellow));
//        pieChart1.addData(new ModelPieChart("Degree",degreeCount,Color.blue));
//        pieChart1.addData(new ModelPieChart("Master",masterCount,Color.green));
    }
//    
//    private void filterTableByIntake(String intakeID, JTable table) {
//        DefaultTableModel model = (DefaultTableModel) table.getModel();
//        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
//        table.setRowSorter(sorter);
//
//        if (intakeID == null || intakeID.trim().isEmpty()) {
//            sorter.setRowFilter(null);
//        } else {
//            // Create a RowFilter that matches the intakeID
//            sorter.setRowFilter(RowFilter.regexFilter("^" + intakeID + ".*", 0)); // Assuming intakeID is in the first column (index 0)
//        }
//    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        profileNav = new avatar.ImageAvatar();
        displayName = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        projectTable = new javax.swing.JTable();
        newBtn = new javax.swing.JButton();
        projSearch = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        intakeTable = new javax.swing.JTable();
        intSearch = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        presentationTable = new javax.swing.JTable();
        preSearch = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        submissionTable = new javax.swing.JTable();
        subSearch = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        studentTable = new javax.swing.JTable();
        intakeFilter = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        feedbackTable = new javax.swing.JTable();
        feedbackSearch = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        imageAvatar1 = new avatar.ImageAvatar();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        profileName = new javax.swing.JTextField();
        profileIC = new javax.swing.JTextField();
        profilePhone = new javax.swing.JTextField();
        profileEmail = new javax.swing.JTextField();
        profileMajor = new javax.swing.JTextField();
        profileMinor = new javax.swing.JTextField();
        profilePassword = new javax.swing.JPasswordField();
        editPmProfile = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pieChart1 = new javaswingdev.chart.PieChart();
        customPanel1 = new card.CustomPanel();
        customPanel2 = new card.CustomPanel();
        customPanel3 = new card.CustomPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(850, 550));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(209, 211, 199));

        jLabel1.setFont(new java.awt.Font("Bell MT", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(59, 67, 56));
        jLabel1.setText("GOODBRAIN");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/brainLogo.png"))); // NOI18N

        profileNav.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Profile/L001.jpg"))); // NOI18N
        profileNav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileNavMouseClicked(evt);
            }
        });

        displayName.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        displayName.setForeground(new java.awt.Color(0, 0, 0));
        displayName.setText("Name");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(795, Short.MAX_VALUE)
                .addComponent(displayName, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(profileNav, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel1)
                    .addContainerGap(676, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(displayName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(profileNav, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(14, 14, 14))
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addContainerGap(11, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 120));

        jPanel1.setBackground(new java.awt.Color(226, 228, 216));
        jPanel1.setPreferredSize(new java.awt.Dimension(1030, 50));

        jLabel3.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(60, 63, 65));
        jLabel3.setText("Project");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(60, 63, 65));
        jLabel4.setText("Presentation");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(60, 63, 65));
        jLabel5.setText("Intake");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(60, 63, 65));
        jLabel6.setText("Submission");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(60, 63, 65));
        jLabel9.setText("Feedback");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(60, 63, 65));
        jLabel10.setText("Student");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(60, 63, 65));
        jLabel11.setText("Dashboard");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/pmChangeToLec.png"))); // NOI18N

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/logout.png"))); // NOI18N
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel11)
                .addGap(45, 45, 45)
                .addComponent(jLabel3)
                .addGap(55, 55, 55)
                .addComponent(jLabel5)
                .addGap(61, 61, 61)
                .addComponent(jLabel4)
                .addGap(59, 59, 59)
                .addComponent(jLabel6)
                .addGap(58, 58, 58)
                .addComponent(jLabel10)
                .addGap(63, 63, 63)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 13, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5)))
                                .addGap(10, 10, 10))))))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 1030, -1));

        jTabbedPane2.setPreferredSize(new java.awt.Dimension(1030, 545));

        jPanel9.setBackground(new java.awt.Color(239, 240, 234));
        jPanel9.setPreferredSize(new java.awt.Dimension(900, 510));

        projectTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Project ID", "Project Name", "Intake", "Supervisor", "Second Marker", "Due Date", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        projectTable.setRowHeight(30);
        projectTable.setSelectionBackground(new java.awt.Color(0, 204, 255));
        jScrollPane1.setViewportView(projectTable);

        newBtn.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        newBtn.setForeground(new java.awt.Color(60, 63, 65));
        newBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/pmPlus.png"))); // NOI18N
        newBtn.setText("  New");
        newBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newBtnMouseClicked(evt);
            }
        });
        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });

        projSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                projSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                projSearchFocusLost(evt);
            }
        });
        projSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projSearchActionPerformed(evt);
            }
        });
        projSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                projSearchKeyPressed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(60, 63, 65));
        jLabel24.setText("Search :");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 921, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(projSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(443, 443, 443)
                        .addComponent(newBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(224, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(newBtn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 1033, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Project", jPanel5);

        jPanel7.setBackground(new java.awt.Color(239, 240, 234));

        intakeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Intake ID", "Study Level", "Intake Name", "Duration", "Resgister Start", "Register End", "Intake Start", "Intake End "
            }
        ));
        intakeTable.setRowHeight(30);
        jScrollPane3.setViewportView(intakeTable);

        intSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                intSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                intSearchFocusLost(evt);
            }
        });
        intSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                intSearchKeyPressed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(60, 63, 65));
        jLabel23.setText("Search :");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 932, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(intSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(231, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
        );

        jTabbedPane2.addTab("Intake", jPanel7);

        jPanel6.setBackground(new java.awt.Color(239, 240, 234));

        jLabel7.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(60, 63, 65));
        jLabel7.setText("Search :");

        presentationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Presentation ID", "Student ID", "Project ID", "Date", "Time", "Student Acceptance", "Supervisor", "Supervisor Acceptance", "Second Marker", "Second Marker Acceptance"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        presentationTable.setRowHeight(30);
        jScrollPane2.setViewportView(presentationTable);

        preSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                preSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                preSearchFocusLost(evt);
            }
        });
        preSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                preSearchKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(preSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 933, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(223, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(preSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
        );

        jTabbedPane2.addTab("Presentation", jPanel6);

        jPanel8.setBackground(new java.awt.Color(239, 240, 234));

        submissionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Submission ID", "Project ID", "Student ID", "Status", "Grade", "Name", "Path", "Super Command", "Second Command"
            }
        ));
        submissionTable.setRowHeight(30);
        jScrollPane4.setViewportView(submissionTable);

        subSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                subSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                subSearchFocusLost(evt);
            }
        });
        subSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                subSearchKeyPressed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(60, 63, 65));
        jLabel22.setText("Search :");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 938, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(subSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(233, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(subSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
        );

        jTabbedPane2.addTab("Submission", jPanel8);

        jPanel11.setBackground(new java.awt.Color(239, 240, 234));

        studentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Student ID", "Student Name", "IC", "Phone", "Email", "Intake"
            }
        ));
        studentTable.setRowHeight(30);
        jScrollPane6.setViewportView(studentTable);

        intakeFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        intakeFilter.setPreferredSize(new java.awt.Dimension(64, 22));
        intakeFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intakeFilterActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(60, 63, 65));
        jLabel13.setText("Search by Intake :");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 940, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(intakeFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(238, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intakeFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
        );

        jTabbedPane2.addTab("Student", jPanel11);

        jPanel10.setBackground(new java.awt.Color(239, 240, 234));

        feedbackTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Feedback ID", "Student ID", "Project ID", "Feedback", "View"
            }
        ));
        feedbackTable.setRowHeight(30);
        jScrollPane5.setViewportView(feedbackTable);

        feedbackSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                feedbackSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                feedbackSearchFocusLost(evt);
            }
        });
        feedbackSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                feedbackSearchKeyPressed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(60, 63, 65));
        jLabel21.setText("Search :");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 929, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(feedbackSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(639, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(228, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(feedbackSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );

        jTabbedPane2.addTab("Feedback", jPanel10);

        jPanel12.setBackground(new java.awt.Color(239, 240, 234));

        jLabel15.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(60, 63, 65));
        jLabel15.setText("Name :");

        jLabel16.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(60, 63, 65));
        jLabel16.setText("IC Number :");

        jLabel17.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(60, 63, 65));
        jLabel17.setText("Phone Number :");

        jLabel18.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(60, 63, 65));
        jLabel18.setText("Email :");

        jLabel19.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(60, 63, 65));
        jLabel19.setText("Major :");

        editPmProfile.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        editPmProfile.setForeground(new java.awt.Color(60, 63, 65));
        editPmProfile.setText("Edit Profile");
        editPmProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPmProfileActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(60, 63, 65));
        jLabel12.setText("Minor :");

        jLabel14.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(60, 63, 65));
        jLabel14.setText("Password :");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(182, Short.MAX_VALUE)
                .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(profilePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(profileIC, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(profileName, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(profileMajor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(profileEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(profileMinor, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(profilePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(131, 131, 131))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(445, 445, 445)
                .addComponent(editPmProfile)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(232, 232, 232)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(profileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(profileIC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(profilePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(profileEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(profileMajor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)))
                    .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(profileMinor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(34, 34, 34)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(profilePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(editPmProfile)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Profile", jPanel12);

        jPanel2.setBackground(new java.awt.Color(239, 240, 234));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(customPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(customPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addComponent(customPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(279, 279, 279)
                .addComponent(pieChart1, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                .addGap(320, 320, 320))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(228, 228, 228)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(customPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(customPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(pieChart1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addGap(99, 99, 99))
        );

        jTabbedPane2.addTab("tab8", jPanel2);

        getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -55, -1, 770));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void newBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newBtnMouseClicked
    }//GEN-LAST:event_newBtnMouseClicked

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        pjInfo = new pmProjectInfo();
        pmNewProject project = new pmNewProject(homepage, pjInfo, false);
        previousProjectComponent = jTabbedPane2.getSelectedComponent();
        jTabbedPane2.setComponentAt(0, project);
        jTabbedPane2.revalidate();
        jTabbedPane2.repaint();
    }//GEN-LAST:event_newBtnActionPerformed

    private void projSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projSearchActionPerformed
    }//GEN-LAST:event_projSearchActionPerformed

    private void projSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_projSearchKeyPressed
        func.searchKeyPressed(projectTable,projSearch);
    }//GEN-LAST:event_projSearchKeyPressed

    private void profileNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileNavMouseClicked
        jTabbedPane2.setSelectedIndex(6);
        
        List<String> projectManagerData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("lecturer.txt"))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(";");
                if (parts[0].equals(pmID)){
                    projectManagerData = Arrays.asList(parts);
                    br.close();
                    break;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        
        List<String> userData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(";");
                if (parts[0].equals(pmID)){
                    userData = Arrays.asList(parts);
                    br.close();
                    break;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        
        if (!projectManagerData.isEmpty()){
            profileName.setText(projectManagerData.get(1));
            profileIC.setText(projectManagerData.get(2));
            profilePhone.setText(projectManagerData.get(3));
            profileEmail.setText(projectManagerData.get(4));
            profileMajor.setText(projectManagerData.get(5));
            profileMinor.setText(projectManagerData.get(6));
            profilePassword.setText(userData.get(1));
            profileName.setEditable(false);
            profileIC.setEditable(false);
            profilePhone.setEditable(false);
            profileEmail.setEditable(false);
            profileMajor.setEditable(false);
            profileMinor.setEditable(false);
        }
    }//GEN-LAST:event_profileNavMouseClicked

    private void projSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_projSearchFocusLost
        func.searchFocusLost(projSearch);
    }//GEN-LAST:event_projSearchFocusLost

    private void projSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_projSearchFocusGained
        func.searchFocusGained(projSearch);
    }//GEN-LAST:event_projSearchFocusGained

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        stuInfo = new pmStudentInfo();
        jTabbedPane2.setSelectedIndex(4);
        func.clearTable(studentTable);
        func.setTable("student.txt", studentTable);
        populateIntakeComboBox("intake.txt", intakeFilter);
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        fdInfo = new pmFeedbackInfo();
        jTabbedPane2.setSelectedIndex(5);
        func.clearTable(feedbackTable);
        func.setTable("feedback.txt", feedbackTable);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        jTabbedPane2.setSelectedIndex(3);
        func.clearTable(submissionTable);
        func.setTable("submission.txt", submissionTable);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        jTabbedPane2.setSelectedIndex(1);
        func.clearTable(intakeTable);
        func.setTable("intake.txt", intakeTable);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        preInfo = new pmPresentationInfo();
        jTabbedPane2.setSelectedIndex(2);
        func.clearTable(presentationTable);
        func.setTable("presentation.txt", presentationTable);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        jTabbedPane2.setSelectedIndex(0);
        func.clearTable(projectTable);
        func.setTable("project.txt", projectTable);

    }//GEN-LAST:event_jLabel3MouseClicked

    private void intakeFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intakeFilterActionPerformed
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
        selectedIntakeCode = (String) intakeFilter.getSelectedItem();

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        studentTable.setRowSorter(sorter);

        if (!"All".equals(selectedIntakeCode)) {
            String regexSelectedIntakeCode = selectedIntakeCode.replace("(", "\\(").replace(")", "\\)");
            RowFilter<Object, Object> intakeFilter = RowFilter.regexFilter(regexSelectedIntakeCode, 5); // Assuming intake code is in column index 6
            sorter.setRowFilter(intakeFilter); // Filter based on selected intake code
            System.out.println(selectedIntakeCode);
        } else {
            sorter.setRowFilter(null); // Show all rows
        }
    }//GEN-LAST:event_intakeFilterActionPerformed

    private void intSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_intSearchFocusGained
        func.searchFocusGained(intSearch);
    }//GEN-LAST:event_intSearchFocusGained

    private void intSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_intSearchFocusLost
        func.searchFocusLost(intSearch);
    }//GEN-LAST:event_intSearchFocusLost

    private void subSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_subSearchFocusGained
        func.searchFocusGained(subSearch);
    }//GEN-LAST:event_subSearchFocusGained

    private void subSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_subSearchFocusLost
        func.searchFocusLost(subSearch);
    }//GEN-LAST:event_subSearchFocusLost

    private void feedbackSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_feedbackSearchFocusGained
        func.searchFocusGained(feedbackSearch);
    }//GEN-LAST:event_feedbackSearchFocusGained

    private void feedbackSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_feedbackSearchFocusLost
        func.searchFocusLost(feedbackSearch);
    }//GEN-LAST:event_feedbackSearchFocusLost

    private void preSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_preSearchFocusGained
        func.searchFocusGained(preSearch);
    }//GEN-LAST:event_preSearchFocusGained

    private void preSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_preSearchFocusLost
        func.searchFocusLost(preSearch);
    }//GEN-LAST:event_preSearchFocusLost

    private void preSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_preSearchKeyPressed
        func.searchKeyPressed(presentationTable, preSearch);
    }//GEN-LAST:event_preSearchKeyPressed

    private void intSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_intSearchKeyPressed
        func.searchKeyPressed(intakeTable, intSearch);
    }//GEN-LAST:event_intSearchKeyPressed

    private void subSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_subSearchKeyPressed
        func.searchKeyPressed(submissionTable, subSearch);
    }//GEN-LAST:event_subSearchKeyPressed

    private void feedbackSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_feedbackSearchKeyPressed
        func.searchKeyPressed(feedbackTable, feedbackSearch);
    }//GEN-LAST:event_feedbackSearchKeyPressed

    private void editPmProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPmProfileActionPerformed
        int confirmEditPM = JOptionPane.showConfirmDialog(null, "Are you sure to update your password?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                    
        if(confirmEditPM == JOptionPane.YES_OPTION){
                        
            try{
                String modifiedPassword = profilePassword.getText();
                File file = new File("user.txt");
                List<String> userDataList = Files.readAllLines(file.toPath());

                for (int i = 0; i < userDataList.size(); i++) {
                    String[] userData = userDataList.get(i).split(";");
                    if (userData.length >= 1 && userData[0].equals(pmID)) {
                        StringBuilder updatedLine = new StringBuilder();
                        updatedLine.append(pmID).append(";").append(modifiedPassword).append(";").append(userData[2]);
                        userDataList.set(i, updatedLine.toString());
                        break;
                    }
                }
                Files.write(file.toPath(), userDataList);
                
                JOptionPane.showMessageDialog(null, "Password updated successfully!");
                
            } catch (FileNotFoundException ex) {
//                JOptionPane.showMessageDialog();
            } catch (IOException e) {
            
            }
        }
    }//GEN-LAST:event_editPmProfileActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        jTabbedPane2.setSelectedIndex(7);
        
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        homepage.dispose();
        general_home generalHome = new general_home();
        generalHome.setVisible(true);
    }//GEN-LAST:event_jLabel20MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(pmHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pmHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pmHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pmHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    new pmHomePage("L003").setVisible(true);
//                } catch (IOException ex) {
//                    Logger.getLogger(pmHomePage.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private card.CustomPanel customPanel1;
    private card.CustomPanel customPanel2;
    private card.CustomPanel customPanel3;
    private javax.swing.JLabel displayName;
    private javax.swing.JButton editPmProfile;
    private javax.swing.JTextField feedbackSearch;
    private javax.swing.JTable feedbackTable;
    private avatar.ImageAvatar imageAvatar1;
    private javax.swing.JTextField intSearch;
    private javax.swing.JComboBox<String> intakeFilter;
    private javax.swing.JTable intakeTable;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
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
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JButton newBtn;
    private javaswingdev.chart.PieChart pieChart1;
    private javax.swing.JTextField preSearch;
    private javax.swing.JTable presentationTable;
    private javax.swing.JTextField profileEmail;
    private javax.swing.JTextField profileIC;
    private javax.swing.JTextField profileMajor;
    private javax.swing.JTextField profileMinor;
    private javax.swing.JTextField profileName;
    private avatar.ImageAvatar profileNav;
    private javax.swing.JPasswordField profilePassword;
    private javax.swing.JTextField profilePhone;
    private javax.swing.JTextField projSearch;
    protected javax.swing.JTable projectTable;
    private javax.swing.JTable studentTable;
    private javax.swing.JTextField subSearch;
    private javax.swing.JTable submissionTable;
    // End of variables declaration//GEN-END:variables
}
