package it.unisalento.pasproject.scoreservice.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for Score.
 * This class is used to transfer score data between different layers of the application,
 * encapsulating various performance scores including single-core, multi-core, and GPU-specific scores.
 */
@Getter
@Setter
public class ScoreDTO {
    private double score; // Single-core performance score
    private double multicore_score; // Multi-core performance score
    private double opencl; // Performance score using OpenCL
    private double vulkan; // Performance score using Vulkan
    private double cuda; // Performance score using CUDA
}