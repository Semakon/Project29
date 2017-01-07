package view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Author:  Martijn
 * Date:    6-1-2017
 */
public class AddClientGUI {

    private JPanel cards;
    private String userName;
    private Map<Integer, List<String>> clients;

    public AddClientGUI(JPanel cards, String userName) {
        this.cards = cards;
        this.userName = userName;
        this.clients = new HashMap<>();
    }

    /**
     * Builds a new GUI on a Card for a Client profile.
     * @param card The card on which the GUI is built.
     */
    public void buildAddClientCard(Container card, Container rootCard) {
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
        JLabel addClientLbl = new JLabel("Add Client");

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
        saveBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Save dialog."));
        cancelBtn.addActionListener(e -> {
            // Show the user profile card
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, TherapistAssistGUI.USER_PROFILE_CARD);
        });
        backBtn.addActionListener(e -> {
            // Show the user profile card
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, TherapistAssistGUI.USER_PROFILE_CARD);
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

        clientsMenuPane.add(addClientLbl);
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

}
