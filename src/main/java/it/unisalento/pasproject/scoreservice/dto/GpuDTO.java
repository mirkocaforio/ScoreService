package it.unisalento.pasproject.scoreservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GpuDTO {
    private int id;
    private String name;
    private String description;
    private double opencl;
    private double vulkan;
    private double metal;
    private double cuda;
}
