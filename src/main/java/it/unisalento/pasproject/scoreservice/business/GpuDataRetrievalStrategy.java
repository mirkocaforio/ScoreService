package it.unisalento.pasproject.scoreservice.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.pasproject.scoreservice.domain.Cpu;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GpuDataRetrievalStrategy implements DataRetrievalStrategy {
    private final Logger LOGGER = LoggerFactory.getLogger(GpuDataRetrievalStrategy.class);
    private final GpuRepository gpuRepository;

    private static final String MAC_BENCHMARKS_URL = "https://browser.geekbench.com/mac-benchmarks.json";
    private static final String GPU_BENCHMARKS_URL = "https://browser.geekbench.com/gpu-benchmarks.json";

    @Autowired
    public GpuDataRetrievalStrategy(GpuRepository gpuRepository) {
        this.gpuRepository = gpuRepository;
    }

    @Override
    public void retrieveData() {
        List<String> gpuUrls = Arrays.asList(GPU_BENCHMARKS_URL, MAC_BENCHMARKS_URL);

        gpuRepository.deleteAll(); // Rimuove la vecchia collezione se esiste

        Map<String, Gpu> uniqueGpus = new HashMap<>();

        for (String url : gpuUrls) {
            try {
                HttpResponse<String> response = sendHttpRequest(url);
                Gpu[] gpus = parseGpuData(response.body());

                for (Gpu gpu : gpus) {
                    if (url.equals(gpuUrls.getFirst()) && gpu.getDescription().contains("Apple M")) {
                        String normalizedGpuName = normalizeGpuName(gpu.getDescription());
                        gpu.setName(normalizedGpuName);
                    }
                    uniqueGpus.putIfAbsent(gpu.getName(), gpu);
                }
            } catch (IOException | InterruptedException e) {
                LOGGER.error("Failed to update GPU data", e);
            }
        }

        gpuRepository.saveAll(uniqueGpus.values());

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

    private String normalizeGpuName(String gpuName) {
        return gpuName.replaceAll(" @.*", "");
    }
}
