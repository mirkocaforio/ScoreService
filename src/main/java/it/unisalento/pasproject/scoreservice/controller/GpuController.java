package it.unisalento.pasproject.scoreservice.controller;

import it.unisalento.pasproject.scoreservice.dto.GpuListDTO;
import it.unisalento.pasproject.scoreservice.dto.ResourceNameListDTO;
import it.unisalento.pasproject.scoreservice.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling requests related to GPU scores.
 * This controller provides endpoints for retrieving GPU score data and GPU names.
 */
@RestController
@RequestMapping("api/scores")
public class GpuController {
    private final ScoreService scoreService;

    /**
     * Constructs a GpuController with a dependency on ScoreService.
     * @param scoreService The service responsible for retrieving score data.
     */
    @Autowired
    public GpuController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    /**
     * Endpoint to retrieve a list of all GPUs with their scores.
     * @return A GpuListDTO containing a list of GPUs and their scores.
     */
    @GetMapping(value = "/gpus")
    public GpuListDTO getAllGpus() {
        return scoreService.getAllGpus();
    }

    /**
     * Endpoint to retrieve a list of all GPU names.
     * @return A ResourceNameListDTO containing a list of GPU names.
     */
    @GetMapping(value = "/gpus/names")
    public ResourceNameListDTO getAllGpuNames() {
        return scoreService.getAllGpuNames();
    }
}