package it.unisalento.pasproject.scoreservice.exceptions;

import it.unisalento.pasproject.scoreservice.exceptions.global.CustomErrorException;
import org.springframework.http.HttpStatus;

public class AccessDeniedException extends CustomErrorException {
    public AccessDeniedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
