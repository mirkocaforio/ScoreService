package it.unisalento.pasproject.scoreservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResourceNameListDTO {
    private List<ResourceNameDTO> list;

    public ResourceNameListDTO() {
        this.list = new ArrayList<>();
    }
}
