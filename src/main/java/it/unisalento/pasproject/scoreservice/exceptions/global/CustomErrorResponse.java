package it.unisalento.pasproject.scoreservice.exceptions.global;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Represents a custom error response structure for the application.
 * This class is used to encapsulate detailed information about errors that occur within the application,
 * including a unique trace ID for tracking, a timestamp, the HTTP status of the error, and a message describing the error.
 */
@Data // Lombok's annotation to generate getters, setters, toString, equals, and hashCode methods.
@NoArgsConstructor // Lombok's annotation to generate a no-argument constructor.
@AllArgsConstructor // Lombok's annotation to generate a constructor with arguments for all fields.
@Builder // Lombok's annotation to implement the Builder pattern for object creation.
public class CustomErrorResponse {
    private String traceId; // Unique identifier for the error instance, useful for tracing errors in logs.
    private String timestamp; // Timestamp when the error occurred.
    private HttpStatus status; // HTTP status code associated with the error.
    private String message; // Descriptive message detailing the error.
}