package it.unisalento.pasproject.scoreservice.service;

import it.unisalento.pasproject.scoreservice.repositories.CpuRepository;
import it.unisalento.pasproject.scoreservice.repositories.GpuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
    private CpuRepository cpuRepository;
    private GpuRepository gpuRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoreService.class);

    public ScoreService() {}


}
