package view;

import modelPackage.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Author:  Martijn
 * Date:    5-1-2017
 */
public class TherapistAssistGUI extends Observable {

    private List<String> clientData;
    private List<String> groupData;
    private JPanel cards;
    private JPanel clientsPane;
    private JPanel groupsPane;
    private JPanel participantsPane;
    private JComboBox<Client> addableClients;
    private String userName;
    private List<Client> clients;
    private List<Group> groups;

    /** Static Card names */
    public static final String USER_PROFILE_CARD = "UserProfileCard";
    public static final String OPTIONS_CARD = "OptionsCard";
    public static final String ADD_CLIENT_CARD = "AddClientCard";
    public static final String ADD_GROUP_CARD = "AddGroupCard";
    /** 'Dynamic' Card names */
    public static final String CLIENT_PROFILE_CARD = "ClientProfileCard";
    public static final String GROUP_PROFILE_CARD = "GroupProfileCard";
    public static final String CLIENT_SESSIONS_CARD = "ClientSessionsCard";
    public static final String GROUP_SESSIONS_CARD = "GroupSessionsCard";

    public TherapistAssistGUI(String username) {
        this.userName = username;
        this.clients = new ArrayList<>();
        this.groups = new ArrayList<>();
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

        // Set clients and groups pane to be globally accessible
        this.clientsPane = clientsPane;
        this.groupsPane = groupsPane;

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
        logoutBtn.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?");
            if (response == JOptionPane.OK_OPTION) {
                logout();
            }
        });
        addClientBtn.addActionListener(e -> {
            // show 'add client'-card
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, ADD_CLIENT_CARD);
        });
        addGroupBtn.addActionListener(e -> {
            // show 'add group'-card
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, ADD_GROUP_CARD);
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

        //TODO: add data from clients to clients pane

        groupsMenuPane.add(groupsLbl);
        groupsMenuPane.add(addGroupBtn);

        //TODO: add data from groups to groups pane

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
     * Builds the sessions overview card for the GUI.
     * @param card The card on which the GUI is built.
     * @param sessionOwner The sessions belong to this group or client. Only a Client or Group
     *                     should be given.
     */
    public void buildSessionsOverviewCard(Container card, SessionOwner sessionOwner) {
        // Get the amount of rows needed to display sessions
        int x = sessionOwner.getSessions().size();

        // Get the amount of participants to display in the participants pane
        int x2 = 3;
        if (sessionOwner instanceof Client) {
            x2++;
        } else if (sessionOwner instanceof Group) {
            Group group = (Group) sessionOwner;
            x2 += group.getParticipants().size();
        } else {
            // The sessionOwner should be an instance of either Group or Client
            errorMessage("Session owner should be Group or Client object.");
        }

        card.setLayout(new FlowLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(1000, 800));

        // Initiate panes
        JPanel participantsPane = new JPanel(new GridLayout(x2, 1)); // x2 rows, 1 column
        JPanel dataPane = new JPanel(new FlowLayout());
        JPanel topPane = new JPanel(new FlowLayout());
        JPanel sessionsMenuPane = new JPanel(new FlowLayout());
        //TODO: change sessionsPane to JScrollPane to show all content
        JPanel sessionsPane = new JPanel(new GridLayout(x, 2)); // x rows, 2 columns

        // Set pane styles
        participantsPane.setPreferredSize(new Dimension(250, 800));
        participantsPane.setBackground(Color.LIGHT_GRAY);
        dataPane.setPreferredSize(new Dimension(720, 800));
        dataPane.setBackground(Color.LIGHT_GRAY);
        topPane.setPreferredSize(new Dimension(720, 50));
        topPane.setBackground(Color.LIGHT_GRAY);

        sessionsMenuPane.setPreferredSize(new Dimension(720, 50));
        sessionsMenuPane.setBackground(Color.LIGHT_GRAY);
        sessionsPane.setPreferredSize(new Dimension(720, 700));
        sessionsPane.setBackground(Color.LIGHT_GRAY);

        // Create labels
        JLabel userNameLbl = new JLabel(this.userName);
        JLabel sessionsLbl = new JLabel("Sessions");

        // Create buttons
        JButton optionsBtn = new JButton("Options");
        JButton logoutBtn = new JButton("Logout");
        JButton newSessionBtn = new JButton("New Session");
        JButton backBtn = new JButton("Back");

        // Button actions
        optionsBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Options dialog."));
        logoutBtn.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?");
            if (response == JOptionPane.OK_OPTION) {
                logout();
            }
        });
        newSessionBtn.addActionListener(e -> {
            //TODO: add "Add session" functionality
        });
        backBtn.addActionListener(e -> {
            // Show the user profile card
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, USER_PROFILE_CARD);
        });

        // Add components to their respective panes
        topPane.add(userNameLbl, BorderLayout.WEST);
        topPane.add(optionsBtn, BorderLayout.EAST);
        topPane.add(logoutBtn, BorderLayout.EAST);

        sessionsMenuPane.add(sessionsLbl);
        sessionsMenuPane.add(newSessionBtn);

        // Identify session owner as group or client
        if (sessionOwner instanceof Group) {
            // Cast session owner to group object
            Group group = (Group)sessionOwner;

            // Add name label to participants pane
            JLabel groupNameLbl = new JLabel(group.getGroupName());
            participantsPane.add(groupNameLbl);

            // Add participants' names to participants pane
            for (Client c : group.getParticipants()) {
                JLabel pLbl = new JLabel(c.getName());
                participantsPane.add(pLbl);
            }

            // Add clickable label to group profile
            JLabel profileLbl = new JLabel("Profile");
            profileLbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // To group profile
                    CardLayout cl = (CardLayout) (cards.getLayout());
                    cl.show(cards, GROUP_PROFILE_CARD + group.getGid());
                }
            });
            participantsPane.add(profileLbl);
        } else if (sessionOwner instanceof Client) {
            // Cast session owner to client object
            Client client = (Client)sessionOwner;

            // Add labels to participants pane
            JLabel clientNameLbl = new JLabel(client.getName());
            JLabel clientBirthDateLbl = new JLabel(client.getPI().getDateOfBirth());

            participantsPane.add(clientNameLbl);
            participantsPane.add(clientBirthDateLbl);

            // Add clickable label to client profile
            JLabel profileLbl = new JLabel("Profile");
            profileLbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // To client profile
                    CardLayout cl = (CardLayout) (cards.getLayout());
                    cl.show(cards, CLIENT_PROFILE_CARD + client.getId());
                }
            });
            participantsPane.add(profileLbl);
        } else {
            // The sessionOwner should be an instance of either Group or Client
            errorMessage("Session owner should be Group or Client object.");
        }

        // Add back button to the participants pane
        participantsPane.add(backBtn);

        // Add sessions with line graphs to sessions pane
        List<Session> sessions = sessionOwner.getSessions();
        for (int i = sessions.size() - 1; i >= 0; i--) {
            //TODO: make session labels clickable
            // Create label and graph pane
            JLabel sessionLbl = new JLabel((i + 1) + ". " + sessions.get(i).getDate());
            JPanel graphPane = new JPanel(); //TODO: insert actual graph
            graphPane.setBackground(Color.CYAN); // place holder for actual graph

            // Add label and pane to sessions pane
            sessionsPane.add(sessionLbl);
            sessionsPane.add(graphPane);
        }

        // Add panes to data pane
        dataPane.add(topPane);
        dataPane.add(sessionsMenuPane);
        dataPane.add(sessionsPane);

        // Add panes to card
        card.add(participantsPane);
        card.add(dataPane);

    }

    /**
     * Builds the session card for the GUI.
     * @param card The card on which the GUI is built.
     */
    public void buildSessionCard(Container card) {

    }

    /**
     * Builds a new GUI on a Card for a Group profile.
     * @param card The card on which the GUI is built.
     * @param group The Group this profile is meant for.
     */
    public void buildGroupProfileCard(Container card, Group group) {
        card.setLayout(new FlowLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(1000, 800));

        // Initiate panes
        JPanel topPane = new JPanel();
        JPanel dataPane = new JPanel();
        JPanel groupsPane = new JPanel();
        JPanel groupsMenuPane = new JPanel();
        JPanel participantsPane = new JPanel();
        JPanel sessionsPane = new JPanel();

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
        JLabel sessionsLbl = new JLabel("Sessions:");

        // Create buttons
        JButton optionsBtn = new JButton("Options");
        JButton logoutBtn = new JButton("Logout");
        JButton saveBtn = new JButton("Save");
        JButton archiveGroupBtn = new JButton("Archive Group");
        JButton backBtn = new JButton("Back");

        // Create text fields
        int txtFieldSize = 20;
        JTextField nameTF = new JTextField(group.getGroupName(), txtFieldSize);
        JTextField anamnesisTF = new JTextField(group.getAnamnesis(), txtFieldSize);
        JTextField helpQuestionTF = new JTextField(group.getHelpQuestion(), txtFieldSize);

        // Button actions
        optionsBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Options dialog."));
        logoutBtn.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?");
            if (response == JOptionPane.OK_OPTION) {
                logout();
            }
        });
        saveBtn.addActionListener(e -> {
            //TODO: add actual save functionality
            JOptionPane.showMessageDialog(null, "Save dialog.");
        });
        archiveGroupBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(null, "Archive Group dialog."));
        backBtn.addActionListener(e -> {
            // Show the user profile card
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, GROUP_SESSIONS_CARD + group.getGid());
        });

        // Set pane layouts
        topPane.setLayout(new FlowLayout());
        groupsMenuPane.setLayout(new FlowLayout());
        groupsPane.setLayout(new GridLayout(6, 2));    // 6 rows, 2 columns

        // Add components to their respective panes
        topPane.add(userNameLbl, BorderLayout.WEST);
        topPane.add(optionsBtn, BorderLayout.EAST);
        topPane.add(logoutBtn, BorderLayout.EAST);

        groupsMenuPane.add(groupProfileLbl);
        groupsMenuPane.add(saveBtn);
        groupsMenuPane.add(archiveGroupBtn);

        //TODO: add participants to participantsPane

        //TODO: add sessions to sessionsPane

        groupsPane.add(nameLbl);
        groupsPane.add(nameTF);
        groupsPane.add(participantsLbl);
        groupsPane.add(participantsPane);
        groupsPane.add(anamnesisLbl);
        groupsPane.add(anamnesisTF);
        groupsPane.add(helpQuestionLbl);
        groupsPane.add(helpQuestionTF);
        groupsPane.add(sessionsLbl);
        groupsPane.add(sessionsPane);
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
        logoutBtn.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?");
            if (response == JOptionPane.OK_OPTION) {
                logout();
            }
        });
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
            cl.show(cards, CLIENT_SESSIONS_CARD + client.getId());
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
     * Builds the options card for the GUI.
     * @param card The card on which the GUI is built.
     */
    public void buildOptionsCard(Container card) {

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
        logoutBtn.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?");
            if (response == JOptionPane.OK_OPTION) {
                logout();
            }
        });
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
            notifyObservers(GuiAction.addClient);

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
            CardLayout cl = (CardLayout) (cards.getLayout());
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
     * Builds the add group card for the GUI.
     * @param card The card on which this GUI is built.
     */
    public void buildAddGroupCard(Container card) {
        card.setLayout(new FlowLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(1000, 800));

        // Initiate panes
        JPanel topPane = new JPanel();
        JPanel dataPane = new JPanel();
        JPanel groupsPane = new JPanel();
        JPanel groupsMenuPane = new JPanel();
        participantsPane = new JPanel();

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

        JLabel nameLbl = new JLabel("Name:");
        JLabel participantsLbl = new JLabel("Participants:");
        JLabel anamnesisLbl = new JLabel("Anamnesis:");
        JLabel helpQuestionLbl = new JLabel("Help question:");

        // Create combo box
        this.addableClients = new JComboBox<>();
        List<Client> addedClients = new ArrayList<>();
        for (Client c : clients) {
            addableClients.addItem(c);
            addedClients.add(c);
        }

        // Create buttons
        JButton optionsBtn = new JButton("Options");
        JButton logoutBtn = new JButton("Logout");
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        JButton addBtn = new JButton("Add");
        JButton backBtn = new JButton("Back");

        // Create text fields
        int txtFieldSize = 20;
        JTextField nameTF = new JTextField(txtFieldSize);
        JTextField anamnesisTF = new JTextField(txtFieldSize);
        JTextField helpQuestionTF = new JTextField(txtFieldSize);

        // Button actions
        optionsBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Options dialog."));
        logoutBtn.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?");
            if (response == JOptionPane.OK_OPTION) {
                logout();
            }
        });
        saveBtn.addActionListener(e -> {
            // Handle empty text field(s)
            if (nameTF.getText() == null || nameTF.getText().length() < 1
                    || anamnesisTF.getText() == null || anamnesisTF.getText().length() < 1
                    || helpQuestionTF.getText() == null || helpQuestionTF.getText().length() < 1) {
                JOptionPane.showMessageDialog(null, "One or more fields are empty.");
                return;
            }

            // Put data in a list
            List<String> data = new ArrayList<>();
            data.add(nameTF.getText());
            data.add(anamnesisTF.getText());
            data.add(helpQuestionTF.getText());
            //TODO: add participants to data using their CIDs

            // Notify observers of new group
            groupData = data;
            setChanged();
            notifyObservers(GuiAction.addGroup);

            // Reset text fields
            nameTF.setText("");
            anamnesisTF.setText("");
            helpQuestionTF.setText("");

            // Empty out the participants pane
            for (Component c : participantsPane.getComponents()) {
                if (c instanceof JLabel) participantsPane.remove(c);
            }

            // Clear added clients list
            addedClients.clear();
        });
        cancelBtn.addActionListener(e -> {
            // Show the user profile card
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, USER_PROFILE_CARD);
        });
        addBtn.addActionListener(e -> {
            // Get the combo box' selected item
            Client c = (Client)addableClients.getSelectedItem();

            // Check for added clients
            for (Client cl : addedClients) {
                if (c.equals(cl)) {
                    JOptionPane.showMessageDialog(null, "That client has already been added.");
                    return;
                }
            }

            // Create label and add it to the participants pane
            JLabel lbl = new JLabel(c.getName());
            participantsPane.add(lbl);

            // Repaint participants pane
            participantsPane.revalidate();
            participantsPane.repaint();

            // Add client to added clients list.
            addedClients.add(c);
        });
        backBtn.addActionListener(e -> {
            // Show the user profile card
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, USER_PROFILE_CARD);
        });

        // Set pane layouts
        topPane.setLayout(new FlowLayout());
        groupsMenuPane.setLayout(new FlowLayout());
        groupsPane.setLayout(new GridLayout(6, 2));    // 6 rows, 2 columns

        // Add components to their respective panes
        topPane.add(userNameLbl, BorderLayout.WEST);
        topPane.add(optionsBtn, BorderLayout.EAST);
        topPane.add(logoutBtn, BorderLayout.EAST);

        groupsMenuPane.add(groupProfileLbl);
        groupsMenuPane.add(saveBtn);
        groupsMenuPane.add(cancelBtn);

        participantsPane.add(addableClients);
        participantsPane.add(addBtn);

        groupsPane.add(nameLbl);
        groupsPane.add(nameTF);
        groupsPane.add(participantsLbl);
        groupsPane.add(participantsPane);
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
        buildOptionsCard(optionsCard);
        buildAddGroupCard(addGroupCard);
        buildAddClientCard(addClientCard);

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
     */
    public void clientAdded(Client client) {
        int cid = client.getId();
        // Add client to clients list
        this.clients.add(client);

        // Create new card for new client profile view
        JPanel newClient = new JPanel();
        buildClientProfileCard(newClient, client);
        this.cards.add(newClient, CLIENT_PROFILE_CARD + cid);

        // Create new card for new sessions view
        JPanel newSessionsClient = new JPanel();
        buildSessionsOverviewCard(newSessionsClient, client);
        this.cards.add(newSessionsClient, CLIENT_SESSIONS_CARD + cid);

        // Create new label
        JLabel lbl = new JLabel(client.getPI().getInitials());
        this.clientsPane.add(lbl);

        // Add mouse listener to the label
        lbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // To client sessions
                CardLayout cl = (CardLayout) (cards.getLayout());
                cl.show(cards, CLIENT_SESSIONS_CARD + cid);
            }
        });

        // Add the client to the combo box in
        this.addableClients.addItem(client);

        // Repaint clientsPane and participantsPane
        this.clientsPane.revalidate();
        this.clientsPane.repaint();
        this.participantsPane.revalidate();
        this.participantsPane.repaint();

        // Show user profile card
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, USER_PROFILE_CARD);
    }

    /**
     * Called by the control class to let this view class rebuild the user profile card when a new
     * group has been added.
     * @param group The newly added Group.
     */
    public void groupAdded(Group group) {
        int gid = group.getGid();
        // Add group to group list
        groups.add(group);

        // Create new card for the new profile view
        JPanel newGroup = new JPanel();
        buildGroupProfileCard(newGroup, group);
        cards.add(newGroup, GROUP_PROFILE_CARD + gid);

        // Create new card for the new sessions view
        JPanel newSessionsGroup = new JPanel();
        buildSessionsOverviewCard(newSessionsGroup, group);
        cards.add(newSessionsGroup, GROUP_SESSIONS_CARD + gid);

        // Create new label
        JLabel lbl = new JLabel(group.getGroupName());
        this.groupsPane.add(lbl);

        // Add mouse listener to the label
        lbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // To group sessions
                CardLayout cl = (CardLayout) (cards.getLayout());
                cl.show(cards, GROUP_SESSIONS_CARD + gid);
            }
        });

        // Repaint groupsPane
        groupsPane.revalidate();
        groupsPane.repaint();

        // Show user profile card
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, USER_PROFILE_CARD);
    }

    /** Initiates the logout protocol. */
    private void logout() {
        //TODO: implement good logout protocol
        setChanged();
        notifyObservers(GuiAction.logout);
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

    public List<String> getGroupData() {
        return groupData;
    }

}
