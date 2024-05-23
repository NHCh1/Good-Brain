/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableController;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class TableActionCellRender extends DefaultTableCellRenderer{
    
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object obj, boolean isSelected, boolean bln1, int row, int column){
        Component comp = super.getTableCellRendererComponent(jtable, obj, isSelected, bln1, row, column);
        
        PanelAction pa = new PanelAction();
        //Row not selected & is even number
        if (isSelected == false && row % 2 == 0){
            pa.setBackground(Color.WHITE);
        }
        else{
            pa.setBackground(comp.getBackground());
        }
        return pa;
    }
}
