package modelPackage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

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
    public static final String VERTICAL_UNIT = "Heart Rate";
    /** Horizontal unit display. */
    public static final String HORIZONTAL_UNIT = "Time";
    /**  */
    private ChartPanel chartPanel;


    public GraphData() {
        this.dataMap = new HashMap<>();
    }

    /**
     * Builds a line graph with this object's data on a given panel with a given name.
     * @param chartTitle The name of the chart.
     */
    public ChartPanel buildLineGraphPanel(String chartTitle) {
        // Create a line chart
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                chartTitle,
                HORIZONTAL_UNIT,
                VERTICAL_UNIT,
                createDataSet(),
                PlotOrientation.VERTICAL,
                true, // showLegend
                true, // createTooltip
                false // createURL
        );

        // Set the chart's range
        XYPlot plot = lineChart.getXYPlot();
        plot.getRangeAxis().setRange(50, 130);
        plot.setRangeZeroBaselineVisible(false);

        chartPanel = new ChartPanel(lineChart);
        return chartPanel;
    }

    /**
     * Updates the chart by recreating the dataset. The data map should be updated before doing this
     * or it will have no effect.
     */
    public void updateChart() {
        chartPanel.getChart().getXYPlot().setDataset(createDataSet());
    }

    /**
     * Creates a data set with this object's data and returns it.
     * @return a data set created with this object's data.
     */
    private XYDataset createDataSet() {
        XYSeriesCollection dataSet = new XYSeriesCollection();

        // Create an XYSeries for every client in the data map
        for (Client client : dataMap.keySet()) {
            XYSeries xySeries = new XYSeries(client.getName());
            List<String[]> list = dataMap.get(client);

            // Add the data to the series
            for (String[] a : list) {
                if (a.length == 2) {
                    Integer y = Integer.parseInt(a[0]);

                    String temp = a[1].replace(":", "");
                    Integer x = Integer.parseInt(temp);

                    xySeries.add(x, y);
                } else {
                    //TODO: implement runtime exception
                }
            }

            // Add the series to the dataset
            dataSet.addSeries(xySeries);
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
