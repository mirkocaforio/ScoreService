package it.unisalento.pasproject.scoreservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a GPU resource in the system.
 * This class is mapped to the "gpuResources" collection in MongoDB.
 * It includes properties for the GPU's ID, name, description, and performance scores across different APIs.
 */
@Getter
@Setter
@Document(collection = "gpuResources")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Gpu {
    @Id
    private int id; // Unique identifier for the GPU
    private String name; // Name of the GPU
    private String description; // Description of the GPU
    private double opencl; // Performance score using OpenCL
    private double vulkan; // Performance score using Vulkan
    private double metal; // Performance score using Metal
    private double cuda; // Performance score using CUDA
}