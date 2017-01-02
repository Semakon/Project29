package model;

/**
 * Client (also patient) of a User.
 *
 * Author:  Martijn
 * Date:    2-1-2017
 */
public class Client implements Person {

    private int id;
    private String name;
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
