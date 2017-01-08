package modelPackage;

/**
 * Client (a.k.a. patient) of a User.
 *
 * Author:  Martijn
 * Date:    2-1-2017
 */
public class Client implements Person {

    /** The Client's unique identifier. */
    private int cid;
    /** The Client's name. */
    private String name;
    /** The Client's personal information. */
    private PersonalInformation pi;

    public Client(int cid, String name, PersonalInformation pi) {
        this.cid = cid;
        this.name = name;
        this.pi = pi;
    }

    public Client(int cid, String name) {
        this(cid, name, new PersonalInformation());
    }

    @Override
    public boolean equals(Person p) {
        return p instanceof Client && (p == this || p.getId() == cid);
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

}
