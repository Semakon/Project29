package modelPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * A group of Clients that share sessions together.
 *
 * Author:  Martijn
 * Date:    4-1-2017
 */
public class Group {

    private int gid;
    private String groupName;
    private List<Client> participants;
    private String anamnesis;
    private String helpQuestion;

    public Group(int gid) {
        this.gid = gid;
        participants = new ArrayList<>();
    }

    /**
     * Adds a Client to this group. This method requires the Client parameter to be in the
     * User's Client list.
     * @param client The Client to be added.
     * @return true if client was added successfully, false if client was already in this group.
     */
    public boolean addClient(Client client) {
        for (Client c : participants) {
            if (client.equals(c)) return false;
        }
        participants.add(client);
        return true;
    }

    /**
     * Removes a Client from this group.
     * @param client The Client to be removed.
     * @return true if client was removed successfully, false if client was not in this group.
     */
    public boolean removeClient(Client client) {
        for (Client c : participants) {
            if (client.equals(c)) {
                participants.remove(c);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a given Client is a participant of this group.
     * @param client The Client to be checked.
     * @return true if the client is in this group, false if not.
     */
    public boolean isInGroup(Client client) {
        for (Client c : participants) {
            if (client.equals(c)) return true;
        }
        return false;
    }

    public boolean equals(Group group) {
        return group == this || group.getGid() == gid;
    }

    public int getGid() {
        return gid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAnamnesis() {
        return anamnesis;
    }

    public void setAnamnesis(String anamnesis) {
        this.anamnesis = anamnesis;
    }

    public String getHelpQuestion() {
        return helpQuestion;
    }

    public void setHelpQuestion(String helpQuestion) {
        this.helpQuestion = helpQuestion;
    }

}
