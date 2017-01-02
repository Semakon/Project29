package model;

import java.util.ArrayList;
import java.util.List;

/**
 * User of the program. Has a list of Clients.
 *
 * Author:  Martijn
 * Date:    2-1-2017
 */
public class User implements Person {

    private int id;
    private String name;
    private PersonalInformation pi;
    private List<Client> clientList;

    public User(int id, String name, PersonalInformation pi) {
        this.id = id;
        this.name = name;
        this.pi = pi;
        this.clientList = new ArrayList<Client>();
    }

    public User(int id, String name) {
        this(id, name, new PersonalInformation());
    }

    public List<Client> getClientList() {
        return clientList;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PersonalInformation getPI() {
        return pi;
    }

}
