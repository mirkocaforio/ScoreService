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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements data retrieval strategy for GPU data.
 * This class is responsible for fetching GPU data from external sources,
 * parsing the data, and updating the local database with the new GPU information.
 */
@Component
public class GpuDataRetrievalStrategy implements DataRetrievalStrategy {
    /**
     * Logger for logging information and errors.
     */
    private final Logger LOGGER = LoggerFactory.getLogger(GpuDataRetrievalStrategy.class);
    /**
     * Repository for GPU data operations.
     */
    private final GpuRepository gpuRepository;

    /**
     * URLs for fetching GPU benchmarks data.
     */
    private static final String MAC_BENCHMARKS_URL = "https://browser.geekbench.com/mac-benchmarks.json";
    private static final String GPU_BENCHMARKS_URL = "https://browser.geekbench.com/gpu-benchmarks.json";

    /**
     * Constructs a GpuDataRetrievalStrategy with a GpuRepository.
     * @param gpuRepository Repository for GPU data operations.
     */
    @Autowired
    public GpuDataRetrievalStrategy(GpuRepository gpuRepository) {
        this.gpuRepository = gpuRepository;
    }

    /**
     * Retrieves GPU data from external sources and updates the local database.
     * This method fetches GPU data from predefined URLs, parses the data,
     * ensures uniqueness of the GPUs, and updates the local database with the new data.
     */
    @Override
    public void retrieveData() {
        List<String> gpuUrls = Arrays.asList(MAC_BENCHMARKS_URL, GPU_BENCHMARKS_URL);

        gpuRepository.deleteAll();

        Map<String, Gpu> uniqueGpus = new HashMap<>();

        for (String url : gpuUrls) {
            try {
                HttpResponse<String> response = sendHttpRequest(url);
                Gpu[] gpus = parseGpuData(response.body());

                // Special handling for Apple M series GPUs from the MAC_BENCHMARKS_URL
                if (url.equals(gpuUrls.get(0))) {
                    for (Gpu gpu : gpus) {
                        if (gpu.getDescription().contains("Apple M")) {
                            String normalizedGpuName = normalizeGpuName(gpu.getDescription());
                            gpu.setName(normalizedGpuName);
                            gpu.setDescription(normalizedGpuName);
                            uniqueGpus.putIfAbsent(gpu.getName(), gpu);
                        }
                    }
                } else {
                    for (Gpu gpu : gpus) {
                        uniqueGpus.putIfAbsent(gpu.getName(), gpu);
                    }
                }
            } catch (IOException | InterruptedException e) {
                LOGGER.error("Failed to update GPU data", e);
            }
        }

        gpuRepository.saveAll(uniqueGpus.values()); // Saves the unique GPU data to the database
    }

    /**
     * Sends an HTTP request to the specified URL and returns the response.
     * @param url The URL to send the request to.
     * @return The HTTP response.
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the operation is interrupted.
     */
    private HttpResponse<String> sendHttpRequest(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Parses the JSON response body into an array of Gpu objects.
     * @param responseBody The JSON response body to parse.
     * @return An array of Gpu objects.
     * @throws JsonProcessingException If parsing fails.
     */
    private Gpu[] parseGpuData(String responseBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);
        JsonNode devicesNode = rootNode.get("devices");

        return mapper.treeToValue(devicesNode, Gpu[].class);
    }

    /**
     * Normalizes the GPU name by removing any frequency information.
     * @param gpuName The GPU name to normalize.
     * @return The normalized GPU name.
     */
    private String normalizeGpuName(String gpuName) {
        return gpuName.replaceAll(" @.*", "");
    }
}