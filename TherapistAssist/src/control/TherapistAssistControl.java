package control;

import modelPackage.Client;
import modelPackage.PersonalInformation;
import modelPackage.TherapistAssist;
import modelPackage.User;
import view.TherapistAssistGUI;

import javax.swing.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Author:  Martijn
 * Date:    5-1-2017
 */
public class TherapistAssistControl implements Observer {

    private TherapistAssistGUI view;
    private TherapistAssist model;

    // Temporary
    private User user;

    public TherapistAssistControl() {
        this.model = new TherapistAssist();
        // TODO: implement multiple users
        this.model.addUser("Agnes de Wit");
        this.user = model.getUsers().get(0);

        this.view = new TherapistAssistGUI(user.getName());
        view.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == view && arg instanceof JLabel) {
            // Extract client data from view
            List<String> newClientData = view.getClientData();
            String name = newClientData.get(0);
            String initials = newClientData.get(1);
            String dateOfBirth = newClientData.get(2);
            String gender = newClientData.get(3);
            String hin = newClientData.get(4);
            String anamnesis = newClientData.get(5);
            String helpQuestion = newClientData.get(6);

            // Put client data in a PI object
            PersonalInformation pi = new PersonalInformation();
            pi.setInitials(initials);
            pi.setDateOfBirth(dateOfBirth);
            pi.setGender(gender);
            pi.setHealthInsuranceNumber(hin);
            pi.setAnamnesis(anamnesis);
            pi.setHelpQuestion(helpQuestion);

            // Create client object and add it to database
            Client client = model.addClient(user, name, pi);
            if (client == null) System.out.println("Error: Client is null");
            view.clientAdded(client, (JLabel)arg);
        } else {
            // TODO: reverse label creation in view @buildAddClientCard()
            view.errorMessage("Something went wrong with adding a client.");
        }
    }

    public TherapistAssistGUI getView() {
        return view;
    }

    /**
     * The static method that creates the basis for the GUI. Should be in the controller and called
     * from the main method.
     */
    private static void setUpTherapistAssist() {
        // Create and set up window.
        JFrame frame = new JFrame("TestUserProfile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up content pane.
        TherapistAssistControl control = new TherapistAssistControl();
        control.getView().buildGUI(frame.getContentPane());

        // Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(TherapistAssistControl::setUpTherapistAssist);
    }

}
