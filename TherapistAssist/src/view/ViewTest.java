package view;

import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Author:  Martijn
 * Date:    6-1-2017
 */
public class ViewTest extends Observable {

    private List<String> clientData;
    private JPanel cards;
    private JPanel rootCard;
    private Map<Integer, List<String>> clients;
    private int count;
    private Dimension dimension = new Dimension(400, 400);

    public static final String CARD = "Card";
    public static final String ROOT_CARD = "RootCard";
    public static final String ADD_LABEL_CARD = "AddLabelCard";

    public ViewTest() {
        this.clients = new HashMap<>();
        this.count = 1;
    }

    public void buildAddClientCard(Container card) {
        card.setLayout(new FlowLayout());
        card.setPreferredSize(dimension);

        // Initialise components
        JLabel nameLbl = new JLabel("Name: ");
        JLabel initLbl = new JLabel("Initials: ");
        JTextField nameTF = new JTextField(10);
        JTextField initTF = new JTextField(10);
        JButton addBtn = new JButton("Add");
        JButton cancelBtn = new JButton("Cancel");

        // Actions of buttons
        addBtn.addActionListener(e -> {
            // Handle empty text field
            if (nameTF.getText() == null || nameTF.getText().length() < 1
                    || initTF.getText() == null || initTF.getText().length() < 1) {
                JOptionPane.showMessageDialog(null, "One or more fields are empty.");
                return;
            }

            // Create new label
            JLabel lbl = new JLabel(initTF.getText());
            rootCard.add(lbl);
            lbl.setName("" + ++count);

            // Put data in a list
            List<String> data = new ArrayList<>();
            data.add(nameTF.getText());
            data.add(initTF.getText());

            // Notify observers of new client
            clientData = data;
            setChanged();
            notifyObservers(lbl);

            nameTF.setText("");
            initTF.setText("");
        });
        cancelBtn.addActionListener(e -> {
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, ROOT_CARD);
            nameTF.setText("");
            initTF.setText("");
        });

        card.add(nameLbl);
        card.add(nameTF);
        card.add(initLbl);
        card.add(initTF);
        card.add(addBtn);
        card.add(cancelBtn);
    }

    public void buildNewCard(Container card, int n) {
        card.setLayout(new FlowLayout());
        card.setPreferredSize(dimension);

        String labelName = "Error";
        String labelInit = "Error";
        for (int i : clients.keySet()) {
            if (i == n) {
                labelName = clients.get(i).get(0);
                labelInit = clients.get(i).get(1);
            }
        }

        JLabel nameLbl = new JLabel(labelName);
        JLabel initLbl = new JLabel(labelInit);
        JButton button = new JButton("Back");

        button.addActionListener(e -> {
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, ROOT_CARD);
        });

        card.add(nameLbl);
        card.add(initLbl);
        card.add(button);
    }

    public void buildRootCard(Container card) {
        card.setLayout(new FlowLayout());
        card.setPreferredSize(dimension);

        JLabel label = new JLabel("Card 1");
        JButton button = new JButton("Add Label");

        button.addActionListener(e -> {
            // to add label card
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, ADD_LABEL_CARD);
        });

        card.add(label);
        card.add(button);
    }

    public void buildGUI(Container pane) {
        JPanel card1 = new JPanel();
        JPanel addLabelCard = new JPanel();

        rootCard = card1;

        buildRootCard(card1);
        buildAddClientCard(addLabelCard);

        this.cards = new JPanel(new CardLayout());
        this.cards.add(card1, ROOT_CARD);
        this.cards.add(addLabelCard, ADD_LABEL_CARD);

        pane.add(cards);
    }

    public void clientAdded(Client client, JLabel lbl) {
        int id = client.getId();
        List<String> pi = new ArrayList<>();
        pi.add(client.getName());
        pi.add(client.getPI().getInitials());
        this.clients.put(id, pi);

        // Create new card for new input
        JPanel newCard = new JPanel();
        buildNewCard(newCard, id);
        cards.add(newCard, CARD + count);

        lbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, CARD + e.getComponent().getName());
            }
        });
        rootCard.revalidate();
        rootCard.repaint();

        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, ROOT_CARD);
    }

    public void errorMessage(String msg) {
        JOptionPane.showMessageDialog(null, "ERROR: " + msg);
    }

    public List<String> getClientData() {
        return clientData;
    }
}
