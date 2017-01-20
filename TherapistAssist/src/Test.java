import modelPackage.Client;
import modelPackage.GraphData;
import modelPackage.LoadData;
import modelPackage.SessionOwner;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:  Martijn
 * Date:    3-1-2017
 */
public class Test {

    private GraphData graphData;

    public Test() {
        this.graphData = new GraphData();
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

    public void buildGUI(Container pane) {
        ChartPanel panel = graphData.buildLineGraphPanel("Test Graph");
        pane.add(panel);
    }

    private static void createAndShowGUI() {
        // Create and set up window.
        JFrame frame = new JFrame("Line Graph Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 400));

        // Create and set up content pane.
        Test test = new Test();
        test.buildGUI(frame.getContentPane());

        // Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Test::createAndShowGUI);
        SessionOwner sessionOwner = new Client(1, "Test client");
        Thread t1 = new LoadData(sessionOwner);
    }

}
