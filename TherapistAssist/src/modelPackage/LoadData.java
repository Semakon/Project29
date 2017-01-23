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
    private Session session;
    private Container pane;

    public static final String FILE_PATH = "/home/martijn/Documents/Mod6Project29/client-1";

    public LoadData(Session session, Container pane) {
        this.session = session;
        this.pane = pane;
        this.updateGraph = true;
    }

    @Override
    public void run() {
        Client client = null;
        if (session.getOwner() instanceof Group) {
            Group group = (Group)session.getOwner();
            if (group.getParticipants().size() >= 1) {
                client = group.getParticipants().get(0);
            } else {
                //TODO: Exception: empty group
            }
        } else {
            client = ((Client)session.getOwner());
        }
        GraphData graphData = session.getGraphData();
        while(updateGraph) {
            graphData.setData(client, loadClientData());
            pane.removeAll();
            pane.add(graphData.buildLineGraphPanel((session.getName())));
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
