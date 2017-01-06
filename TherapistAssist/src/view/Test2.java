package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:  Martijn
 * Date:    6-1-2017
 */
public class Test2 {

    private JPanel cards;
    private List<JLabel> clients;
    private int count;
    private Dimension dimension = new Dimension(400, 400);

    public static final String CARD = "Card";

    public Test2() {
        clients = new ArrayList<>();
        count = 1;
    }

    public void simpleCard(Container card, int n) {
        card.setLayout(new FlowLayout());
        card.setPreferredSize(dimension);

        JLabel label = new JLabel("Card " + n);
        JButton button = new JButton("Back");

        button.addActionListener(e -> {
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, CARD + 1);
        });

        card.add(label);
        card.add(button);
    }

    public void buildCard(Container card, int n) {
        card.setLayout(new FlowLayout());
        card.setPreferredSize(dimension);

        JLabel label = new JLabel("Card " + n);
        JButton button = new JButton("Add Label");

        button.addActionListener(e -> {
            JLabel lbl = new JLabel("Label-" + ++count);
            clients.add(lbl);
            card.add(lbl);
            lbl.setName("" + count);

            JPanel newCard = new JPanel();
            simpleCard(newCard, count);
            cards.add(newCard, CARD + count);
            System.out.println(CARD + count);

            lbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards, CARD + e.getComponent().getName());
                    System.out.println(CARD + e.getComponent().getName());
                }
            });
            card.revalidate();
            card.repaint();
        });

        card.add(label);
        card.add(button);
    }

    public void buildGUI(Container pane) {
        JPanel card1 = new JPanel();

        buildCard(card1, 1);

        this.cards = new JPanel(new CardLayout());
        this.cards.add(card1, CARD + 1);

        pane.add(cards);
    }

    private static void createAndShowGUI() {
        // Create and set up window.
        JFrame frame = new JFrame("CardsLayout Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 400));

        // Create and set up content pane.
        Test2 test = new Test2();
        test.buildGUI(frame.getContentPane());

        // Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Test2::createAndShowGUI);
    }
}
