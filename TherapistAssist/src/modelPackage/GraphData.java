package modelPackage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.*;

/**
 * The data of a graph of a session.
 *
 * Author:  Martijn
 * Date:    16-1-2017
 */
public class GraphData {

    /** Map of client to data (array: {Vertical unit, Horizontal unit}). */
    private Map<Client, List<String[]>> dataMap;
    /** Vertical unit display. */
    public static final String VERTICAL_UNIT = "Stress level";
    /** Horizontal unit display. */
    public static final String HORIZONTAL_UNIT = "Time (Minutes)";
    /** Baseline of horizontal unit. */


    public GraphData() {
        this.dataMap = new HashMap<>();
    }

    /**
     * Builds a line graph with this object's data on a given panel with a given name.
     * @param chartTitle The name of the chart.
     */
    public ChartPanel buildLineGraphPanel(String chartTitle) {
        // Create a line chart
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                HORIZONTAL_UNIT,
                VERTICAL_UNIT,
                createDataSet(),
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
        plot.getRangeAxis().setRange(50, 120);

        return new ChartPanel(lineChart);
    }

    /**
     * Creates a data set with this object's data and returns it.
     * @return a data set created with this object's data.
     */
    private DefaultCategoryDataset createDataSet() {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        for (Client client : dataMap.keySet()) {
            List<String[]> list = dataMap.get(client);
            for (String[] a : list) {
                if (a.length == 2) {
                    int verticalUnit = Integer.parseInt(a[0]);
                    String horizontalUnit = a[1];

                    dataSet.addValue(verticalUnit, client.getName(), horizontalUnit);
                } else {
                    //TODO: implement runtime exception
                }
            }
        }
        return dataSet;
    }

    /**
     * Sets the data of a Client to new data.
     * @param client The client whose data is changed.
     * @param newData The new data.
     */
    public void setData(Client client, List<String[]> newData) {
        for (Client c : dataMap.keySet()) {
            if (client.equals(c)) {
                dataMap.put(c, newData);
                return;
            }
        }
        dataMap.put(client, newData);
    }

}
