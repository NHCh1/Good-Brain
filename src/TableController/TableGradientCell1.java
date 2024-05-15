/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableController;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class TableGradientCell1 extends DefaultTableCellRenderer{
    private Color colour1;
    private Color colour2;
    private int x;
    private int width;
    private boolean isSelected;
    private int row;
    
    public TableGradientCell1() {
        this(Color.decode("#c2e59c"), Color.decode("#64b3f4"));
    }

    public TableGradientCell1(Color color1, Color color2) {
        this.colour1 = color1;
        this.colour2 = color2;
        setOpaque(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Rectangle cellRec = table.getCellRect(row, column, true);
        x = -cellRec.x;
        width = table.getWidth() - cellRec.x;
        this.isSelected = isSelected;
        this.row = row;
        return comp;
    }

    @Override
    protected void paintComponent(Graphics grgh) {
        Graphics2D g2d = (Graphics2D) grgh.create();
        if (isSelected) {
            g2d.setPaint(new GradientPaint(x, 0, colour1, width, 0, colour2));
            g2d.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        } else if (row % 2 == 0) {
            Color gradientColor1 = Color.decode("#64b3f4");
            Color gradientColor2 = Color.decode("#c2e59c");
            g2d.setPaint(new GradientPaint(x,0,gradientColor1,width,0,gradientColor2));
        }
        else{
            g2d.setPaint(getBackground());
        }
//        g2d.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        g2d.dispose();
        super.paintComponent(grgh);
    }
    
    
//    protected void paintComponent(Graphics grgh) {
//        Graphics2D g2d = (Graphics2D) grgh.create();
//        if (isSelected) {
//            g2d.setPaint(new GradientPaint(x, 0, colour1, width, 0, colour2));
//            g2d.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
//        } else if (row % 2 == 0) {
//            g2d.setPaint(new GradientPaint(x, 0, Color.decode("#ECE9E6"), width, 0, Color.decode("#FFFFFF")));
//            g2d.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
//        }
//        g2d.dispose();
//        super.paintComponent(grgh);
//    }
}
