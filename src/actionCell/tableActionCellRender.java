/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actionCell;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author User
 */
public class tableActionCellRender extends DefaultTableCellRenderer{
    
    private boolean isOneButton;    
    public tableActionCellRender(boolean isOneButton) {
        this.isOneButton = isOneButton;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSelected, boolean bln1, int row, int column){
//        Component com = super.getTableCellRendererComponent(jtable, o, isSelected, bln1, row, column);
        if(isOneButton) {
            Component com = super.getTableCellRendererComponent(jtable, o, isSelected, bln1, row, column);
            panelAction2 action = new panelAction2();
            if(isSelected == false && row%2 == 0){
                action.setBackground(Color.WHITE);
            } else {
                action.setBackground(com.getBackground());
            }
            return action;
        } else {
            Component com = super.getTableCellRendererComponent(jtable, o, isSelected, bln1, row, column);
            panelAction action = new panelAction();
            if(isSelected == false && row%2 == 0){
                action.setBackground(Color.WHITE);
            } else {
                action.setBackground(com.getBackground());
            }
            return action;
        }
//        return action;
    }
}
