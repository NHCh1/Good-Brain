/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableController;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;


public class TableActionCellEditor extends javax.swing.DefaultCellEditor{

    private TableActionEvent event;

    
    public TableActionCellEditor(TableActionEvent event){
        super(new JCheckBox());
        this.event = event;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object obj, boolean bln, int row, int column){
        PanelAction pa = new PanelAction();
        pa.initEvent(event, row);
        pa.setBackground(jtable.getSelectionBackground());   
        return pa;
    }
}
