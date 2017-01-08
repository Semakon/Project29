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

    /** The Session's participating group. */
    private Group group;
    /** The date the session happened. */
    private String date;

    public Session(int id, User therapist, String date, Group group) {
        this.id = id;
        this. therapist = therapist;
        this.date = date;
        this.group = group;
    }

    public boolean isInSession(Client client) {
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

    public Group getGroup() {
        return group;
    }

}
