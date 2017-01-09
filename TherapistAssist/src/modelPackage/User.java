package modelPackage;

import modelPackage.exceptions.IdAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User of the program (the therapist). Contains a list of Clients and Sessions.
 *
 * Author:  Martijn
 * Date:    2-1-2017
 */
public class User implements Person {

    /** The User's unique identifier. */
    private int uid;
    /** The User's name. */
    private String name;
    /** The User's personal information. */
    private PersonalInformation pi;
    /** The User's Archive. */
    private Archive archive;
    /** The User's client list. */
    private List<Client> clients;
    /** The User's session list. */
    private List<Session> sessions;
    /** The User's groups of clients. */
    private List<Group> groups;
    /** A counter that keeps track of the used Group IDs. */
    private int gidCounter;
    /** A counter that keeps track of the used Client IDs. */
    private int cidCounter;

    public User(int uid, String name, PersonalInformation pi) {
        this.uid = uid;
        this.name = name;
        this.pi = pi;
        this.archive = new Archive();
        this.clients = new ArrayList<>();
        this.sessions = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.gidCounter = 0;
        this.cidCounter = 0;
    }

    public User(int uid, String name) {
        this(uid, name, new PersonalInformation());
    }

    /**
     * Creates a new Client with an automatically generated ID, a given name, and a given pi. An
     * IdAlreadyExistsException() is thrown when the generated ID is already in use.
     * @param name The new Client's name.
     * @param pi The new Client's PersonalInformation.
     * @return The added Client.
     */
    public Client addClient(String name, PersonalInformation pi) {
        int cid = cidCounter++;
        for (Client cl : clients) {
            if (cl.getId() == cid) {
                throw new IdAlreadyExistsException();
            }
        }
        Client c = pi == null? new Client(cid, name) : new Client(cid, name, pi);
        clients.add(c);
        return c;
    }

    /**
     * Archives a Client with the given Client ID.
     * @param cid The Client ID of the Client to be archived.
     * @return true if the client was archived successfully, false if the client is not in the
     * client list.
     */
    public boolean archiveClient(int cid) {
        for (Client c : clients) {
            if (c.getId() == cid) {
                archive.archiveClient(c);
                clients.remove(c);
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a new group with an automatically generated ID. An IdAlreadyExistsException() is thrown
     * when the newly generated ID is already in use.
     * @return The added Group.
     */
    public Group addGroup() {
        int gid = gidCounter++;
        for (Group g : groups) {
            if (g.getGid() == gid) {
                throw new IdAlreadyExistsException();
            }
        }
        Group g = new Group(gid);
        groups.add(g);
        return g;
    }

    /**
     * Archives a Group with the given Group ID.
     * @param gid The Group ID of the Group to be archived.
     * @return true if the group was archived successfully, false if the group is not in the
     * group list.
     */
    public boolean archiveGroup(int gid) {
        for (Group g : groups) {
            if (g.getGid() == gid) {
                archive.archiveGroup(g);
                groups.remove(g);
                return true;
            }
        }
        return false;
    }

    /**
     * Gives a list of all sessions a given client participated in. Returns an empty list if
     * the client did not
     * participate in any session.
     * @param client The client that is to be checked.
     * @return a list of sessions that the client participated in. An empty list if client
     * did not participate in
     * any session.
     */
    public List<Session> getClientSessions(Client client) {
        return sessions.stream().filter(s -> s.isInSession(client)).collect(Collectors.toList());
    }

    /**
     * Searches for a client with cid as their Client ID.
     * @param cid The Client ID.
     * @return The client that has this Client ID or null if the client is not in the client list.
     */
    public Client getClientByCid(int cid) {
        for (Client c : clients) {
            if (c.getId() == cid) return c;
        }
        return null;
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public Archive getArchive() {
        return archive;
    }

    @Override
    public boolean equals(Person p) {
        return p instanceof User && (p == this || p.getId() == uid);
    }

    @Override
    public int getId() {
        return uid;
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
