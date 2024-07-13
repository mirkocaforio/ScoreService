package it.unisalento.pasproject.scoreservice.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for CPU entities.
 * This class is used to transfer CPU data between different layers of the application,
 * encapsulating the CPU's id, name, description, score, and multicore score.
 */
@Getter
@Setter
public class CpuDTO {
    private int id; // Unique identifier for the CPU
    private String name; // Name of the CPU
    private String description; // Description of the CPU's features and capabilities
    private double score; // Performance score of the CPU
    private double multicore_score; // Performance score of the CPU when using multiple cores
}