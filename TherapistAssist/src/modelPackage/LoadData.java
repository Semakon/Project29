package modelPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Loads session data from file to put in a line graph.
 *
 * Author:  martijn
 * Date:    20-1-17.
 */
public class LoadData {

    private SessionOwner sessionOwner;
    private List<Integer[]> sessionData;
    public static final String FILE_LOCATION = "/home/martijn/Documents/Mod6Project29/";
    public static final String FILE_PREFIX = "client-";

    public LoadData(SessionOwner sessionOwner) {
        this.sessionOwner = sessionOwner;
        this.sessionData = new ArrayList<>();
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

    public List<Integer[]> getSessionData() {
        return sessionData;
    }

}
