package it.unisalento.pasproject.scoreservice.repositories;

import it.unisalento.pasproject.scoreservice.domain.Cpu;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for {@link Cpu} entities.
 * This interface extends {@link MongoRepository}, providing CRUD operations and custom query methods for CPU entities.
 */
public interface CpuRepository extends MongoRepository<Cpu, String> {

    /**
     * Finds a CPU entity by its name.
     *
     * @param name The name of the CPU to find.
     * @return The found CPU entity or {@code null} if no CPU with the given name exists.
     */
    Cpu findByName(String name);
}