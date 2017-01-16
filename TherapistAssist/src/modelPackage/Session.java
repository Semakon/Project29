package modelPackage;

/**
 * A therapy session. Contains the User who runs the session and the participating group.
 *
 * Author:  Martijn
 * Date:    2-1-2017
 */
public class Session {

    /** The Session's unique identifier. */
    private int id;

    /** The User (therapist) of the session. */
    private User therapist; //TODO: find out: necessary?

    /** The Session's participating session owner. */
    private SessionOwner owner;
    /** The date the session happened. */
    private String date;
    /** The session's graph. */
    private LineGraph graph;

    public Session(int id, User therapist, String date, SessionOwner sessionOwner) {
        this.id = id;
        this. therapist = therapist;
        this.date = date;
        this.owner = sessionOwner;
    }

    public boolean isInSession(Client client) {
        if (owner instanceof Client) {
            return client.equals((Client)owner);
        }
        Group group = (Group)owner;
        return group.isInGroup(client);
    }

    public boolean equals(Session session) {
        return session == this || session.getId() == id;
    }

    public int getId() {
        return id;
    }

    public User getTherapist() {
        return therapist;
    }

    public String getDate() {
        return date;
    }

    public SessionOwner getOwner() {
        return owner;
    }
}
