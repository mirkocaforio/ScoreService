package it.unisalento.pasproject.scoreservice.controller;

import it.unisalento.pasproject.scoreservice.domain.Cpu;
import it.unisalento.pasproject.scoreservice.repositories.CpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cpus")
public class CpuControllerTest {
    private final CpuRepository cpuRepository;

    @Autowired
    public CpuControllerTest(CpuRepository cpuRepository) {
        this.cpuRepository = cpuRepository;
    }

    @GetMapping
    public List<Cpu> getAllCpus() {
        return cpuRepository.findAll();
    }

    @GetMapping("/names")
    public List<String> getAllCpuNames() {
        return cpuRepository.findAllNames();
    }
}
