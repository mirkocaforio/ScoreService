package it.unisalento.pasproject.scoreservice.controller;

import it.unisalento.pasproject.scoreservice.domain.Gpu;
import it.unisalento.pasproject.scoreservice.repositories.GpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gpus")
public class GpuControllerTest {
    private final GpuRepository gpuRepository;

    @Autowired
    public GpuControllerTest(GpuRepository gpuRepository) {
        this.gpuRepository = gpuRepository;
    }

    @GetMapping
    public List<Gpu> getAllGpus() {
        return gpuRepository.findAll();
    }

    @GetMapping("/names")
    public List<String> getAllGpuNames() {
        return gpuRepository.findAll().stream()
                .map(Gpu::getName)
                .collect(Collectors.toList());
    }
}
