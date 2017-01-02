package model;

/**
 * Interface for people.
 *
 * Author:  Martijn
 * Date:    2-1-2017
 */
public interface Person {

    /** Checks whether p is of the same Class and with the same id as this object. */
    boolean equals(Person p);

    int getId();
    String getName();
    PersonalInformation getPI();

}
