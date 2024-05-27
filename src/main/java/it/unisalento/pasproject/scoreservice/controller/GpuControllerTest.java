package it.unisalento.pasproject.scoreservice.controller;

import it.unisalento.pasproject.scoreservice.domain.Gpu;
import it.unisalento.pasproject.scoreservice.repositories.GpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gpus")
public class GpuControllerTest {
    @Autowired
    private GpuRepository gpuRepository;

    @GetMapping
    public List<Gpu> getAllGpus() {
        return gpuRepository.findAll();
    }
}