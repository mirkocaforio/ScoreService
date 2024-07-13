package it.unisalento.pasproject.scoreservice.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for GPU entities.
 * This class is used to transfer GPU data between different layers of the application,
 * encapsulating the GPU's id, name, description, and performance scores across different APIs.
 */
@Getter
@Setter
public class GpuDTO {
    private int id; // Unique identifier for the GPU
    private String name; // Name of the GPU
    private String description; // Description of the GPU's features and capabilities
    private double opencl; // Performance score using OpenCL
    private double vulkan; // Performance score using Vulkan
    private double metal; // Performance score using Metal
    private double cuda; // Performance score using CUDA
}