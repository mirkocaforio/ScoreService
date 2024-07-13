package it.unisalento.pasproject.scoreservice.exceptions;

import it.unisalento.pasproject.scoreservice.exceptions.global.CustomErrorException;
import org.springframework.http.HttpStatus;

/**
 * Custom exception class representing an invalid resource type error within the application.
 * This exception is thrown when an operation is attempted on a resource of an incorrect type.
 * It extends {@link CustomErrorException} to provide a structured response with an HTTP status of BAD_REQUEST.
 */
public class InvalidResourceType extends CustomErrorException {
    /**
     * Constructs an InvalidResourceType exception with a specific message.
     * The HTTP status is set to BAD_REQUEST (400), indicating that the server cannot or will not process the request due to something that is perceived to be a client error (e.g., malformed request syntax, invalid request message framing, or deceptive request routing).
     *
     * @param message the detail message associated with this exception.
     */
    public InvalidResourceType(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}