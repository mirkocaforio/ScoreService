package it.unisalento.pasproject.scoreservice.controller;

import it.unisalento.pasproject.scoreservice.dto.CpuListDTO;
import it.unisalento.pasproject.scoreservice.dto.ResourceNameListDTO;
import it.unisalento.pasproject.scoreservice.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling requests related to CPU scores.
 * This controller provides endpoints for retrieving CPU score data and CPU names.
 */
@RestController
@RequestMapping("/api/scores")
public class CpuController {
    private final ScoreService scoreService;

    /**
     * Constructs a CpuController with a dependency on ScoreService.
     * @param scoreService The service responsible for retrieving score data.
     */
    @Autowired
    public CpuController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    /**
     * Endpoint to retrieve a list of all CPUs with their scores.
     * @return A CpuListDTO containing a list of CPUs and their scores.
     */
    @GetMapping(value = "/cpus")
    public CpuListDTO getAllCpus() {
        return scoreService.getAllCpus();
    }

    /**
     * Endpoint to retrieve a list of all CPU names.
     * @return A ResourceNameListDTO containing a list of CPU names.
     */
    @GetMapping(value = "/cpus/names")
    public ResourceNameListDTO getAllCpuNames() {
        return scoreService.getAllCpuNames();
    }
}