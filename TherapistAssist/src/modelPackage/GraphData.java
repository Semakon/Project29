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
    private Map<Client, List<Integer[]>> dataMap;
    /** Vertical unit display. */
    public static final String VERTICAL_UNIT = "Stress level";
    /** Horizontal unit display. */
    public static final String HORIZONTAL_UNIT = "Time (Minutes)";
    /** Baseline of horizontal unit. */
    public static final int BASELINE = 75;
    /** Maximum session time in a graph in minutes. */
    public static final int MAX_SESSION_TIME = 60; // in minutes


    public GraphData() {
        this.dataMap = new HashMap<>();

        // Create baseline using a fake client
//        Client fakeClient = new Client(-1, "Baseline");

        // Create the list of data for the baseline
//        List<Integer[]> baseline = new ArrayList<>();
//        for (int i = 0; i <= MAX_SESSION_TIME; i++) {
//            Integer[] tempData = {BASELINE, i};
//            baseline.add(tempData);
//        }

        // Add the baseline data to the data map using the fake client as key
//        this.dataMap.put(fakeClient, baseline);
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
            List<Integer[]> list = dataMap.get(client);
            for (Integer[] a : list) {
                if (a.length == 2) {
                    // dataSet.addValue(Vertical unit, Client name, Horizontal unit);
                    dataSet.addValue(a[0], client.getName(), a[1]);
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
    public void setData(Client client, List<Integer[]> newData) {
//        System.out.println("\nnewData:");
//        for (Integer[] i : newData) {
//            System.out.println(Arrays.toString(i));
//        }
        for (Client c : dataMap.keySet()) {
            if (client.equals(c)) {

                dataMap.put(c, newData);

                // Print arrays to the console (test)
//                System.out.println("\ndataMap contents:");
//                for (Integer[] i : dataMap.get(c)) {
//                    System.out.println(Arrays.toString(i));
//                }
                return;
            }
        }
        dataMap.put(client, newData);
    }

    public Map<Client, List<Integer[]>> getDataMap() {
        return dataMap;
    }
}
