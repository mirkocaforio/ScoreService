package it.unisalento.pasproject.scoreservice.repositories;

import it.unisalento.pasproject.scoreservice.domain.Gpu;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GpuRepository extends MongoRepository<Gpu, String> {
    Gpu findByName(String name);
}
