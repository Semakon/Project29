package control;

import modelPackage.*;
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

    /**
     * Update method using enumeration types to decide what action to take.
     * @param o The Observable object that called this method.
     * @param arg The argument that was passed to the notifyAllObservers() method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof GuiAction) {
            switch((GuiAction)arg) {
                case addClient:
                    List<String> newClientData = view.getClientData();
                    String clientName = newClientData.get(0);
                    String initials = newClientData.get(1);
                    String dateOfBirth = newClientData.get(2);
                    String gender = newClientData.get(3);
                    String hin = newClientData.get(4);
                    String clientAnamnesis = newClientData.get(5);
                    String clientHelpQuestion = newClientData.get(6);

                    // Put client data in a PI object
                    PersonalInformation pi = new PersonalInformation();
                    pi.setInitials(initials);
                    pi.setDateOfBirth(dateOfBirth);
                    pi.setGender(gender);
                    pi.setHealthInsuranceNumber(hin);
                    pi.setAnamnesis(clientAnamnesis);
                    pi.setHelpQuestion(clientHelpQuestion);

                    // Create client object and add it to database
                    Client client = model.addClient(user, clientName, pi);
                    if (client == null) {
                        System.out.println("Error: Client is null");
                        return;
                    }

                    // Send newly created client to the view
                    view.clientAdded(client);
                    return;
                case addGroup:
                    // Create Group and add it to the database
                    Group group = model.addGroup(user);
                    if (group == null) {
                        System.out.println("Error: Group is null");
                        return;
                    }

                    // Retrieve data from GUI and add it to the newly created group
                    List<String> newGroupData = view.getGroupData();
                    String groupName = newGroupData.get(0);
                    String groupAnamnesis = newGroupData.get(1);
                    String groupHelpQuestion = newGroupData.get(2);

                    group.setGroupName(groupName);
                    group.setAnamnesis(groupAnamnesis);
                    group.setHelpQuestion(groupHelpQuestion);

                    for (int i = 3; i < newGroupData.size(); i++) {
                        int cid = Integer.parseInt(newGroupData.get(i));
                        group.addClient(model.getClientByCid(user, cid));
                    }

                    // Send newly created group to the view
                    view.groupAdded(group);
                    return;
                case logout:
                    // TODO: implement good logout protocol
                    System.exit(0);
            }
        } else {
            // TODO: reverse label creation in view
            view.errorMessage("Something went wrong.");
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
        JFrame frame = new JFrame("TherapistAssist");
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
