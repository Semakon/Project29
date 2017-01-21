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
public class Test extends Thread {

    private GraphData graphData;
    private ChartPanel panel;
    private Client client;
    private LoadData loadData;
    private Container pane;

    public Test(Container pane) {
        this.pane = pane;
        this.graphData = new GraphData();
        SessionOwner sessionOwner = new Client(1, "Test client");
        this.client = (Client)sessionOwner;
        loadData = new LoadData(sessionOwner);
        buildGUI();
    }

    @Override
    public void run() {
        while (true) {
            updateGraph();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateGraph() {
        loadData.loadClientData();
        graphData.setData(client, loadData.getSessionData());
        graphData.updatePanel(panel);
        pane.repaint();
    }

    private void addValues() {
        List<Integer[]> list1 = new ArrayList<>();
        List<Integer[]> list2 = new ArrayList<>();
        Client client1 = new Client(1, "Bert Zonneklaar", null);
        Client client2 = new Client(2, "Paul de Jong", null);

        Integer[] data1 = {40, 0};
        Integer[] data2 = {54, 1};
        Integer[] data3 = {31, 2};
        Integer[] data4 = {78, 3};
        Integer[] data5 = {89, 4};

        Integer[] data6 = {35, 4};
        Integer[] data7 = {12, 0};
        Integer[] data8 = {31, 1};
        Integer[] data9 = {28, 2};
        Integer[] data0 = {43, 3};

        list1.add(data1);
        list1.add(data2);
        list1.add(data3);
        list1.add(data4);
        list1.add(data5);

        list2.add(data6);
        list2.add(data7);
        list2.add(data8);
        list2.add(data9);
        list2.add(data0);

        graphData.setData(client1, list1);
        graphData.setData(client2, list2);
    }

    public void buildGUI() {
        panel = graphData.buildLineGraphPanel("Test Graph");
        pane.add(panel);
    }

    private static void createAndShowGUI() {
        // Create and set up window.
        JFrame frame = new JFrame("Line Graph Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 400));

        // Create and set up content pane.
        Thread test = new Test(frame.getContentPane());
        test.start();

        // Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Test::createAndShowGUI);
    }

}
