import org.jfree.chart.*;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Author:  Martijn
 * Date:    3-1-2017
 */
public class Test {

    public Test() {

    }

    private ChartPanel lineGraph() {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Test Chart",
                "X",
                "Y",
                createDataSet(),
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        return new ChartPanel(chart);
    }

    private XYDataset createDataSet() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries s1 = new XYSeries("Bert");
        XYSeries s2 = new XYSeries("Paul");

        Random rand = new Random();

        for (int i = 1; i <= 100; i++) {
            s1.add(i, (rand.nextInt(90 - 60) + 1) + 60);
            s2.add(i, (rand.nextInt(90 - 60) + 1) + 60);
        }

        dataset.addSeries(s1);
        dataset.addSeries(s2);

        return dataset;
    }

    public void buildGUI(Container pane) {
        pane.setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Coordinates");
        ChartPanel panel = lineGraph();

        panel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent e) {
                ChartEntity ce = e.getEntity();
                if (ce instanceof XYItemEntity) {
                    XYItemEntity ie = (XYItemEntity) ce;
                    XYDataset dataset = ie.getDataset();
                    if (dataset instanceof XYSeriesCollection) {
                        XYSeriesCollection d = (XYSeriesCollection) dataset;
                        int s = ie.getSeriesIndex();
                        int i = ie.getItem();
                        String k = d.getSeries(s).getKey().toString();

                        // Print X and Y coordinates
                        String text = k + ": X:" + d.getX(s, i) + ", Y:" + d.getY(s, i);
                        System.out.println(text);
                        lbl.setText(text);
                    }
                }
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent e) {
            }
        });

        pane.add(lbl, BorderLayout.NORTH);
        pane.add(panel, BorderLayout.CENTER);
    }

    private static void createAndShowGUI() {
        // Create and set up window.
        JFrame frame = new JFrame("Line Graph Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 800));

        // Create and set up content pane.
        Test test = new Test();
        test.buildGUI(frame.getContentPane());

        // Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Test::createAndShowGUI);
    }

}
