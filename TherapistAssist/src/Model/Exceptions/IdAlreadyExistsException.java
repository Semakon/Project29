package model.exceptions;

/**
 * Author:  Martijn
 * Date:    4-1-2017
 */
public class IdAlreadyExistsException extends RuntimeException {

    public IdAlreadyExistsException() {
        super("The generated ID of is invalid or already in use.");
    }

}
