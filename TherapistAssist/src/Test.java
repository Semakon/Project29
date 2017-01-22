import modelPackage.Client;
import modelPackage.GraphData;
import modelPackage.LoadData;
import modelPackage.SessionOwner;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Author:  Martijn
 * Date:    3-1-2017
 */
public class Test {

    private Thread t1;
    private Thread t2;
    private GraphData graphData;
    private ChartPanel panel;
    private Container pane;
    private String switchBtnText;

    public Test(Container pane) {
        this.pane = pane;
        this.graphData = new GraphData();
        this.switchBtnText = "Start";
        buildGUI();
    }

    public void start() {
        SessionOwner sessionOwner = new Client(1, "Test client");
        t1 = new LoadData(sessionOwner, panel, pane);
        t2 = new DataAddTest();
        t1.start();
        t2.start();
    }

    public void switchUpdateGraph() {
        switchBtnText = ((LoadData) t1).switchUpdateGraph() ? "Stop" : "Start";
    }

    public void buildGUI() {
        pane.setLayout(new FlowLayout());
        panel = graphData.buildLineGraphPanel("Test Graph");
        JButton switchBtn = new JButton(switchBtnText);
        switchBtn.addActionListener(e -> {
            switchUpdateGraph();
            switchBtn.setText(switchBtnText);
        });
        pane.add(switchBtn);
        pane.add(panel);
    }

    private static void createAndShowGUI() {
        // Create and set up window.
        JFrame frame = new JFrame("Line Graph Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 400));

        // Create and set up content pane.
        Test test = new Test(frame.getContentPane());
        test.start();

        // Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Test::createAndShowGUI);
    }

}
