package view;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

/**
 * Author:  martijn
 * Date:    24-1-17.
 */
public class CoordinatesChartMouseListener implements ChartMouseListener {

    private JLabel lbl;

    public CoordinatesChartMouseListener(JLabel lbl) {
        this.lbl = lbl;
    }

    /**
     * Prints the X and Y coordinates of the clicked series to a given label.
     * @param e The ChartMouseEvent with all the information about the event.
     */
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

                String timeStamp = d.getX(s, i).toString();
                if (timeStamp.length() >= 6) {
                    timeStamp = timeStamp.substring(0, 2) + ":" + timeStamp.substring(2, 4) + ":"
                            + timeStamp.substring(4);
                }

                // Print X and Y coordinates
                String text = k + " - Time stamp: " + timeStamp + ", Heart Rate: " + d.getY(s, i);
                System.out.println(text);
                lbl.setText(text);
            }
        }
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent e) {}
}
