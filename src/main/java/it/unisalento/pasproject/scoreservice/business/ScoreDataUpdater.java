package it.unisalento.pasproject.scoreservice.business;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Component responsible for updating score data for CPUs and GPUs.
 * This class schedules and executes data retrieval strategies for both CPUs and GPUs,
 * ensuring that the latest performance scores are fetched and stored in the application's database.
 */
@Component
public class ScoreDataUpdater {
    /**
     * Logger for logging information and errors.
     */
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ScoreDataUpdater.class);

    /**
     * Strategy for retrieving CPU data.
     */
    private final DataRetrievalStrategy cpuDataRetrievalStrategy;

    /**
     * Strategy for retrieving GPU data.
     */
    private final DataRetrievalStrategy gpuDataRetrievalStrategy;

    /**
     * Constructs a ScoreDataUpdater with specified CPU and GPU data retrieval strategies.
     * @param cpuDataRetrievalStrategy Strategy for CPU data retrieval.
     * @param gpuDataRetrievalStrategy Strategy for GPU data retrieval.
     */
    @Autowired
    public ScoreDataUpdater(@Qualifier("cpuDataRetrievalStrategy") DataRetrievalStrategy cpuDataRetrievalStrategy,
                            @Qualifier("gpuDataRetrievalStrategy") DataRetrievalStrategy gpuDataRetrievalStrategy) {
        this.cpuDataRetrievalStrategy = cpuDataRetrievalStrategy;
        this.gpuDataRetrievalStrategy = gpuDataRetrievalStrategy;
    }

    /**
     * Scheduled task to update score data.
     * This method is executed automatically based on the configured schedule.
     * Currently, it runs on the first day of every month at 6:00 AM.
     */
    @PostConstruct
    @Scheduled(cron = "0 0 6 1 * ?") // Executes the method on the first day of the month at 6:00
    public void updateScoreData() {
        LOGGER.info("Updating score data...");

        cpuDataRetrievalStrategy.retrieveData();
        gpuDataRetrievalStrategy.retrieveData();
    }
}