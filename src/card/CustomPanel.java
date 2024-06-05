/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package card;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class CustomPanel extends JPanel{
    
    private JLabel titleLabel;
    private JLabel dataLabel;
    private JLabel iconLabel;
    private int borderRadius = 25;
    
    private String title;
    private String data;
    private Icon icon;
    
    // Define gradient colors
    private final Color defaultStartColor = Color.DARK_GRAY;
    private final Color defaultEndColor = Color.cyan;
    private Color gradientStartColor;
    private Color gradientEndColor;

    public CustomPanel() {
        // Initialize the components
        titleLabel = new JLabel();
        dataLabel = new JLabel();
        iconLabel = new JLabel();

//        rippleEffect.setRippleColor(new Color(220, 220, 220));

        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);

        // Add the title label to the top left
        titleLabel = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);
        // set the font foe title label
        Font titleLabelFont = new Font("Arial", Font.BOLD, 25); // Example font: Arial, bold, size 14
        titleLabel.setForeground(FlatLaf.isLafDark() ? Color.WHITE : Color.CYAN);
        titleLabel.setFont(titleLabelFont);
        

        // Add the data label to the bottom left
        dataLabel = new JLabel();
        gbc.gridy = 1;
        add(dataLabel, gbc);
        // Set font for dataLabel
        Font dataLabelFont = new Font("Arial", Font.PLAIN, 16); // Example font: Arial, plain, size 12
        dataLabel.setForeground(FlatLaf.isLafDark() ? Color.white : Color.CYAN);
        dataLabel.setFont(dataLabelFont);

        // Add the icon label to the right
        iconLabel = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 1.0; 
        gbc.anchor = GridBagConstraints.EAST;
        add(iconLabel, gbc);

        // Optional: Set some padding around the components
        setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
        setOpaque(false); 
        
//        setBorder(null);
            
    }

    // Setters to update the labels and icon
    public void setTitle(String title) {
        titleLabel.setText(title);
    }
    
    public String getTitle() {
        return title;
    }

    public void setData(String data) {
        dataLabel.setText(data);
    }
    
    public String getData() {
        return data;
    }

    public void setIcon(Icon icon) {
        iconLabel.setIcon(icon);
    }
    
    public Icon getIcon() {
        return icon;
    }
    
    public void setStartColor(Color color) {
        this.gradientStartColor = color;
    }
    
    public void setEndColor(Color color) {
        this.gradientEndColor = color;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Create a gradient paint
        GradientPaint gradientPaint = new GradientPaint(0, 0, 
                gradientStartColor == null ? defaultStartColor : gradientStartColor, 
                getWidth(), getHeight(), 
                gradientEndColor == null ? defaultEndColor : gradientEndColor);

        // Create rounded rectangle shape
        Shape roundedRectangle = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);

        // Set the color for the background
//        g2d.setColor(getBackground());
        g2d.setPaint(gradientPaint);
        g2d.fill(roundedRectangle);

        // Clean up graphics context
        g2d.dispose();
    }
    
//    @Override
//    protected void paintBorder(Graphics g) {
//        super.paintBorder(g);
//        Graphics2D g2d = (Graphics2D) g.create();
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        // Create rounded rectangle shape for the border
//        Shape roundedRectangle = new RoundRectangle2D.Double(0, -1, getWidth(), getHeight(), borderRadius, borderRadius);
//
//        // Set the color for the border
//        g2d.setColor(getForeground());
//        g2d.draw(roundedRectangle);
//
//        // Clean up graphics context
//        g2d.dispose();
//    }
}
