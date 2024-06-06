package it.unisalento.pasproject.scoreservice.controller;

import it.unisalento.pasproject.scoreservice.dto.CpuListDTO;
import it.unisalento.pasproject.scoreservice.dto.ResourceNameListDTO;
import it.unisalento.pasproject.scoreservice.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scores")
public class CpuController {
    private final ScoreService scoreService;

    @Autowired
    public CpuController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }


    @GetMapping(value = "/cpus")
    public CpuListDTO getAllCpus() {
        return scoreService.getAllCpus();
    }

    @GetMapping(value = "cpus/names")
    public ResourceNameListDTO getAllCpuNames() {
        return scoreService.getAllCpuNames();
    }
}
