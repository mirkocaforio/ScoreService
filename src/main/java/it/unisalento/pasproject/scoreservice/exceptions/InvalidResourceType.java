package it.unisalento.pasproject.scoreservice.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidResourceType extends CustomErrorException {
    public InvalidResourceType(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}