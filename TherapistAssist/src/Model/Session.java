package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A therapy session. Contains the User who runs the session and a list of participating clients.
 *
 * Author:  Martijn
 * Date:    2-1-2017
 */
public class Session {

    /** The Session's unique identifier. */
    private int id;
    /** The User (therapist) of the session. */
    private User therapist;
    /** The Session's participating clients. */
    private List<Client> participants;
    /** The date the session happened. */
    private String date;

    public Session(int id, User therapist, String date, Client... clients) {
        this.id = id;
        this. therapist = therapist;
        this.date = date;
        participants = new ArrayList<>();
        Collections.addAll(participants, clients);
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

    public List<Client> getParticipants() {
        return participants;
    }

}
