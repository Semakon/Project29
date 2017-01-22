package modelPackage;

import org.jfree.chart.ChartPanel;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Loads session data from file to put in a line graph.
 *
 * Author:  martijn
 * Date:    20-1-17.
 */
public class LoadData extends Thread {

    private boolean updateGraph;
    private SessionOwner sessionOwner;
    private List<Integer[]> sessionData;
    private GraphData graphData;
    private ChartPanel panel;
    private Container pane;
    public static final String FILE_LOCATION = "/home/martijn/Documents/Mod6Project29/";
    public static final String FILE_PREFIX = "client-";

    public LoadData(SessionOwner sessionOwner, ChartPanel panel, Container pane) {
        this.sessionOwner = sessionOwner;
        this.graphData = new GraphData();
        this.panel = panel;
        this.pane = pane;
        this.sessionData = new ArrayList<>();
        this.updateGraph = true;
    }

    @Override
    public void run() {
        while(updateGraph) {
            loadClientData();
            graphData.setData((Client) sessionOwner, sessionData);
            graphData.updatePanel(panel);
            pane.repaint();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadClientData() {
        String fileName = FILE_PREFIX;
        if (sessionOwner instanceof Client) {
            fileName += ((Client) sessionOwner).getId();
        } else if (sessionOwner instanceof Group) {
            Group group = (Group)sessionOwner;
            if (group.getParticipants().size() >= 1) {
                fileName += group.getParticipants().get(0).getId();
            } else {
                System.out.println("Not supported");
            }
        } else {
            System.out.println("Session owner should be client or group.");
        }

        File file = new File(FILE_LOCATION + fileName);
        try {
            Scanner scanner = new Scanner(file);

            // Skip first two lines
            if (scanner.hasNextLine()) scanner.nextLine();
            if (scanner.hasNextLine()) scanner.nextLine();

            // Scan for data
            while (scanner.hasNext()) {
                // Skip next 6 strings
                for (int i = 1; i <= 6; i++) {
                    scanner.next();
                }

                // Save data to array
                Integer[] data = new Integer[2];
                data[0] = Integer.parseInt(scanner.next());
                data[1] = Integer.parseInt(scanner.next());

                // Add array to list of session data
                sessionData.add(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Print arrays to the console (test)
//        for (Integer[] i : sessionData) {
//            System.out.println(Arrays.toString(i));
//        }
    }

    public boolean switchUpdateGraph() {
        this.updateGraph = !this.updateGraph;
        return this.updateGraph;
    }

    public boolean isUpdateGraph() {
        return updateGraph;
    }

    public List<Integer[]> getSessionData() {
        return sessionData;
    }

}
