package control;

import modelPackage.Client;
import modelPackage.PersonalInformation;
import modelPackage.exceptions.IdAlreadyExistsException;
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

    /** A counter that keeps track of the used Client IDs. */
    private int cidCounter;
    private List<Client> clients;
    private ViewTest gui;

    public ControlTest() {
        this.clients = new ArrayList<>();
        this.gui = new ViewTest();
        gui.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == gui && arg instanceof JLabel) {
            List<String> newClientData = gui.getClientData();
            String name = newClientData.get(0);
            String initials = newClientData.get(1);

            PersonalInformation pi = new PersonalInformation();
            pi.setInitials(initials);

            Client client = addClient(name, pi);
            gui.clientAdded(client, (JLabel)arg);
        } else {
            gui.errorMessage("Something went wrong with adding a client.");
        }
    }

    /**
     * Creates a new Client with an automatically generated ID, a given name, and a given pi. An
     * IdAlreadyExistsException() is thrown when the generated ID is already in use.
     * @param name The new Client's name.
     * @param pi The new Client's PersonalInformation.
     */
    public Client addClient(String name, PersonalInformation pi) {
        int cid = cidCounter++;
        for (Client cl : clients) {
            if (cl.getId() == cid) {
                throw new IdAlreadyExistsException();
            }
        }
        Client c = pi == null? new Client(cid, name) : new Client(cid, name, pi);
        clients.add(c);
        return c;
    }

    public ViewTest getGui() {
        return gui;
    }

    private static void createAndShowGUI() {
        // Create and set up window.
        JFrame frame = new JFrame("CardsLayout Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 400));

        // Create and set up content pane.
//        ViewTest test = new ViewTest();
        ControlTest test = new ControlTest();
        test.getGui().buildGUI(frame.getContentPane());

        // Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(ControlTest::createAndShowGUI);
    }

}
