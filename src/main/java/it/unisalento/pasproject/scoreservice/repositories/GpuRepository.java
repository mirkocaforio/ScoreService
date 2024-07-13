package it.unisalento.pasproject.scoreservice.repositories;

import it.unisalento.pasproject.scoreservice.domain.Gpu;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for {@link Gpu} entities.
 * This interface extends {@link MongoRepository}, providing CRUD operations and custom query methods for GPU entities.
 */
public interface GpuRepository extends MongoRepository<Gpu, String> {

    /**
     * Finds a GPU entity by its name.
     *
     * @param name The name of the GPU to find.
     * @return The found GPU entity or {@code null} if no GPU with the given name exists.
     */
    Gpu findByName(String name);
}