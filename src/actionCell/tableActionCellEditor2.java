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
public class tableActionCellEditor2 extends DefaultCellEditor{
    private tableActionEvent2 event;
    
    public tableActionCellEditor2(tableActionEvent2 event){
        super(new JCheckBox());
        this.event = event;
    }
    
    @Override
    public Component getTableCellEditorComponent (JTable jtable, Object o, boolean bln, int row, int column){
        panelAction2 action = new panelAction2();
        action.iniEvent(event, row);
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }
}
