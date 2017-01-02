package model;

import java.util.ArrayList;
import java.util.List;

/**
 * User of the program (the therapist). Contains a list of Clients and Sessions.
 *
 * Author:  Martijn
 * Date:    2-1-2017
 */
public class User implements Person {

    /** The User's unique identifier. */
    private int id;
    /** The User's name. */
    private String name;
    /** The User's personal information. */
    private PersonalInformation pi;
    /** The User's client list. */
    private List<Client> clients;
    /** The User's session list. */
    private List<Session> sessions;

    public User(int id, String name, PersonalInformation pi) {
        this.id = id;
        this.name = name;
        this.pi = pi;
        this.clients = new ArrayList<>();
        this.sessions = new ArrayList<>();
    }

    public User(int id, String name) {
        this(id, name, new PersonalInformation());
    }

    /**
     * Gives a list of all sessions a given client participated in. Returns an empty list if the client did not
     * participate in any session.
     * @param client The client that is to be checked.
     * @return a list of sessions that the client participated in. An empty list if client did not participate in
     * any session.
     */
    public List<Session> getClientSessions(Client client) {
        List<Session> sl = new ArrayList<>();
        for (Session s : sessions) {
            for (Client c : s.getParticipants()) {
                if (client.equals(c)) {
                    sl.add(s);
                    break;
                }
            }
        }
        return sl;
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    @Override
    public boolean equals(Person p) {
        return p instanceof User && (p == this || p.getId() == id);
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
