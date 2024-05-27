package it.unisalento.pasproject.scoreservice.repositories;

import it.unisalento.pasproject.scoreservice.domain.Cpu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CpuRepository extends MongoRepository<Cpu, String> {
    Cpu findByName(String name);
}
