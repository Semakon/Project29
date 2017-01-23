package modelPackage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public static final String FILE_PATH = "/home/martijn/Documents/Mod6Project29/";
    public static final String FILE_NAME = "client-1";
    public static final int LINE_SKIPS = 30;

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

        // Clear file
        try {
            File file = new File(FILE_PATH + FILE_NAME);
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the client data from a file and returns it as a list of integer arrays.
     * @return The data from the watch of the client as a List of Integer arrays.
     */
    public List<String[]> loadClientData() {
        List<String[]> sessionData = new ArrayList<>();
        File file = new File(FILE_PATH + FILE_NAME);
        try {
            Scanner scanner = new Scanner(file);

            // Skip first few lines
            for (int i = 0; i < LINE_SKIPS; i++) {
                if (scanner.hasNextLine()) scanner.nextLine();
            }

            // Skip next 7 strings
            for (int i = 1; i <= 7; i++) {
                if (scanner.hasNext()) scanner.next();
            }

            // Scan for data
            while (scanner.hasNext()) {
                // Skip next 6 strings
                for (int i = 1; i <= 6; i++) {
                    scanner.next();
                }

                // Save data to array
                String[] data = new String[2];

                // Heart rate
                data[0] = scanner.next();

                // Timestamp
                Date date = new Date(Long.parseLong(scanner.next()));
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                data[1] = formatter.format(date);

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
