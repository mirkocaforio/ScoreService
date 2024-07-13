package it.unisalento.pasproject.scoreservice.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for conveying score messages.
 * This class encapsulates the name and type of resource, typically used to communicate
 * scoring information or updates between different layers of the application.
 */
@Getter
@Setter
public class ScoreMessageDTO {
    private String resourceName; // The name of the resource
    private String resourceType; // The type of the resource (e.g., GPU, CPU)
}