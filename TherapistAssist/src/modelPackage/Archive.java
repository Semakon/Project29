package modelPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * An archive keeping track of all archived data of a User.
 *
 * Author:  Martijn
 * Date:    4-1-2017
 */
public class Archive {

    private List<Client> archivedClients;
    private List<Group> archivedGroups;
    private List<Session> archivedSessions;

    public Archive() {
        archivedClients = new ArrayList<>();
        archivedGroups = new ArrayList<>();
        archivedSessions = new ArrayList<>();
    }

    /**
     * Adds a Client to the Archive.
     * @param client The Client to be archived.
     */
    public void archiveClient(Client client) {
        archivedClients.add(client);
    }

    /**
     * Adds a Group to the Archive.
     * @param group The Group to be archived.
     */
    public void archiveGroup(Group group) {
        archivedGroups.add(group);
    }

    /**
     * Adds a Session to the Archive.
     * @param session The Session to be archived.
     */
    public void archiveSession(Session session) {
        archivedSessions.add(session);
    }

    public List<Client> getArchivedClients() {
        return archivedClients;
    }

    public List<Group> getArchivedGroups() {
        return archivedGroups;
    }

    public List<Session> getArchivedSessions() {
        return archivedSessions;
    }

}
