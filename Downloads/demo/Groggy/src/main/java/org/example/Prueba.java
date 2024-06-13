package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class Prueba {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        DefaultPieDataset<String> datasetPastel = new DefaultPieDataset<String>();
        datasetPastel.setValue("Martin", 1);
        datasetPastel.setValue("Sergio", 10);
        datasetPastel.setValue("Saul", 3);
        datasetPastel.setValue("Perez", 20);
        datasetPastel.setValue("Teodoro", 17);
        datasetPastel.setValue("Daniel", 25);
        datasetPastel.setValue("Juan", 21);

        JFreeChart pieChart = ChartFactory.createPieChart(
                "", // Título del gráfico
                datasetPastel,
                true, // Incluir leyenda
                false,
                false);

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setLabelGenerator(null);
        plot.setOutlineVisible(false);

        ChartPanel chartPanelPastel = new ChartPanel(pieChart);
        chartPanelPastel.setSize(380, 210);
        chartPanelPastel.setLocation(100, 40);


        panel.setSize(1000, 800);
        panel.setLayout(null);
        panel.setLocation(0,0);
        panel.setBackground(Color.white);
        panel.add(chartPanelPastel);
        panel.setVisible(true);


        frame.setSize(1000, 800);
        frame.setLocation(0,0);
        frame.setLayout(null);
        frame.add(panel);
        frame.setVisible(true);
    }


}
