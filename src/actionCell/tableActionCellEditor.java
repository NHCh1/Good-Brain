/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actionCell;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author User
 */
public class tableActionCellEditor extends DefaultCellEditor{
    
    private tableActionEvent event;
    
    public tableActionCellEditor(tableActionEvent event){
        super(new JCheckBox());
        this.event = event;
    }
    
    @Override
    public Component getTableCellEditorComponent (JTable jtable, Object o, boolean bln, int row, int column){
        panelAction action = new panelAction();
        action.iniEvent(event, row);
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }

}
