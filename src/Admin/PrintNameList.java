/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Admin;

import TableController.TableAlignment;
import com.formdev.flatlaf.FlatLaf;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.print.PageFormat;
//import java.awt.print.Printable;
//import java.awt.print.PrinterException;
//import java.awt.print.PrinterJob;
//import javax.swing.table.DefaultTableModel;
//import TableController.TableAlignment;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Stream;

public class PrintNameList extends javax.swing.JFrame {
    private Object[][] data;
    private String intakeCode;

    public PrintNameList() {
        initComponents();
    }

    public void showForm(Object[][] data, String intakeCode) {
        this.data = data;
        this.intakeCode = intakeCode;
        System.out.println(data);
        populateTable();
        setVisible(true);
    }

    private void populateTable() {
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

            // Increment group number after every 20 students
            if (studentCount % 20 == 0) {
                groupCount++;
            }
            model.addRow(rowData);
        }
    }

    public void generatePdf() {
        Document document = new Document(PageSize.A4.rotate());
        try {
            String filePath = "C:/Users/User/Documents/NetBeansProjects/Good-Brain/src/NameList/" + intakeCode + ".pdf";
            File folder = new File(filePath).getParentFile();
            if (!folder.exists()) {
                folder.mkdirs(); // Create the folder if it doesn't exist
            }

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);

            Paragraph header = new Paragraph("GoodBrain", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Paragraph title = new Paragraph("Intake: " + intakeCode, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(Chunk.NEWLINE);

            // Create table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 4, 4, 2});

            // Add table headers
            addTableHeader(table);

            // Add table data
            addRows(table);

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    private void addTableHeader(PdfPTable table) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
        Stream.of("No", "Student Name", "Student ID", "Group").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(1);
            header.setPhrase(new Phrase(columnTitle, headerFont));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_MIDDLE);
            header.setMinimumHeight(30);
            table.addCell(header);
        });
    }

//    private void addRows(PdfPTable table) {
//        int studentCount = 0;
//        int groupCount = 1;
//
//        for (Object[] rowData : data) {
//            table.addCell(String.valueOf(++studentCount));
//            table.addCell(String.valueOf(rowData[1]));
//            table.addCell(String.valueOf(rowData[0]));
//            table.addCell("Group " + groupCount);
//
//            // Increment group number after every 20 students
//            if (studentCount % 20 == 0) {
//                groupCount++;
//            }
//        }
//    }
    
    private void addRows(PdfPTable table) {
        int studentCount = 0;
        int groupCount = 1;

        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 13, BaseColor.BLACK);
 
        for (Object[] rowData : data) {
            PdfPCell cell1 = new PdfPCell(new Phrase(String.valueOf(++studentCount)));
            PdfPCell cell2 = new PdfPCell(new Phrase(String.valueOf(rowData[1])));
            PdfPCell cell3 = new PdfPCell(new Phrase(String.valueOf(rowData[0])));
            PdfPCell cell4 = new PdfPCell(new Phrase("Group " + groupCount, cellFont));

            // Align content to the center
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Set cell height
            cell1.setMinimumHeight(30);
            cell2.setMinimumHeight(30);
            cell3.setMinimumHeight(30);
            cell4.setMinimumHeight(30);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);

            // Increment group number after every 20 students
            if (studentCount % 20 == 0) {
                groupCount++;
            }
        }
    }
    
    
//    private Object[][] data;
//    private String intakeCode;
//    
//    private static final double A4_WIDTH_INCHES = 8.27;
//    private static final double A4_HEIGHT_INCHES = 11.69;
//
//    public PrintNameList() {
//        
//    }
//
//    public void showForm(Object[][] data, String intakeCode){
//        initComponents();
//        this.data = data;
//        this.intakeCode = intakeCode;
//        System.out.println(data);
//        populateTable();
//        setVisible(true);
//    }
//    
//    private void populateTable(){
//        TableAlignment alignment = new TableAlignment();
//        alignment.alignTable(nameListTable);
//        
//        intakeCodeLabel.setText(intakeCode);
//        DefaultTableModel model = (DefaultTableModel) nameListTable.getModel();
//        model.setRowCount(0);
//        
//        int studentCount = 0;
//        int groupCount = 1;
//        
//        for (int i = 0; i < data.length; i++) {
//            Object[] rowData = new Object[4];
//            rowData[0] = ++studentCount; // No. column
//            rowData[1] = data[i][1]; // Student Name column
//            rowData[2] = data[i][0]; // Student ID column
//            rowData[3] = "Group " + groupCount; // Group column
//
//            // Increment group number after every 10 students
//            if (studentCount % 10 == 0) {
//                groupCount++;
//            }
//            model.addRow(rowData);
//        }
//    }
//    
//    
//        public void printPanel() {
//        PrinterJob printer = PrinterJob.getPrinterJob();
//        printer.setJobName(intakeCode);
//
//        printer.setPrintable(new Printable() {
//            @Override
//            public int print(Graphics gr, PageFormat pf, int pageNum) {
//                pf.setOrientation(PageFormat.LANDSCAPE);
//
//                if (pageNum > 0) {
//                    // Handle page numbers greater than 0
//                    return Printable.NO_SUCH_PAGE;
//                }
//
//                Graphics2D g2d = (Graphics2D) gr;
//                g2d.translate(pf.getImageableX(), pf.getImageableY());
//
//                // Print the JPanel (nameListPanel) to fit the A4 size
//                double scaleX = pf.getImageableWidth() / nameListPanel.getWidth();
//                double scaleY = pf.getImageableHeight() / nameListPanel.getHeight();
//                double scale = Math.min(scaleX, scaleY);
//                g2d.scale(scale, scale);
//                nameListPanel.print(g2d);
//
//
//                // Save the printed content to a PDF file
////                File folder = new File("C:/Users/User/Documents/NetBeansProjects/Good-Brain/NameList/" + intakeCode + ".pdf");
//                File folder = new File("/src/NameList/" + intakeCode + ".pdf");
//                if (!folder.exists()) {
//                    folder.mkdir(); // Create the folder if it doesn't exist
//                }
//
//                File file = new File(folder + intakeCode + ".pdf");
//                try (FileOutputStream fos = new FileOutputStream(file)) {
//                    fos.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                return Printable.PAGE_EXISTS;
//            }
//        });
//
//            try {
//                printer.print();
//            } catch (PrinterException ex) {
//                ex.printStackTrace();
//            }
//        }
    
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
                        .addGap(252, 252, 252)
                        .addComponent(jLabel1))
                    .addGroup(nameListPanelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(nameListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(nameListPanelLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(intakeCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        nameListPanelLayout.setVerticalGroup(
            nameListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nameListPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(nameListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(intakeCodeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nameListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nameListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
