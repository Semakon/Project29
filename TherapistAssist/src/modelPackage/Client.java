package modelPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Client (a.k.a. patient) of a User.
 *
 * Author:  Martijn
 * Date:    2-1-2017
 */
public class Client implements Person, SessionOwner {

    /** The Client's unique identifier. */
    private int cid;
    /** The Client's name. */
    private String name;
    /** The Client's personal information. */
    private PersonalInformation pi;
    private List<Session> sessions;

    public Client(int cid, String name, PersonalInformation pi) {
        this.cid = cid;
        this.name = name;
        this.pi = pi;
        this.sessions = new ArrayList<>();
    }

    public Client(int cid, String name) {
        this(cid, name, new PersonalInformation());
    }

    public boolean addSession(Session session) {
        for (Session s : sessions) {
            if (session.equals(s)) return false;
        }
        this.sessions.add(session);
        return true;
    }

    @Override
    public boolean equals(Person p) {
        return p instanceof Client && (p == this || p.getId() == cid);
    }

    @Override
    public int getId() {
        return cid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PersonalInformation getPI() {
        return pi;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    @Override
    public String toString() {
        return name;
    }

}
