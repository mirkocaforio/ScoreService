package it.unisalento.pasproject.scoreservice.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for Resource Name.
 * This class is used to transfer the name of a resource between different layers of the application,
 * encapsulating the resource's name in a simple, reusable format.
 */
@Getter
@Setter
public class ResourceNameDTO {
    private String name; // Name of the resource
}