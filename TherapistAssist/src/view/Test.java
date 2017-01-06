package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Author:  Martijn
 * Date:    5-1-2017
 */
public class Test {

    private JPanel cards;
    private String userName;
    private int clientCount;
    private int groupCount;

    /** Static Card names */
    public static final String USER_PROFILE_CARD = "UserProfileCard";
    public static final String OPTIONS_CARD = "OptionsCard";
    public static final String ADD_CLIENT_CARD = "AddClientCard";
    public static final String ADD_GROUP_CARD = "AddGroupCard";
    /** 'Dynamic' Card names */
    public static final String CLIENT_PROFILE = "ClientProfileCard";
    public static final String GROUP_PROFILE = "GroupProfileCard";

    public Test() {
        userName = "Agnes de Wit";
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
            // Create new label, set its name, and add it to the pane
            JLabel lbl = new JLabel("Client-" + ++clientCount);
            lbl.setName(CLIENT_PROFILE + clientCount);
            clientsPane.add(lbl);

            // Create and build a new card, then add it to the card layout
            JPanel newCard = new JPanel();
            buildClientProfileCard(newCard, lbl.getText());
            cards.add(newCard, CLIENT_PROFILE + clientCount);

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
            clientsPane.revalidate();
            clientsPane.repaint();
        });
        addGroupBtn.addActionListener(e -> {
            // Create new label, set its name, and add it to the pane
            JLabel lbl = new JLabel("Group-" + ++groupCount);
            lbl.setName(GROUP_PROFILE + groupCount);
            groupsPane.add(lbl);

            // Create and build a new card, then add it to the card layout
            JPanel newCard = new JPanel();
            buildGroupProfileCard(newCard, lbl.getText());
            cards.add(newCard, GROUP_PROFILE + groupCount);

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
     * Builds a new GUI on a Card for a Client profile.
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
    public void buildClientProfileCard(Container card, String client) {
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
        JTextField nameTF = new JTextField(client, txtFieldSize);
        JTextField initialsTF = new JTextField(txtFieldSize);
        JTextField dateOfBirthTF = new JTextField(txtFieldSize);
        JTextField genderTF = new JTextField(txtFieldSize);
        JTextField hinTF = new JTextField(txtFieldSize);
        JTextField anamnesisTF = new JTextField(txtFieldSize);
        JTextField helpQuestionTF = new JTextField(txtFieldSize);

        // Button actions
        optionsBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Options dialog."));
        logoutBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Logout dialog."));
        saveBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Save dialog."));
        archiveClientBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(null, "Archive Client dialog."));
        backBtn.addActionListener(e -> {
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
//        buildAddClientCard(addClientCard);
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
     * The static method that creates the basis for the GUI. Should be in the controller and called
     * from the main method.
     */
    private static void createAndShowGUI() {
        // Create and set up window.
        JFrame frame = new JFrame("TestUserProfile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up content pane.
        Test test = new Test();
        test.buildGUI(frame.getContentPane());

        // Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Test::createAndShowGUI);
    }

}
