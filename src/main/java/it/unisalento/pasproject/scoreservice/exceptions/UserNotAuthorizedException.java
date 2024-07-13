package it.unisalento.pasproject.scoreservice.exceptions;

import it.unisalento.pasproject.scoreservice.exceptions.global.CustomErrorException;
import org.springframework.http.HttpStatus;

/**
 * Custom exception class representing an unauthorized user error within the application.
 * This exception is thrown when a user attempts to perform an action without the necessary authorization.
 * It extends {@link CustomErrorException} to provide a structured response with an HTTP status of UNAUTHORIZED.
 */
public class UserNotAuthorizedException extends CustomErrorException {

    /**
     * Constructs a UserNotAuthorizedException with a specific message.
     * The HTTP status is set to UNAUTHORIZED (401), indicating that the request has not been applied because it lacks
     * valid authentication credentials for the target resource.
     *
     * @param message the detail message associated with this exception.
     */
    public UserNotAuthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}