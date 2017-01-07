package control;

import model.Client;
import view.ViewTest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.List;

/**
 * Author:  Martijn
 * Date:    7-1-2017
 */
public class ControlTest implements Observer {

    private List<Client> clients;
    private ViewTest gui;

    public ControlTest(ViewTest gui) {
        clients = new ArrayList<>();
        this.gui = gui;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    private static void createAndShowGUI() {
        // Create and set up window.
        JFrame frame = new JFrame("CardsLayout Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 400));

        // Create and set up content pane.
        ViewTest test = new ViewTest();
        test.buildGUI(frame.getContentPane());

        // Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(ControlTest::createAndShowGUI);
    }

}
