package it.unisalento.pasproject.scoreservice.exceptions;

import it.unisalento.pasproject.scoreservice.exceptions.global.CustomErrorException;
import org.springframework.http.HttpStatus;

public class UserNotAuthorizedException extends CustomErrorException {

    public UserNotAuthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
