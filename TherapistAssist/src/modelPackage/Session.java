package modelPackage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A therapy session. Contains the User who runs the session and the participating group.
 *
 * Author:  Martijn
 * Date:    2-1-2017
 */
public class Session {

    /** The Session's unique identifier. */
    private int id;
    /** The session's name. */
    private String name;

    /** The User (therapist) of the session. */
    private User therapist; //TODO: find out: necessary?

    /** The Session's participating session owner. */
    private SessionOwner owner;
    /** The date the session happened. */
    private String date;
    /** The session's graph data. */
    private GraphData graphData;

    public Session(int id, User therapist, SessionOwner sessionOwner) {
        this.id = id;
        this.name = "Session " + id;
        this. therapist = therapist;
        this.owner = sessionOwner;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        this.date = dateFormat.format(date);

        this.graphData = new GraphData();
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

    public GraphData getGraphData() {
        return graphData;
    }

    public String getName() {
        return name;
    }
}
