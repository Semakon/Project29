import modelPackage.GraphData;
import modelPackage.LoadData;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Author:  Martijn
 * Date:    3-1-2017
 */
public class Test {

    private Thread t1;
    private Thread t2;
    private GraphData graphData;
    private String switchBtnText;

    public Test(Container pane) {
        this.graphData = new GraphData();
        this.switchBtnText = "Start";
        buildGUI(pane);
    }

    public void start() {
//        SessionOwner sessionOwner = new Client(1, "Test client");
//        t1 = new LoadData(sessionOwner, null); // TODO: fix null
//        t1.start();
    }

    public void switchUpdateGraph() {
        switchBtnText = ((LoadData) t1).switchUpdateGraph() ? "Stop" : "Start";
    }

    public void buildGUI(Container pane) {
        pane.setLayout(new FlowLayout());
        ChartPanel panel = graphData.buildLineGraphPanel("Test Graph");
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
