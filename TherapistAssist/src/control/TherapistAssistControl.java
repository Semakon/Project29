package control;

import modelPackage.*;
import view.TherapistAssistGUI;

import javax.swing.*;
import java.util.*;

/**
 * Author:  Martijn
 * Date:    5-1-2017
 */
public class TherapistAssistControl implements Observer {

    private TherapistAssistGUI view;
    private TherapistAssist model;

    private Thread t1;
    private boolean threadActive;

    // Temporary
    private User user;

    public TherapistAssistControl() {
        this.model = new TherapistAssist();
        // TODO: implement multiple users
        this.model.addUser("Agnes de Wit");
        this.user = model.getUsers().get(0);

        // For test purposes
        addTestClients();

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
                    view.addClientToView(client);
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
                    view.addGroupToView(group);
                    return;
                case addSession:
                    // Create SessionOwner and start a new session
                    SessionOwner sessionOwner = view.getCurrentOwner();
                    Session session = model.startSession(user, sessionOwner);

                    // Send newly created session to the view
                    view.addSessionToView(session);
                    return;
                case startSession:
                    // Don't start a new thread when one is already active
                    if (threadActive) return;

                    // Activate thread
                    threadActive = true;

                    // Add graph data to newly created session in real time (threads)
                    t1 = new LoadData(view.getCurrentSession(), view.getActivePane());
                    t1.start();
                    return;
                case stopSession:
                    // Can't stop a thread that doesn't exist
                    if (!threadActive) return;

                    // Stop updating graph by joining thread
                    try {
                        ((LoadData)t1).setUpdateGraph(false);
                        t1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Deactivate thread
                    threadActive = false;
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

    /** TEMPORARY */
    public User getUser() {
        return user;
    }

    public TherapistAssistGUI getView() {
        return view;
    }

    /**
     * Adds a few clients and groups for testing purposes.
     */
    private void addTestClients() {
        // Create client 'Bert'
        PersonalInformation bertPi = new PersonalInformation();
        bertPi.setInitials("B.Z.");
        bertPi.setDateOfBirth("28-06-1970");
        bertPi.setGender("Male");
        bertPi.setHealthInsuranceNumber("381403850493");
        bertPi.setAnamnesis("-");
        bertPi.setHelpQuestion("Anger management");
        Client bert = this.model.addClient(user, "Bert Zonneklaar", bertPi);
        bert.setPicturePath(LoadData.FILE_PATH + "Bert.jpg");

        // Create client 'Paul'
        PersonalInformation paulPi = new PersonalInformation();
        paulPi.setInitials("P.J.");
        paulPi.setDateOfBirth("14-04-1981");
        paulPi.setGender("Male");
        paulPi.setHealthInsuranceNumber("591578910295");
        paulPi.setAnamnesis("-");
        paulPi.setHelpQuestion("Anger management");
        Client paul = this.model.addClient(user, "Paul de Jong", paulPi);
        paul.setPicturePath(LoadData.FILE_PATH + "Paul.jpg");

        // Create client 'Henk'
        PersonalInformation henkPi = new PersonalInformation();
        henkPi.setInitials("H.P.");
        henkPi.setDateOfBirth("14-04-1967");
        henkPi.setGender("Male");
        henkPi.setHealthInsuranceNumber("842684597521");
        henkPi.setAnamnesis("-");
        henkPi.setHelpQuestion("Anger management");
        Client henk = this.model.addClient(user, "Henk van Pamelen", henkPi);
        henk.setPicturePath(LoadData.FILE_PATH + "Henk.jpg");

        // Create group 'Group 1'
        Group group1 = this.model.addGroup(user);
        group1.setGroupName("Group 1");
        group1.setAnamnesis("-");
        group1.setHelpQuestion("Anger management");
        group1.addClient(bert);
        group1.addClient(paul);

        // Create group 'Group 2'
        Group group2 = this.model.addGroup(user);
        group2.setGroupName("Group 2");
        group2.setAnamnesis("-");
        group2.setHelpQuestion("Anger management");
        group2.addClient(bert);
        group2.addClient(henk);

        // Create session for 'Bert'
        Session bertSession = model.startSession(user, bert);
        bertSession.getGraphData().setData(bert, generateRandomData(60));
        bert.addSession(bertSession);

        // Create sessions for 'Paul'
        Session paulSession = model.startSession(user, paul);
        paulSession.getGraphData().setData(paul, generateRandomData(60));
        paul.addSession(paulSession);

        Session paulSession2 = model.startSession(user, paul);
        paulSession2.getGraphData().setData(paul, generateRandomData(60));
        paul.addSession(paulSession2);

        // Create session for 'Henk'
        Session henkSession = model.startSession(user, henk);
        henkSession.getGraphData().setData(henk, generateRandomData(60));
        henk.addSession(henkSession);

        // Create session for 'Group 1'
        Session group1Session = model.startSession(user, group1);
        group1Session.getGraphData().setData(bert, generateRandomData(60));
        group1Session.getGraphData().setData(paul, generateRandomData(60));
        group1.addSession(group1Session);

        // Create session for 'Group 2'
        Session group2Session = model.startSession(user, group2);
        group2Session.getGraphData().setData(bert, generateRandomData(60));
        group2Session.getGraphData().setData(henk, generateRandomData(60));
        group2.addSession(group2Session);

    }

    private List<Integer[]> generateRandomData(int values) {
        List<Integer[]> randomData = new ArrayList<>();

        for (int i = 0; i <= values; i++) {
            Random rand = new Random();
            int value = rand.nextInt((90 - 60) + 1) + 60;
            Integer[] data = {value, i};
            randomData.add(data);
        }

        return randomData;
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
        User user = control.getUser();
        control.getView().buildGUI(frame.getContentPane(), user.getClients(), user.getGroups());

        // Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(TherapistAssistControl::setUpTherapistAssist);
    }

}
