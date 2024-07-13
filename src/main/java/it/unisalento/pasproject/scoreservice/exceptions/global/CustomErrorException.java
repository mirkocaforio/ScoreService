package it.unisalento.pasproject.scoreservice.exceptions.global;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Abstract base class for custom error exceptions within the application.
 * This class extends RuntimeException and provides a structured way to handle errors
 * by encapsulating error details in a {@link CustomErrorResponse} object.
 */
@Getter
public abstract class CustomErrorException extends RuntimeException {
    private CustomErrorResponse errorResponse;

    /**
     * Constructs a new CustomErrorException with a specific message and HTTP status.
     * Initializes a {@link CustomErrorResponse} with the provided message, status,
     * current timestamp, and a randomly generated trace ID.
     *
     * @param message the detail message associated with the exception.
     * @param status the HTTP status code that should be returned.
     */
    public CustomErrorException(String message, HttpStatus status) {
        super(message);
        this.errorResponse = CustomErrorResponse.builder()
                .status(status)
                .message(message)
                .timestamp(OffsetDateTime.now().toString())
                .traceId(UUID.randomUUID().toString())
                .build();
    }
}