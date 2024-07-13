package it.unisalento.pasproject.scoreservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) for encapsulating a list of GPU entities.
 * This class is used to transfer a list of GPU data between different layers of the application,
 * effectively encapsulating GPU details in a list format.
 */
@Getter
@Setter
public class GpuListDTO {
    private List<GpuDTO> gpuList; // List of GPU DTOs

    /**
     * Constructs a new GpuListDTO with an empty list of GPU DTOs.
     * This is useful for initializing the object without any GPU data.
     */
    public GpuListDTO() {
        this.gpuList = new ArrayList<>();
    }
}