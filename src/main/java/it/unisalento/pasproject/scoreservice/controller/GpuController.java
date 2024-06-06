package it.unisalento.pasproject.scoreservice.controller;

import it.unisalento.pasproject.scoreservice.dto.GpuListDTO;
import it.unisalento.pasproject.scoreservice.dto.ResourceNameListDTO;
import it.unisalento.pasproject.scoreservice.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/scores")
public class GpuController {
    private final ScoreService scoreService;

    @Autowired
    public GpuController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping(value = "/gpus")
    public GpuListDTO getAllGpus() {
        return scoreService.getAllGpus();
    }

    @GetMapping(value = "/gpus/names")
    public ResourceNameListDTO getAllGpuNames() {
        return scoreService.getAllGpuNames();
    }
}
