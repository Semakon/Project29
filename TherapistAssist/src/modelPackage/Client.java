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
    /** The Session this Client has participated in. */
    private List<Session> sessions;
    /** Path to picture of Client. */
    private String picturePath;

    public Client(int cid, String name, PersonalInformation pi) {
        this.cid = cid;
        this.name = name;
        this.pi = pi;
        this.sessions = new ArrayList<>();
    }

    public Client(int cid, String name) {
        this(cid, name, new PersonalInformation());
    }

    @Override
    public void addSession(Session session) {
        for (Session s : sessions) {
            if (session.equals(s)) return;
        }
        this.sessions.add(session);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Client && (o == this || ((Client)o).getId() == cid);
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

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public String toString() {
        return name;
    }

}
