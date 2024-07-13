package it.unisalento.pasproject.scoreservice.service;

import it.unisalento.pasproject.scoreservice.domain.Cpu;
import it.unisalento.pasproject.scoreservice.domain.Gpu;
import it.unisalento.pasproject.scoreservice.dto.*;
import it.unisalento.pasproject.scoreservice.repositories.CpuRepository;
import it.unisalento.pasproject.scoreservice.repositories.GpuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling operations related to CPU and GPU scores.
 * Provides methods to convert CPU and GPU entities to their respective DTOs,
 * retrieve all CPUs and GPUs, and fetch names of all CPUs and GPUs.
 */
@Service
public class ScoreService {
    private final CpuRepository cpuRepository;
    private final GpuRepository gpuRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoreService.class);

    /**
     * Constructs a ScoreService with necessary CPU and GPU repositories.
     *
     * @param cpuRepository The repository for CPU entities.
     * @param gpuRepository The repository for GPU entities.
     */
    public ScoreService(CpuRepository cpuRepository, GpuRepository gpuRepository) {
        this.cpuRepository = cpuRepository;
        this.gpuRepository = gpuRepository;
    }

    /**
     * Converts a Cpu entity to a CpuDTO.
     *
     * @param cpu The Cpu entity to convert.
     * @return The converted CpuDTO.
     */
    public CpuDTO getCpu(Cpu cpu) {
        CpuDTO cpuDTO = new CpuDTO();

        Optional.of(cpu.getId()).ifPresent(cpuDTO::setId);
        Optional.ofNullable(cpu.getName()).ifPresent(cpuDTO::setName);
        Optional.ofNullable(cpu.getDescription()).ifPresent(cpuDTO::setDescription);
        Optional.of(cpu.getScore()).ifPresent(cpuDTO::setScore);
        Optional.of(cpu.getMulticore_score()).ifPresent(cpuDTO::setMulticore_score);

        return cpuDTO;
    }

    /**
     * Converts a Gpu entity to a GpuDTO.
     *
     * @param gpu The Gpu entity to convert.
     * @return The converted GpuDTO.
     */
    public GpuDTO getGpu(Gpu gpu) {
        GpuDTO gpuDTO = new GpuDTO();

        Optional.of(gpu.getId()).ifPresent(gpuDTO::setId);
        Optional.ofNullable(gpu.getName()).ifPresent(gpuDTO::setName);
        Optional.ofNullable(gpu.getDescription()).ifPresent(gpuDTO::setDescription);
        Optional.of(gpu.getOpencl()).ifPresent(gpuDTO::setOpencl);
        Optional.of(gpu.getVulkan()).ifPresent(gpuDTO::setVulkan);
        Optional.of(gpu.getMetal()).ifPresent(gpuDTO::setMetal);
        Optional.of(gpu.getCuda()).ifPresent(gpuDTO::setCuda);

        return gpuDTO;
    }

    /**
     * Retrieves all CPUs and converts them to a list of CpuDTOs.
     *
     * @return A CpuListDTO containing a list of CpuDTOs.
     */
    public CpuListDTO getAllCpus() {
        CpuListDTO cpuListDTO = new CpuListDTO();
        cpuListDTO.setCpuList(cpuRepository.findAll().stream().map(this::getCpu).toList());
        return cpuListDTO;
    }

    /**
     * Retrieves all GPUs and converts them to a list of GpuDTOs.
     *
     * @return A GpuListDTO containing a list of GpuDTOs.
     */
    public GpuListDTO getAllGpus() {
        GpuListDTO gpuListDTO = new GpuListDTO();
        gpuListDTO.setGpuList(gpuRepository.findAll().stream().map(this::getGpu).toList());
        return gpuListDTO;
    }

    /**
     * Retrieves names of all CPUs and wraps them in a ResourceNameListDTO.
     *
     * @return A ResourceNameListDTO containing names of all CPUs.
     */
    public ResourceNameListDTO getAllCpuNames() {
        ResourceNameListDTO resourceNameListDTO = new ResourceNameListDTO();

        List<String> cpuNames = cpuRepository.findAll().stream().map(Cpu::getName).toList();

        for (String cpuName : cpuNames) {
            ResourceNameDTO resourceNameDTO = new ResourceNameDTO();
            resourceNameDTO.setName(cpuName);

            resourceNameListDTO.getList().add(resourceNameDTO);
        }

        return resourceNameListDTO;
    }

    /**
     * Retrieves names of all GPUs and wraps them in a ResourceNameListDTO.
     *
     * @return A ResourceNameListDTO containing names of all GPUs.
     */
    public ResourceNameListDTO getAllGpuNames() {
        ResourceNameListDTO resourceNameListDTO = new ResourceNameListDTO();

        List<String> gpuNames = gpuRepository.findAll().stream().map(Gpu::getName).toList();

        for (String gpuName : gpuNames) {
            ResourceNameDTO resourceNameDTO = new ResourceNameDTO();
            resourceNameDTO.setName(gpuName);

            resourceNameListDTO.getList().add(resourceNameDTO);
        }

        return resourceNameListDTO;
    }
}