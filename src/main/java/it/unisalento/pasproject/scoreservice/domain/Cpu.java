package it.unisalento.pasproject.scoreservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "cpuResources")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cpu {
    @Id
    private int id;
    private String name;
    private String description;
    private double score;
    private double multicore_score;
}