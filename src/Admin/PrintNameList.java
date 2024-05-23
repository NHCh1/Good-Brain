/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Admin;

import com.formdev.flatlaf.FlatLaf;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.table.DefaultTableModel;
import TableController.TableAlignment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class PrintNameList extends javax.swing.JFrame {
    private Object[][] data;
    private String intakeCode;
    
    private static final double A4_WIDTH_INCHES = 8.27;
    private static final double A4_HEIGHT_INCHES = 11.69;

    public PrintNameList() {
        
    }

    public void showForm(Object[][] data, String intakeCode){
        initComponents();
        this.data = data;
        this.intakeCode = intakeCode;
        System.out.println(data);
        populateTable();
        setVisible(true);
    }
    
    private void populateTable(){
        TableAlignment alignment = new TableAlignment();
        alignment.alignTable(nameListTable);
        
        intakeCodeLabel.setText(intakeCode);
        DefaultTableModel model = (DefaultTableModel) nameListTable.getModel();
        model.setRowCount(0);
        
        int studentCount = 0;
        int groupCount = 1;
        
        for (int i = 0; i < data.length; i++) {
            Object[] rowData = new Object[4];
            rowData[0] = ++studentCount; // No. column
            rowData[1] = data[i][1]; // Student Name column
            rowData[2] = data[i][0]; // Student ID column
            rowData[3] = "Group " + groupCount; // Group column

            // Increment group number after every 10 students
            if (studentCount % 10 == 0) {
                groupCount++;
            }
            model.addRow(rowData);
        }
    }
    
    
        public void printPanel() {
        PrinterJob printer = PrinterJob.getPrinterJob();
        printer.setJobName(intakeCode);

        printer.setPrintable(new Printable() {
            @Override
            public int print(Graphics gr, PageFormat pf, int pageNum) {
                pf.setOrientation(PageFormat.LANDSCAPE);

                if (pageNum > 0) {
                    // Handle page numbers greater than 0
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) gr;
                g2d.translate(pf.getImageableX(), pf.getImageableY());

                // Print the JPanel (nameListPanel) to fit the A4 size
                double scaleX = pf.getImageableWidth() / nameListPanel.getWidth();
                double scaleY = pf.getImageableHeight() / nameListPanel.getHeight();
                double scale = Math.min(scaleX, scaleY);
                g2d.scale(scale, scale);
                nameListPanel.print(g2d);


                // Save the printed content to a PDF file
                File folder = new File("C:/Users/User/Documents/NetBeansProjects/newAGH/NameList/" + intakeCode + ".pdf");
                if (!folder.exists()) {
                    folder.mkdir(); // Create the folder if it doesn't exist
                }

                File file = new File(folder + intakeCode + ".pdf");
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return Printable.PAGE_EXISTS;
            }
        });

            try {
                printer.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    
//        public void printPanel() {
//            try {
//                // Create a new document
//                Document document = new Document();
//
//                // Create a PDF writer instance
//                String pdfFilePath = "C:/Users/User/Documents/NetBeansProjects/newAGH/NameList/" + intakeCode + ".pdf";
//                PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
//
//                // Open the document
//                document.open();
//
//                // Add the JPanel (nameListPanel) to the PDF document
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                BufferedImage image = new BufferedImage(nameListPanel.getWidth(), nameListPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
//                Graphics2D g2d = image.createGraphics();
//                nameListPanel.printAll(g2d);
//                g2d.dispose();
//                ImageIO.write(image, "png", baos);
//                Image pdfImage = Image.getInstance(baos.toByteArray());
//                document.add(pdfImage);
//
//                // Close the document
//                document.close();
//
//                System.out.println("PDF created successfully at " + pdfFilePath);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameListPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        nameListTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        intakeCodeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        nameListPanel.setOpaque(false);

        nameListTable.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        nameListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Student Name", "Student ID", "Group"
            }
        ));
        nameListTable.setRowHeight(30);
        nameListTable.setShowGrid(true);
        jScrollPane1.setViewportView(nameListTable);

        jLabel1.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel1.setText("GoOdBrAiN");

        jLabel2.setFont(new java.awt.Font("Bell MT", 1, 16)); // NOI18N
        jLabel2.setText("Intake Code:");

        intakeCodeLabel.setFont(new java.awt.Font("Bell MT", 0, 16)); // NOI18N

        javax.swing.GroupLayout nameListPanelLayout = new javax.swing.GroupLayout(nameListPanel);
        nameListPanel.setLayout(nameListPanelLayout);
        nameListPanelLayout.setHorizontalGroup(
            nameListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nameListPanelLayout.createSequentialGroup()
                .addGroup(nameListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nameListPanelLayout.createSequentialGroup()
                        .addGap(291, 291, 291)
                        .addComponent(jLabel1))
                    .addGroup(nameListPanelLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(nameListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(nameListPanelLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(intakeCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        nameListPanelLayout.setVerticalGroup(
            nameListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nameListPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(nameListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(intakeCodeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nameListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(nameListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(PrintNameList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrintNameList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrintNameList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrintNameList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrintNameList().setVisible(true);
                FlatLaf.registerCustomDefaultsSource("flatlafProperties");
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel intakeCodeLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel nameListPanel;
    private javax.swing.JTable nameListTable;
    // End of variables declaration//GEN-END:variables
}