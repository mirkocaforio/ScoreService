package it.unisalento.pasproject.scoreservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) for encapsulating a list of CPU entities.
 * This class is used to transfer a list of CPU data between different layers of the application,
 * effectively encapsulating CPU details in a list format.
 */
@Getter
@Setter
public class CpuListDTO {
    private List<CpuDTO> cpuList; // List of CPU DTOs

    /**
     * Constructs a new CpuListDTO with an empty list of CPU DTOs.
     */
    public CpuListDTO() {
        this.cpuList = new ArrayList<>();
    }
}