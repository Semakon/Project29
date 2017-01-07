package control;

import model.TherapistAssist;
import view.TherapistAssistGUI;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Author:  Martijn
 * Date:    5-1-2017
 */
public class TherapistAssistControl implements Observer {

    private TherapistAssistGUI view;
    private TherapistAssist model;

    public TherapistAssistControl() {
        this.view = new TherapistAssistGUI("Agnes de Wit");
        this.model = new TherapistAssist();
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Input: " + arg);
    }

    /**
     * The static method that creates the basis for the GUI. Should be in the controller and called
     * from the main method.
     */
    private static void createAndShowGUI() {
        // Create and set up window.
        JFrame frame = new JFrame("TestUserProfile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up content pane.
        TherapistAssistGUI gui = new TherapistAssistGUI("Agnes de Wit");
        gui.buildGUI(frame.getContentPane());

        // Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new TherapistAssistControl();
        javax.swing.SwingUtilities.invokeLater(TherapistAssistControl::createAndShowGUI);
    }

}
