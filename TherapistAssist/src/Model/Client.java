package model;

/**
 * Client (a.k.a. patient) of a User.
 *
 * Author:  Martijn
 * Date:    2-1-2017
 */
public class Client implements Person {

    /** The Client's unique identifier. */
    private int id;
    /** The Client's name. */
    private String name;
    /** The Client's personal information. */
    private PersonalInformation pi;

    public Client(int id, String name, PersonalInformation pi) {
        this.id = id;
        this.name = name;
        this.pi = pi;
    }

    public Client(int id, String name) {
        this(id, name, new PersonalInformation());
    }

    @Override
    public boolean equals(Person p) {
        return p instanceof Client && (p == this || p.getId() == id);
    }

    @Override
    public int getId() {
        return id;
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
