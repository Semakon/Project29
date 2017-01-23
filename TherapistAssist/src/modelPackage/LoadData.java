package modelPackage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Time;
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
    private boolean started = false;

    public static final String FILE_PATH = "/home/sam/Documents/mod6log.txt";

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
            File file = new File(FILE_PATH);
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Integer[]> loadClientData() {
        List<Integer[]> sessionData = new ArrayList<>();
        File file = new File(FILE_PATH);
        try {
            Scanner scanner = new Scanner(file);


            // Skip first two lines
            for (int i = 0; i < 30; i++) {
                if (scanner.hasNextLine()) scanner.nextLine();
            }

            // Skip next 7 strings
            for (int i = 1; i <= 7; i++) {
                if (scanner.hasNext()) scanner.next();
            }
            // Save the next int as start time
            int startTime = 0;
            if (scanner.hasNext()) startTime = Integer.parseInt(scanner.next());
            System.out.println(startTime);

            if(started == false) {
                String dateFormatted;
                String measureTime = "";
                Long time = System.currentTimeMillis();
                Date date = new Date(time);
                DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
                dateFormatted = formatter.format(date);
                dateFormatted = dateFormatted.substring(0, dateFormatted.length() - 7);
                System.out.println(dateFormatted);

                Thread.sleep(10);
                while (!dateFormatted.equals(measureTime)) {

                    scanner.next();
                    measureTime = scanner.next();
                    measureTime = measureTime.substring(0, measureTime.length() - 7);
                    System.out.println(measureTime);


                    // Skip next 6 strings
                    for (int i = 1; i <= 6; i++) {
                        scanner.next();

                    }
                    Thread.sleep(10);
                }
                started = true;
            }




            // Scan for data
            while (scanner.hasNext()) {
                // Skip next 6 strings
                for (int i = 1; i <= 6; i++) {
                    scanner.next();
                }

                // Save data to array
                Integer[] data = new Integer[2];
                data[0] = Integer.parseInt(scanner.next());
                data[1] = Integer.parseInt(scanner.next()) - startTime;

                // Add array to list of session data
                sessionData.add(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
