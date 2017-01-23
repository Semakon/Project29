package modelPackage;

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
    private GraphData graphData;
    private Container pane;
    public static final String FILE_PATH = "C:\\Users\\Martijn\\IdeaProjects\\" +
            "Project29\\TherapistAssist\\src\\client-1";

    public LoadData(SessionOwner sessionOwner, Container pane) {
        this.sessionOwner = sessionOwner;
        this.graphData = new GraphData();
        this.pane = pane;
        this.updateGraph = true;
    }

    @Override
    public void run() {
        while(updateGraph) {
            graphData.setData((Client) sessionOwner, loadClientData());
            pane.removeAll();
            pane.add(graphData.buildLineGraphPanel(((Client) sessionOwner).getName()));
            pane.revalidate();
            pane.repaint();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Integer[]> loadClientData() {
        List<Integer[]> sessionData = new ArrayList<>();
        File file = new File(FILE_PATH);
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
        return sessionData;
    }

    public void setUpdateGraph(boolean updateGraph) {
        this.updateGraph = updateGraph;
    }

    public boolean switchUpdateGraph() {
        this.updateGraph = !this.updateGraph;
        return this.updateGraph;
    }

}
