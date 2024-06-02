package it.unisalento.pasproject.scoreservice.exceptions;

import it.unisalento.pasproject.scoreservice.exceptions.global.CustomErrorException;
import org.springframework.http.HttpStatus;

public class InvalidResourceType extends CustomErrorException {
    public InvalidResourceType(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}