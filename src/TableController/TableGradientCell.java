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


public class TableGradientCell extends DefaultTableCellRenderer{
    
    private Color color1;
    private Color color2;
    private int x;
    private int width;
    private boolean isSelected;
    private int row;
    
    public TableGradientCell() {
        this(Color.decode("#009FFF"), Color.decode("#ec2F4B"));
    }
    
    public TableGradientCell(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
        setOpaque(false);
    }
    
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Rectangle cellRec = table.getCellRect(row, column, true);
        x = -cellRec.x;
        width = table.getWidth()-cellRec.x;
        this.isSelected = isSelected;
        this.row = row;
        return com;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();
        if(isSelected) {
            g2.setPaint(new GradientPaint(x, 0, color1, width, 0, color2));
            g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        } else if(row%2 == 0) {
//            Color gradientColor1 = FlatLaf.isLafDark() ? Color.decode("#4a4a49") : Color.decode("#fcfcfc");
//            Color gradientColor2 = FlatLaf.isLafDark() ? Color.decode("#3d3d3d") : Color.decode("#77f4fc");
            Color gradientColor1 = Color.decode("#fcfcfc");
            Color gradientColor2 = Color.decode("#77f4fc");
            g2.setPaint(new GradientPaint(x, 0, gradientColor1, width, 0, gradientColor2));
            g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
