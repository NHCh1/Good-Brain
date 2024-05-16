/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ChartFrame;

import ChartFrame.LegendItem;
import ChartFrame.ModelChart;
import ChartFrame.ModelLegend;
import EmptyChart.BlankPlotChart;
import EmptyChart.BlankPlotChartRender;
import EmptyChart.SeriesSize;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

    public class Chart extends javax.swing.JPanel {
        DecimalFormat df = new DecimalFormat("#,##0.##");
        private List<ModelLegend> legends = new ArrayList<>();
        private List<ModelChart> model = new ArrayList<>();
        private final int seriesSize = 18;
        private final int seriesSpace = 10;
        private final Animator animator;
        private float animate;
        private String showLabel;
        private Point labelLocation = new Point();

        public Chart() {
            initComponents();
            TimingTarget target = new TimingTargetAdapter() {
                @Override
                public void timingEvent(float fraction) {
                    animate = fraction;
                    repaint();
                }
            };
            animator = new Animator(800, target);
            animator.setResolution(0);
            animator.setAcceleration(0.5f);
            animator.setDeceleration(0.5f);
            blankPlotChart.setBlankPlotChartRender(new BlankPlotChartRender(){
                @Override
                public int getMaxLegend() {
                    return legends.size();
                }

                @Override
                public String getLabelText(int index) {
                    return model.get(index).getLabel();
                }

                @Override
                public void renderSeries(BlankPlotChart bpchart, Graphics2D g2, SeriesSize size, int index) {
                    double totalSeriesWidth = (seriesSize * legends.size()) + (seriesSpace * (legends.size() - 1));
                    double x = (size.getWidth() - totalSeriesWidth) / 2;
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
                    for (int i = 0; i < legends.size(); i++) {
                        ModelLegend legend = legends.get(i);
                        double seriesValues = bpchart.getSeriesValuesOf(model.get(index).getValues()[i], size.getHeight()) * animate;
                        int s = seriesSize / 2;
                        int sy = seriesSize / 3;
                        int px[] = {(int) (size.getX() + x), (int) (size.getX() + x + s), (int) (size.getX() + x + s), (int) (size.getX() + x)};
                        int py[] = {(int) (size.getY() + size.getHeight() - seriesValues), (int) (size.getY() + size.getHeight() - seriesValues + sy), (int) (size.getY() + size.getHeight() + sy), (int) (size.getY() + size.getHeight())};
                        GradientPaint gra = new GradientPaint((int) (size.getX() + x) - s, 0, legend.getColorLight(), (int) (size.getX() + x + s), 0, legend.getColor());
                        g2.setPaint(gra);
                        g2.fillPolygon(px, py, px.length);
                        int px1[] = {(int) (size.getX() + x + s), (int) (size.getX() + x + seriesSize), (int) (size.getX() + x + seriesSize), (int) (size.getX() + x + s)};
                        int py1[] = {(int) (size.getY() + size.getHeight() - seriesValues + sy), (int) (size.getY() + size.getHeight() - seriesValues), (int) (size.getY() + size.getHeight()), (int) (size.getY() + size.getHeight() + sy)};
                        g2.setColor(legend.getColorLight());
                        g2.fillPolygon(px1, py1, px1.length);
                        int px2[] = {(int) (size.getX() + x), (int) (size.getX() + x + s), (int) (size.getX() + x + seriesSize), (int) (size.getX() + x + s)};
                        int py2[] = {(int) (size.getY() + size.getHeight() - seriesValues), (int) (size.getY() + size.getHeight() - seriesValues - sy), (int) (size.getY() + size.getHeight() - seriesValues), (int) (size.getY() + size.getHeight() - seriesValues + sy)};
                        g2.fillPolygon(px2, py2, px2.length);
                        x += seriesSpace + seriesSize;
                    }
                    if (showLabel != null) {
                        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                        Dimension s = getLabelWidth(showLabel, g2);
                        int space = 3;
                        int spaceTop = 5;
                        g2.setColor(new Color(30, 30, 30));
                        g2.fillRoundRect(labelLocation.x - s.width / 2 - 3, labelLocation.y - s.height - space * 2 - spaceTop, s.width + space * 2, s.height + space * 2, 10, 10);
                        g2.setColor(new Color(200, 200, 200));
                        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                        g2.drawString(showLabel, labelLocation.x - s.width / 2, labelLocation.y - spaceTop - space * 2);
                    }
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }

                @Override
                public void renderSeries(BlankPlotChart bpchart, Graphics2D g2d, SeriesSize size, int index, List<Path2D.Double> gra) {
                }

                @Override
                public void renderGraphics(Graphics2D g2d, List<Path2D.Double> gra) {

                }

                @Override
                public boolean mouseMoving(BlankPlotChart bpchart, MouseEvent evt, Graphics2D g2, SeriesSize size, int index) {
                    double totalSeriesWidth = (seriesSize * legends.size()) + (seriesSpace * (legends.size() - 1));
                    double x = (size.getWidth() - totalSeriesWidth) / 2;
                    for (int i = 0; i < legends.size(); i++) {
                        double seriesValues = bpchart.getSeriesValuesOf(model.get(index).getValues()[i], size.getHeight()) * animate;
                        int s = seriesSize / 2;
                        int sy = seriesSize / 3;
                        int px[] = {(int) (size.getX() + x), (int) (size.getX() + x + s), (int) (size.getX() + x + seriesSize), (int) (size.getX() + x + seriesSize), (int) (size.getX() + x + s), (int) (size.getX() + x)};
                        int py[] = {(int) (size.getY() + size.getHeight() - seriesValues), (int) (size.getY() + size.getHeight() - seriesValues - sy), (int) (size.getY() + size.getHeight() - seriesValues), (int) (size.getY() + size.getHeight()), (int) (size.getY() + size.getHeight() + sy), (int) (size.getY() + size.getHeight())};
                        if (new Polygon(px, py, px.length).contains(evt.getPoint())) {
                            double data = model.get(index).getValues()[i];
                            showLabel = df.format(data);
                            labelLocation.setLocation((int) (size.getX() + x + s), (int) (size.getY() + size.getHeight() - seriesValues - sy));
                            bpchart.repaint();
                            return true;
                        }
                        x += seriesSpace + seriesSize;
                    }
                    return false;
                }
            });        
        }

        public void addLegend(String name, Color color, Color color1) {
            ModelLegend data = new ModelLegend(name, color, color1);
            legends.add(data);
            panelLegend.add(new LegendItem(data));
            panelLegend.repaint();
            panelLegend.revalidate();
        }

        public void addData(ModelChart data) {
            model.add(data);
            blankPlotChart.setLabelCount(model.size());
            double max = data.getMaxValues();
            if (max > blankPlotChart.getMaxValues()) {
                blankPlotChart.setMaxValues(max);
            }
        }

        public void clear() {
            animate = 0;
            showLabel = null;
            blankPlotChart.setLabelCount(0);
            model.clear();
            repaint();
        }

        public void start() {
            showLabel = null;
            if (!animator.isRunning()) {
                animator.start();
            }
        }

        private Dimension getLabelWidth(String text, Graphics2D g2) {
            FontMetrics ft = g2.getFontMetrics();
            Rectangle2D rec2d = ft.getStringBounds(text, g2);
            return new Dimension((int) rec2d.getWidth(), (int) rec2d.getHeight());
        }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLegend = new javax.swing.JPanel();
        blankPlotChart = new EmptyChart.BlankPlotChart();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLegend, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
            .addComponent(blankPlotChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(blankPlotChart, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(panelLegend, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private EmptyChart.BlankPlotChart blankPlotChart;
    private javax.swing.JPanel panelLegend;
    // End of variables declaration//GEN-END:variables
}
