package it.unisalento.pasproject.scoreservice.exceptions;

import it.unisalento.pasproject.scoreservice.exceptions.global.CustomErrorException;
import org.springframework.http.HttpStatus;

/**
 * Custom exception class representing an access denied error within the application.
 * This exception is thrown when an operation is attempted without the necessary permissions.
 * It extends {@link CustomErrorException} to provide a structured response with an HTTP status of FORBIDDEN.
 */
public class AccessDeniedException extends CustomErrorException {
    /**
     * Constructs an AccessDeniedException with a specific message.
     * The HTTP status is set to FORBIDDEN (403).
     *
     * @param message the detail message associated with this exception.
     */
    public AccessDeniedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}