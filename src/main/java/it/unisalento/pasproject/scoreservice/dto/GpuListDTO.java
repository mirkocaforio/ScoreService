package it.unisalento.pasproject.scoreservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GpuListDTO {
    private List<GpuDTO> gpuList;

    public GpuListDTO() {
        this.gpuList = new ArrayList<>();
    }
}
