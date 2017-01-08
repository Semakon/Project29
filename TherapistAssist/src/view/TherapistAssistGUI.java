package view;

import model.Client;
import model.PersonalInformation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Author:  Martijn
 * Date:    5-1-2017
 */
public class TherapistAssistGUI extends Observable {

    private List<String> clientData;
    private JPanel cards;
    private JPanel clientsPane;
    private String userName;
    private Map<Integer, List<String>> clients;
    private int clientCount;
    private int groupCount;

    /** Static Card names */
    public static final String USER_PROFILE_CARD = "UserProfileCard";
    public static final String OPTIONS_CARD = "OptionsCard";
    public static final String ADD_CLIENT_CARD = "AddClientCard";
    public static final String ADD_GROUP_CARD = "AddGroupCard";
    /** 'Dynamic' Card names */
    public static final String CLIENT_PROFILE_CARD = "ClientProfileCard";
    public static final String GROUP_PROFILE_CARD = "GroupProfileCard";

    public TherapistAssistGUI(String username) {
        this.userName = username;
        this.clients = new HashMap<>();
        this.clientCount = 0;
        this.groupCount = 0;
    }

    /**
     * Builds the user profile card for the GUI.
     * @param card The card on which the GUI is built.
     */
    public void buildUserProfile(Container card) {
        card.setLayout(new FlowLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(1000, 800));

        // Initiate panes
        JPanel topPane = new JPanel();
        JPanel searchPane = new JPanel();
        JPanel dataPane = new JPanel();
        JPanel clientsPane = new JPanel();
        JPanel clientsMenuPane = new JPanel();
        JPanel groupsPane = new JPanel();
        JPanel groupsMenuPane = new JPanel();

        // Set clients pane to be globally accessible
        this.clientsPane = clientsPane;

        // Set pane styles
        topPane.setPreferredSize(new Dimension(1000, 50));
        topPane.setBackground(Color.LIGHT_GRAY);
        searchPane.setPreferredSize(new Dimension(1000, 50));
        searchPane.setBackground(Color.LIGHT_GRAY);
        dataPane.setPreferredSize(new Dimension(1000, 700));
        dataPane.setBackground(Color.LIGHT_GRAY);

        clientsPane.setPreferredSize(new Dimension(450, 550));
        clientsPane.setBackground(Color.LIGHT_GRAY);
        clientsMenuPane.setPreferredSize(new Dimension(450, 50));
        clientsMenuPane.setBackground(Color.LIGHT_GRAY);
        groupsPane.setPreferredSize(new Dimension(450, 55));
        groupsPane.setBackground(Color.LIGHT_GRAY);
        groupsMenuPane.setPreferredSize(new Dimension(450, 50));
        groupsMenuPane.setBackground(Color.LIGHT_GRAY);

        // Create labels
        JLabel userNameLbl = new JLabel(this.userName);
        JLabel searchLbl = new JLabel("Search");
        JLabel clientsLbl = new JLabel("Clients");
        JLabel groupsLbl = new JLabel("Groups");

        // Create buttons
        JButton optionsBtn = new JButton("Options");
        JButton logoutBtn = new JButton("Logout");
        JButton addClientBtn = new JButton("Add Client");
        JButton addGroupBtn = new JButton("Add Group");

        // Create text field
        JTextField searchTF = new JTextField(20);

        // Button actions
        optionsBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Options dialog."));
        logoutBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Logout dialog."));
        addClientBtn.addActionListener(e -> {
            // show 'add client'-card
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, ADD_CLIENT_CARD);
        });
        addGroupBtn.addActionListener(e -> {
            // Create new label, set its name, and add it to the pane
            JLabel lbl = new JLabel("Group-" + ++groupCount);
            lbl.setName(GROUP_PROFILE_CARD + groupCount);
            groupsPane.add(lbl);

            // Create and build a new card, then add it to the card layout
            JPanel newCard = new JPanel();
            buildGroupProfileCard(newCard, lbl.getText());
            cards.add(newCard, GROUP_PROFILE_CARD + groupCount);

            // Add a mouse listener to the label
            lbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Use the label's name to identify the card that is shown
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards, e.getComponent().getName());
                }
            });

            // Revalidate and repaint the pane
            groupsPane.revalidate();
            groupsPane.repaint();
        });

        // Set pane layouts
        topPane.setLayout(new FlowLayout());
        searchPane.setLayout(new FlowLayout());
        dataPane.setLayout(new GridLayout(2, 2));       // 2 rows, 2 columns
        clientsMenuPane.setLayout(new FlowLayout());
        groupsMenuPane.setLayout(new FlowLayout());
        clientsPane.setLayout(new GridLayout(8, 2));    // 8 rows, 2 columns
        groupsPane.setLayout(new GridLayout(8, 2));     // 8 rows, 2 columns

        // Add components to their respective panes
        topPane.add(userNameLbl, BorderLayout.WEST);
        topPane.add(optionsBtn, BorderLayout.EAST);
        topPane.add(logoutBtn, BorderLayout.EAST);

        searchPane.add(searchLbl);
        searchPane.add(searchTF);

        clientsMenuPane.add(clientsLbl);
        clientsMenuPane.add(addClientBtn);

        groupsMenuPane.add(groupsLbl);
        groupsMenuPane.add(addGroupBtn);

        // Add panes to data pane
        dataPane.add(clientsMenuPane);
        dataPane.add(groupsMenuPane);
        dataPane.add(clientsPane);
        dataPane.add(groupsPane);

        // Add panes to card
        card.add(topPane);
        card.add(searchPane);
        card.add(dataPane);
    }

    /**
     * Builds a new GUI on a Card for a Group profile.
     * @param card The card on which the GUI is built.
     * @param group The Group this profile is meant for.
     */
    public void buildGroupProfileCard(Container card, String group) {
        card.setLayout(new FlowLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(1000, 800));

        // Initiate panes
        JPanel topPane = new JPanel();
        JPanel dataPane = new JPanel();
        JPanel groupsPane = new JPanel();
        JPanel groupsMenuPane = new JPanel();

        // Set pane styles
        topPane.setPreferredSize(new Dimension(1000, 50));
        topPane.setBackground(Color.LIGHT_GRAY);
        dataPane.setPreferredSize(new Dimension(1000, 750));
        dataPane.setBackground(Color.LIGHT_GRAY);

        groupsPane.setPreferredSize(new Dimension(1000, 700));
        groupsPane.setBackground(Color.LIGHT_GRAY);
        groupsMenuPane.setPreferredSize(new Dimension(1000, 50));
        groupsMenuPane.setBackground(Color.LIGHT_GRAY);

        // Create labels
        JLabel userNameLbl = new JLabel(this.userName);
        JLabel groupProfileLbl = new JLabel("Group profile");

        JLabel nameLbl = new JLabel("Group name:");
        JLabel participantsLbl = new JLabel("Participants:");
        JLabel anamnesisLbl = new JLabel("Anamnesis:");
        JLabel helpQuestionLbl = new JLabel("Help question:");

        // Create buttons
        JButton optionsBtn = new JButton("Options");
        JButton logoutBtn = new JButton("Logout");
        JButton saveBtn = new JButton("Save");
        JButton archiveGroupBtn = new JButton("Archive Group");
        JButton backBtn = new JButton("Back");

        // Create text fields
        int txtFieldSize = 20;
        JTextField nameTF = new JTextField(group, txtFieldSize);
        JTextField participantsTF = new JTextField(txtFieldSize);
        JTextField anamnesisTF = new JTextField(txtFieldSize);
        JTextField helpQuestionTF = new JTextField(txtFieldSize);

        // Button actions
        optionsBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Options dialog."));
        logoutBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Logout dialog."));
        saveBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Save dialog."));
        archiveGroupBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(null, "Archive Group dialog."));
        backBtn.addActionListener(e -> {
            // Show the user profile card
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, USER_PROFILE_CARD);
        });

        // Set pane layouts
        topPane.setLayout(new FlowLayout());
//        dataPane.setLayout(new FlowLayout());
        groupsMenuPane.setLayout(new FlowLayout());
        groupsPane.setLayout(new GridLayout(5, 2));    // 5 rows, 2 columns

        // Add components to their respective panes
        topPane.add(userNameLbl, BorderLayout.WEST);
        topPane.add(optionsBtn, BorderLayout.EAST);
        topPane.add(logoutBtn, BorderLayout.EAST);

        groupsMenuPane.add(groupProfileLbl);
        groupsMenuPane.add(saveBtn);
        groupsMenuPane.add(archiveGroupBtn);

        groupsPane.add(nameLbl);
        groupsPane.add(nameTF);
        groupsPane.add(participantsLbl);
        groupsPane.add(participantsTF);
        groupsPane.add(anamnesisLbl);
        groupsPane.add(anamnesisTF);
        groupsPane.add(helpQuestionLbl);
        groupsPane.add(helpQuestionTF);
        groupsPane.add(backBtn);

        // Add panes to data pane
        dataPane.add(groupsMenuPane);
        dataPane.add(groupsPane);

        // Add panes to card
        card.add(topPane);
        card.add(dataPane);
    }

    /**
     * Builds a new GUI on a Card for a Client profile.
     * @param card The card on which the GUI is built.
     * @param client The Client this profile is meant for.
     */
    public void buildClientProfileCard(Container card, Client client) {
        card.setLayout(new FlowLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(1000, 800));

        // Initiate panes
        JPanel topPane = new JPanel();
        JPanel dataPane = new JPanel();
        JPanel clientsPane = new JPanel();
        JPanel clientsMenuPane = new JPanel();

        // Set pane styles
        topPane.setPreferredSize(new Dimension(1000, 50));
        topPane.setBackground(Color.LIGHT_GRAY);
        dataPane.setPreferredSize(new Dimension(1000, 750));
        dataPane.setBackground(Color.LIGHT_GRAY);

        clientsPane.setPreferredSize(new Dimension(1000, 700));
        clientsPane.setBackground(Color.LIGHT_GRAY);
        clientsMenuPane.setPreferredSize(new Dimension(1000, 50));
        clientsMenuPane.setBackground(Color.LIGHT_GRAY);

        // Create labels
        JLabel userNameLbl = new JLabel(this.userName);
        JLabel clientProfileLbl = new JLabel("Client profile");

        JLabel nameLbl = new JLabel("Name:");
        JLabel initialsLbl = new JLabel("Initials:");
        JLabel dateOfBirthLbl = new JLabel("Date of Birth:");
        JLabel genderLbl = new JLabel("Gender:");
        JLabel hinLbl = new JLabel("Health insurance number:");
        JLabel anamnesisLbl = new JLabel("Anamnesis:");
        JLabel helpQuestionLbl = new JLabel("Help question:");

        // Create buttons
        JButton optionsBtn = new JButton("Options");
        JButton logoutBtn = new JButton("Logout");
        JButton saveBtn = new JButton("Save");
        JButton archiveClientBtn = new JButton("Archive Client");
        JButton backBtn = new JButton("Back");

        // Create text fields
        int txtFieldSize = 20;
        PersonalInformation pi = client.getPI();
        JTextField nameTF = new JTextField(client.getName(), txtFieldSize);
        JTextField initialsTF = new JTextField(pi.getInitials(), txtFieldSize);
        JTextField dateOfBirthTF = new JTextField(pi.getDateOfBirth(), txtFieldSize);
        JTextField genderTF = new JTextField(pi.getGender(), txtFieldSize);
        JTextField hinTF = new JTextField(pi.getHealthInsuranceNumber(), txtFieldSize);
        JTextField anamnesisTF = new JTextField(pi.getAnamnesis(), txtFieldSize);
        JTextField helpQuestionTF = new JTextField(pi.getHelpQuestion(), txtFieldSize);

        // Button actions
        optionsBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Options dialog."));
        logoutBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Logout dialog."));

        saveBtn.addActionListener(e -> {
            //TODO: implement save functionality
            JOptionPane.showMessageDialog(null, "Save dialog.");
        });
        archiveClientBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(null, "Archive Client dialog."));
        backBtn.addActionListener(e -> {
            // Reset text field values
            nameTF.setText(client.getName());
            initialsTF.setText(pi.getInitials());
            dateOfBirthTF.setText(pi.getDateOfBirth());
            genderTF.setText(pi.getGender());
            hinTF.setText(pi.getHealthInsuranceNumber());
            anamnesisTF.setText(pi.getAnamnesis());
            helpQuestionTF.setText(pi.getHelpQuestion());

            // Show the user profile card
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, USER_PROFILE_CARD);
        });

        // Set pane layouts
        topPane.setLayout(new FlowLayout());
        clientsMenuPane.setLayout(new FlowLayout());
        clientsPane.setLayout(new GridLayout(8, 2));    // 8 rows, 2 columns

        // Add components to their respective panes
        topPane.add(userNameLbl, BorderLayout.WEST);
        topPane.add(optionsBtn, BorderLayout.EAST);
        topPane.add(logoutBtn, BorderLayout.EAST);

        clientsMenuPane.add(clientProfileLbl);
        clientsMenuPane.add(saveBtn);
        clientsMenuPane.add(archiveClientBtn);

        clientsPane.add(nameLbl);
        clientsPane.add(nameTF);
        clientsPane.add(initialsLbl);
        clientsPane.add(initialsTF);
        clientsPane.add(dateOfBirthLbl);
        clientsPane.add(dateOfBirthTF);
        clientsPane.add(genderLbl);
        clientsPane.add(genderTF);
        clientsPane.add(hinLbl);
        clientsPane.add(hinTF);
        clientsPane.add(anamnesisLbl);
        clientsPane.add(anamnesisTF);
        clientsPane.add(helpQuestionLbl);
        clientsPane.add(helpQuestionTF);
        clientsPane.add(backBtn);

        // Add panes to data pane
        dataPane.add(clientsMenuPane);
        dataPane.add(clientsPane);

        // Add panes to card
        card.add(topPane);
        card.add(dataPane);
    }

    /**
     * Builds the add client card for the GUI.
     * @param card The card on which this GUI is built.
     */
    public void buildAddClientCard(Container card) {
        card.setLayout(new FlowLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(1000, 800));

        // Initiate panes
        JPanel topPane = new JPanel();
        JPanel dataPane = new JPanel();
        JPanel clientsPane = new JPanel();
        JPanel clientsMenuPane = new JPanel();

        // Set pane styles
        topPane.setPreferredSize(new Dimension(1000, 50));
        topPane.setBackground(Color.LIGHT_GRAY);
        dataPane.setPreferredSize(new Dimension(1000, 750));
        dataPane.setBackground(Color.LIGHT_GRAY);

        clientsPane.setPreferredSize(new Dimension(1000, 700));
        clientsPane.setBackground(Color.LIGHT_GRAY);
        clientsMenuPane.setPreferredSize(new Dimension(1000, 50));
        clientsMenuPane.setBackground(Color.LIGHT_GRAY);

        // Create labels
        JLabel userNameLbl = new JLabel(this.userName);
        JLabel clientProfileLbl = new JLabel("Client profile");

        JLabel nameLbl = new JLabel("Name:");
        JLabel initialsLbl = new JLabel("Initials:");
        JLabel dateOfBirthLbl = new JLabel("Date of Birth:");
        JLabel genderLbl = new JLabel("Gender:");
        JLabel hinLbl = new JLabel("Health insurance number:");
        JLabel anamnesisLbl = new JLabel("Anamnesis:");
        JLabel helpQuestionLbl = new JLabel("Help question:");

        // Create buttons
        JButton optionsBtn = new JButton("Options");
        JButton logoutBtn = new JButton("Logout");
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        JButton backBtn = new JButton("Back");

        // Create text fields
        int txtFieldSize = 20;
        JTextField nameTF = new JTextField(txtFieldSize);
        JTextField initialsTF = new JTextField(txtFieldSize);
        JTextField dateOfBirthTF = new JTextField(txtFieldSize);
        JTextField genderTF = new JTextField(txtFieldSize);
        JTextField hinTF = new JTextField(txtFieldSize);
        JTextField anamnesisTF = new JTextField(txtFieldSize);
        JTextField helpQuestionTF = new JTextField(txtFieldSize);

        // Button actions
        optionsBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Options dialog."));
        logoutBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Logout dialog."));
        saveBtn.addActionListener(e -> {
            // Handle empty text field(s)
            if (nameTF.getText() == null || nameTF.getText().length() < 1
                    || initialsTF.getText() == null || initialsTF.getText().length() < 1
                    || dateOfBirthTF.getText() == null || dateOfBirthTF.getText().length() < 1
                    || genderTF.getText() == null || genderTF.getText().length() < 1
                    || hinTF.getText() == null || hinTF.getText().length() < 1
                    || anamnesisTF.getText() == null || anamnesisTF.getText().length() < 1
                    || helpQuestionTF.getText() == null || helpQuestionTF.getText().length() < 1) {
                JOptionPane.showMessageDialog(null, "One or more fields are empty.");
                return;
            }

            // Create new label
            JLabel lbl = new JLabel(initialsTF.getText());
            this.clientsPane.add(lbl);
            lbl.setName("" + ++clientCount);

            // Put data in a list
            List<String> data = new ArrayList<>();
            data.add(nameTF.getText());
            data.add(initialsTF.getText());
            data.add(dateOfBirthTF.getText());
            data.add(genderTF.getText());
            data.add(hinTF.getText());
            data.add(anamnesisTF.getText());
            data.add(helpQuestionTF.getText());

            // Notify observers of new client
            clientData = data;
            setChanged();
            notifyObservers(lbl);

            // Reset text fields
            nameTF.setText("");
            initialsTF.setText("");
            dateOfBirthTF.setText("");
            genderTF.setText("");
            hinTF.setText("");
            anamnesisTF.setText("");
            helpQuestionTF.setText("");
        });
        cancelBtn.addActionListener(e -> {
            // Show the user profile card
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, USER_PROFILE_CARD);
        });backBtn.addActionListener(e -> {
            // Show the user profile card
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, USER_PROFILE_CARD);
        });

        // Set pane layouts
        topPane.setLayout(new FlowLayout());
//        dataPane.setLayout(new FlowLayout());
        clientsMenuPane.setLayout(new FlowLayout());
        clientsPane.setLayout(new GridLayout(8, 2));    // 8 rows, 2 columns

        // Add components to their respective panes
        topPane.add(userNameLbl, BorderLayout.WEST);
        topPane.add(optionsBtn, BorderLayout.EAST);
        topPane.add(logoutBtn, BorderLayout.EAST);

        clientsMenuPane.add(clientProfileLbl);
        clientsMenuPane.add(saveBtn);
        clientsMenuPane.add(cancelBtn);

        clientsPane.add(nameLbl);
        clientsPane.add(nameTF);
        clientsPane.add(initialsLbl);
        clientsPane.add(initialsTF);
        clientsPane.add(dateOfBirthLbl);
        clientsPane.add(dateOfBirthTF);
        clientsPane.add(genderLbl);
        clientsPane.add(genderTF);
        clientsPane.add(hinLbl);
        clientsPane.add(hinTF);
        clientsPane.add(anamnesisLbl);
        clientsPane.add(anamnesisTF);
        clientsPane.add(helpQuestionLbl);
        clientsPane.add(helpQuestionTF);
        clientsPane.add(backBtn);

        // Add panes to data pane
        dataPane.add(clientsMenuPane);
        dataPane.add(clientsPane);

        // Add panes to card
        card.add(topPane);
        card.add(dataPane);
    }

    /**
     * The basis of the cards of the card layout. Should be called from the createAndShowGUI() method
     * in the controller.
     * @param pane The content pane of the main JFrame of the GUI.
     */
    public void buildGUI(Container pane) {
        // Initialise static cards
        JPanel userProfileCard = new JPanel();
        JPanel optionsCard = new JPanel();
        JPanel addClientCard = new JPanel();
        JPanel addGroupCard = new JPanel();

        // Build the cards
        buildUserProfile(userProfileCard);
//        buildOptionsCard(optionsCard);
        buildAddClientCard(addClientCard);
//        buildAddGroupCard(addGroupCard);

        // Add the cards to the card layout
        this.cards = new JPanel(new CardLayout());
        this.cards.add(userProfileCard, USER_PROFILE_CARD);
        this.cards.add(optionsCard, OPTIONS_CARD);
        this.cards.add(addClientCard, ADD_CLIENT_CARD);
        this.cards.add(addGroupCard, ADD_GROUP_CARD);

        pane.add(cards);
    }

    /**
     * Called by the control class to let this view class rebuild the user profile card when a new
     * client has been added.
     * @param client The newly added client.
     * @param lbl The label pointing to the new client's profile card.
     */
    public void clientAdded(Client client, JLabel lbl) {
        // Add client to clients map
        int id = client.getId();
        List<String> pi = new ArrayList<>();
        pi.add(client.getName());
        pi.add(client.getPI().getInitials());
        pi.add(client.getPI().getDateOfBirth());
        pi.add(client.getPI().getGender());
        pi.add(client.getPI().getHealthInsuranceNumber());
        pi.add(client.getPI().getAnamnesis());
        pi.add(client.getPI().getHelpQuestion());
        this.clients.put(id, pi);

        // Create new card for new input
        JPanel newClient = new JPanel();
        buildClientProfileCard(newClient, client);
        cards.add(newClient, CLIENT_PROFILE_CARD + clientCount);

        // Add mouse listener to the label
        lbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, CLIENT_PROFILE_CARD + e.getComponent().getName());
            }
        });

        // Repaint clientsPane
        clientsPane.revalidate();
        clientsPane.repaint();

        // Show user profile card
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, USER_PROFILE_CARD);
    }

    /**
     * Displays an error message dialog.
     * @param msg The error message.
     */
    public void errorMessage(String msg) {
        JOptionPane.showMessageDialog(null, "ERROR: " + msg);
    }

    public List<String> getClientData() {
        return clientData;
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
        javax.swing.SwingUtilities.invokeLater(TherapistAssistGUI::createAndShowGUI);
    }

}
