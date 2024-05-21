package it.unisalento.pasproject.scoreservice.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.pasproject.scoreservice.repositories.GpuRepository;
import it.unisalento.pasproject.scoreservice.domain.Gpu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Arrays;

@Component
public class GpuDataRetrievalStrategy implements DataRetrievalStrategy {
    private final Logger LOGGER = LoggerFactory.getLogger(GpuDataRetrievalStrategy.class);
    private final GpuRepository gpuRepository;

    private static final String GPU_BENCHMARKS_URL = "https://browser.geekbench.com/gpu-benchmarks.json";

    @Autowired
    public GpuDataRetrievalStrategy(GpuRepository gpuRepository) {
        this.gpuRepository = gpuRepository;
    }

    @Override
    public void retrieveData() {
        gpuRepository.deleteAll(); // Rimuove la vecchia collezione se esiste

        try {
            HttpResponse<String> response = sendHttpRequest(GPU_BENCHMARKS_URL);
            Gpu[] gpus = parseGpuData(response.body());

            gpuRepository.saveAll(Arrays.asList(gpus)); // Salva i dati GPU nel database
        } catch (IOException | InterruptedException e) {
            LOGGER.error("Failed to update GPU data", e);
        }
    }

    private HttpResponse<String> sendHttpRequest(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    private Gpu[] parseGpuData(String responseBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);
        JsonNode devicesNode = rootNode.get("devices");

        return mapper.treeToValue(devicesNode, Gpu[].class);
    }
}
