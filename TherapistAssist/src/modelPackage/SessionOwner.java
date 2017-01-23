package modelPackage;

import java.util.List;

/**
 * Author:  Martijn
 * Date:    16-1-2017
 */
public interface SessionOwner {

    List<Session> getSessions();
    void addSession(Session session);
    boolean equals(Object o);

}
