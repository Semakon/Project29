package view;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Author:  Martijn
 * Date:    5-1-2017
 */
public class UserProfileGUI extends Frame {

    private String userName;
    private Map<Integer, String> clients;
    private Map<Integer, String> groups;

    public UserProfileGUI() {

    }

    public void buildGUI() {
        Panel searchPanel = new Panel(new CardLayout());
        Label searchLabel = new Label("Search");
        TextField searchTxtField = new TextField("Search", 20);
        searchPanel.add(searchTxtField);
        searchPanel.add(searchLabel);



        setLayout(new FlowLayout());
        add(searchPanel);

        setTitle(userName + "'s profile");
        setSize(1000, 800);
        setVisible(true);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
