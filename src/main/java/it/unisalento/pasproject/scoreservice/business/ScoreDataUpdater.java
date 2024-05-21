package it.unisalento.pasproject.scoreservice.business;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScoreDataUpdater {
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ScoreDataUpdater.class);
    private final DataRetrievalStrategy cpuDataRetrievalStrategy;
    private final DataRetrievalStrategy gpuDataRetrievalStrategy;

    @Autowired
    public ScoreDataUpdater(@Qualifier("cpuDataRetrievalStrategy") DataRetrievalStrategy cpuDataRetrievalStrategy,
                            @Qualifier("gpuDataRetrievalStrategy") DataRetrievalStrategy gpuDataRetrievalStrategy) {
        this.cpuDataRetrievalStrategy = cpuDataRetrievalStrategy;
        this.gpuDataRetrievalStrategy = gpuDataRetrievalStrategy;
    }

    @PostConstruct
    @Scheduled(cron = "0 0 6 1 * ?") // Esegue il metodo il primo giorno del mese alle 6:00
    public void updateScoreData() {
        LOGGER.info("Updating score data...");

        cpuDataRetrievalStrategy.retrieveData();
        gpuDataRetrievalStrategy.retrieveData();
    }
}
