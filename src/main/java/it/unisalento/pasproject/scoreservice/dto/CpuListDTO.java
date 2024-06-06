package it.unisalento.pasproject.scoreservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CpuListDTO {
    private List<CpuDTO> cpuList;

    public CpuListDTO() {
        this.cpuList = new ArrayList<>();
    }
}
