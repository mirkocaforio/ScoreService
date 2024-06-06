package it.unisalento.pasproject.scoreservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CpuDTO {
    private int id;
    private String name;
    private String description;
    private double score;
    private double multicore_score;
}
