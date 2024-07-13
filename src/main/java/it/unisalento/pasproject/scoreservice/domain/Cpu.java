package it.unisalento.pasproject.scoreservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a CPU resource in the system.
 * This class is mapped to the "cpuResources" collection in MongoDB.
 * It includes properties for the CPU's ID, name, description, score, and multicore score.
 */
@Getter
@Setter
@Document(collection = "cpuResources")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cpu {
    @Id
    private int id; // Unique identifier for the CPU
    private String name; // Name of the CPU
    private String description; // Description of the CPU
    private double score; // Performance score of the CPU
    private double multicore_score; // Multicore performance score of the CPU
}