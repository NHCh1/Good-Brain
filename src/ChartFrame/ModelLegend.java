/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChartFrame;

import java.awt.Color;

public class ModelLegend {
    private String name;
    private Color color;
    private Color lightColor;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColorLight() {
        return lightColor;
    }

    public void setColorLight(Color colorLight) {
        this.lightColor = colorLight;
    }

    public ModelLegend(String name, Color color, Color colorLight) {
        this.name = name;
        this.color = color;
        this.lightColor = colorLight;
    }

    public ModelLegend() {
    }
}
