/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableController;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class ActionButton extends JButton{
    
    private boolean mousePress;
    
    public ActionButton(){
        setContentAreaFilled(false);    //remove border
        setBorder(new EmptyBorder(2,2,2,2));
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent me){
                mousePress = true;
            }

            @Override
            public void mouseReleased(MouseEvent me){
                mousePress = false;
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics grp){
        Graphics2D g2d = (Graphics2D)grp.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height);
        int x = (width-size) / 2;
        int y = (height-size) / 2;
        if (mousePress){
            g2d.setColor(new Color(174, 176, 175));
        }
        else{
            g2d.setColor(new Color(195, 201, 197));
        }
        g2d.fill(new Ellipse2D.Double(x, y,size,size));
        g2d.dispose();
        super.paintComponent(grp);
    }
    
}
