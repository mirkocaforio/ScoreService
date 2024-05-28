package it.unisalento.pasproject.scoreservice.service;

import it.unisalento.pasproject.scoreservice.domain.Cpu;
import it.unisalento.pasproject.scoreservice.domain.Gpu;
import it.unisalento.pasproject.scoreservice.dto.ScoreDTO;
import it.unisalento.pasproject.scoreservice.dto.ScoreMessageDTO;
import it.unisalento.pasproject.scoreservice.exceptions.InvalidResourceType;
import it.unisalento.pasproject.scoreservice.repositories.CpuRepository;
import it.unisalento.pasproject.scoreservice.repositories.GpuRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScoreMessageHandler {
    private final CpuRepository cpuRepository;
    private final GpuRepository gpuRepository;

    @Autowired
    public ScoreMessageHandler(CpuRepository cpuRepository, GpuRepository gpuRepository) {
        this.cpuRepository = cpuRepository;
        this.gpuRepository = gpuRepository;
    }

    @RabbitListener(queues = "${rabbitmq.queue.score.name}")
    public ScoreDTO receiveScoreMessage(ScoreMessageDTO message) {
        ScoreDTO scoreDTO = new ScoreDTO();
        switch (message.getResourceType()) {
            case "cpu" -> {
                Optional<Cpu> cpu = Optional.ofNullable(cpuRepository.findByName(message.getResourceName()));
                if (cpu.isPresent()) {
                    scoreDTO.setScore(cpu.get().getScore());
                    scoreDTO.setMulticore_score(cpu.get().getMulticore_score());
                } else {
                    return null;
                }
            }
            case "gpu" -> {
                Optional<Gpu> gpu = Optional.ofNullable(gpuRepository.findByName(message.getResourceName()));
                if (gpu.isPresent()) {
                    scoreDTO.setOpencl(gpu.get().getOpencl());
                    scoreDTO.setVulkan(gpu.get().getVulkan());
                    scoreDTO.setCuda(gpu.get().getCuda());
                } else {
                    return null;
                }
            }
            case "soc" -> {
                Optional<Cpu> cpu = Optional.ofNullable(cpuRepository.findByName(message.getResourceName()));
                Optional<Gpu> gpu = Optional.ofNullable(gpuRepository.findByName(message.getResourceName()));
                if (cpu.isPresent() && gpu.isPresent()) {
                    scoreDTO.setScore(cpu.get().getScore());
                    scoreDTO.setMulticore_score(cpu.get().getMulticore_score());
                    scoreDTO.setOpencl(gpu.get().getOpencl());
                    scoreDTO.setVulkan(gpu.get().getVulkan());
                    scoreDTO.setCuda(gpu.get().getCuda());
                    //TODO: Vedere se fino alla fine inglobare il Metal
                } else {
                    return null;
                }
            }
            default -> throw new InvalidResourceType("Invalid resource type: " + message.getResourceType());
        }
        return scoreDTO;
    }
}
