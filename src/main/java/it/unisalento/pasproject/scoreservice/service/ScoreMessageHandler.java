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

/**
 * Service class responsible for handling score messages.
 * It listens for messages on a RabbitMQ queue and processes them based on the resource type (CPU, GPU, SoC).
 * The processing involves fetching the score details from the corresponding repository and assembling a {@link ScoreDTO}.
 */
@Service
public class ScoreMessageHandler {
    private final CpuRepository cpuRepository;
    private final GpuRepository gpuRepository;

    /**
     * Constructs a ScoreMessageHandler with the necessary repositories.
     *
     * @param cpuRepository The repository for CPU entities.
     * @param gpuRepository The repository for GPU entities.
     */
    @Autowired
    public ScoreMessageHandler(CpuRepository cpuRepository, GpuRepository gpuRepository) {
        this.cpuRepository = cpuRepository;
        this.gpuRepository = gpuRepository;
    }

    /**
     * Receives and processes score messages from a RabbitMQ queue.
     * The method determines the type of resource (CPU, GPU, SoC) from the message and fetches the corresponding scores.
     * If the resource is found, it populates and returns a {@link ScoreDTO}; otherwise, it returns null.
     *
     * @param message The score message received from the queue.
     * @return A {@link ScoreDTO} with the score details or null if the resource is not found.
     * @throws InvalidResourceType if the resource type in the message is not recognized.
     */
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