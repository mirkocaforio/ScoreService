package it.unisalento.pasproject.scoreservice.business;

/**
 * Interface defining the strategy for data retrieval.
 * Implementations of this interface are responsible for fetching and processing data
 * from various sources (e.g., APIs, databases) and updating the application's data stores accordingly.
 * This pattern allows for flexible data retrieval strategies that can be swapped or modified
 * depending on the application's needs or the data source's requirements.
 */
public interface DataRetrievalStrategy {
    /**
     * Retrieves and processes data from a specified source.
     * Implementations should handle all aspects of data retrieval including
     * sending requests to data sources, parsing responses, and updating the application's data stores.
     * This method should encapsulate the entire process of data retrieval and be self-contained.
     */
    void retrieveData();
}
