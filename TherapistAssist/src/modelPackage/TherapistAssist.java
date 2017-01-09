package modelPackage;

import modelPackage.exceptions.IdAlreadyExistsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The program keeping track of the Users.
 *
 * Author:  Martijn
 * Date:    4-1-2017
 */
public class TherapistAssist {

    /** List of Users. */
    private List<User> users;
    /** User ID Counter; for generating new User IDs. */
    private int uidCounter;
    /** Map of removed User IDs to their respective archives. */
    private Map<Integer, Archive> removedUsersArchives;

    public TherapistAssist() {
        this.users = new ArrayList<>();
        uidCounter = 0;
        removedUsersArchives = new HashMap<>();
    }

    /**
     * Adds a new user to the program with a generated ID and a given name.
     * @param name The name of the new User.
     * @throws IdAlreadyExistsException if the generated ID is already in use.
     */
    public void addUser(String name) throws IdAlreadyExistsException {
        int uid = uidCounter++;
        for (User u : users) {
            if (u.getId() == uid) {
                throw new IdAlreadyExistsException();
            }
        }
        User user = new User(uid, name);
        users.add(user);
    }

    /**
     * Removes a user from the program, but keeps its archive.
     * @param user User to be removed.
     * @return true if user was removed successfully, false if user was not in the user list.
     */
    public boolean removeUser(User user) { //TODO: exception?
        for (User u : users) {
            if (user.equals(u)) {
                removedUsersArchives.put(u.getId(), u.getArchive());
                users.remove(u);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a user with a given User ID from the program, but keeps its archive.
     * @param uid User ID of the user to be removed.
     * @return true if user was removed successfully, false if user was not in the user list.
     */
    public boolean removeUser(int uid) { //TODO: exception?
        for (User user : users) {
            if (user.getId() == uid) return removeUser(user);
        }
        return false;
    }

    /**
     * Adds a new Client to the current User with a given name.
     * @param currentUser Currently online User.
     * @param name Name of new Client.
     * @return The created client or null if the user does not exist.
     */
    public Client addClient(User currentUser, String name, PersonalInformation pi) {
        for (User u : users) {
            if (currentUser.equals(u)) {
                return currentUser.addClient(name, pi);
            }
        }
        return null;
    }

    /**
     * Archives the Client of the current User with the given Client ID.
     * @param currentUser Currently online User.
     * @param cid Client ID of Client to be archived.
     * @return true if Client was archived successfully, false if the currentUser or the Client do
     * not exist.
     */
    public boolean archiveClient(User currentUser, int cid) {
        for (User u : users) {
            if (currentUser.equals(u)) {
                return currentUser.archiveClient(cid);
            }
        }
        return false;
    }

    /**
     * Adds a new Group to the current User.
     * @param currentUser Currently online User.
     * @return the added group or null if the currentUser does not exist.
     */
    public Group addGroup(User currentUser) {
        for (User u : users) {
            if (currentUser.equals(u)) {
                return currentUser.addGroup();
            }
        }
        return null;
    }

    public boolean archiveGroup(User currentUser, int gid) {

        return false;
    }

    /**
     * Searches for a client with cid as their Client ID from the currentUser.
     * @param cid The Client ID.
     * @return The client that has this Client ID. null if the currentUser is not in the user list
     * or if the client is not in the client list of the currentUser.
     */
    public Client getClientByCid(User currentUser, int cid) {
        if (!users.contains(currentUser)) return null;
        return currentUser.getClientByCid(cid);
    }

    public List<User> getUsers() {
        return users;
    }

    public Map<Integer, Archive> getRemovedUsersArchives() {
        return removedUsersArchives;
    }
}
