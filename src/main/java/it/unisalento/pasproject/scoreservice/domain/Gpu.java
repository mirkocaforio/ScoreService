package it.unisalento.pasproject.scoreservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "gpuResources")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Gpu {
    @Id
    private int id;
    private String name;
    private double opencl;
    private double vulkan;
    private double metal;
    private double cuda;
}