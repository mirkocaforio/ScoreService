package it.unisalento.pasproject.scoreservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) for encapsulating a list of ResourceName entities.
 * This class is used to transfer a list of resource names between different layers of the application,
 * effectively encapsulating resource name details in a list format.
 */
@Getter
@Setter
public class ResourceNameListDTO {
    private List<ResourceNameDTO> list; // List of ResourceName DTOs

    /**
     * Constructs a new ResourceNameListDTO with an empty list of ResourceName DTOs.
     * This is useful for initializing the object without any resource name data.
     */
    public ResourceNameListDTO() {
        this.list = new ArrayList<>();
    }
}