import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Author:  martijn
 * Date:    22-1-17.
 */
public class DataAddTest extends Thread {

    //TODO: Fix no such file or directory
    public static final String FILE_PATH = " /home/martijn/Documents/Mod6Project29/client-1";

    @Override
    public void run() {
        try {
            for (int i = 0; i < 50; i++) {
                addRandomData(i);
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addRandomData(int i) throws IOException {
        FileWriter fw = null;
        try {
            Random rand = new Random();
            String data = "01-20 13:07:46.454  1522  1522 I MainActivity: " +
                    rand.nextInt(120) + " " + i;

            fw = new FileWriter(FILE_PATH, true);
            fw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) fw.close();
        }
    }

}
