package it.unisalento.pasproject.scoreservice.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.pasproject.scoreservice.repositories.CpuRepository;
import it.unisalento.pasproject.scoreservice.domain.Cpu;
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
 * Implements data retrieval strategy for CPU data.
 * This class is responsible for fetching CPU data from external sources,
 * parsing the data, and updating the local database with the new CPU information.
 */
@Component
public class CpuDataRetrievalStrategy implements DataRetrievalStrategy {
    /**
     * Logger for logging information and errors.
     */
    private final Logger LOGGER = LoggerFactory.getLogger(CpuDataRetrievalStrategy.class);
    /**
     * Repository for CPU data operations.
     */
    private final CpuRepository cpuRepository;

    /**
     * URLs for fetching CPU benchmarks data.
     */
    private static final String MAC_BENCHMARKS_URL = "https://browser.geekbench.com/mac-benchmarks.json";
    private static final String PROCESSOR_BENCHMARKS_URL = "https://browser.geekbench.com/processor-benchmarks.json";

    /**
     * Constructs a CpuDataRetrievalStrategy with a CpuRepository.
     * @param cpuRepository Repository for CPU data operations.
     */
    @Autowired
    public CpuDataRetrievalStrategy(CpuRepository cpuRepository) {
        this.cpuRepository = cpuRepository;
    }

    /**
     * Retrieves CPU data from external sources and updates the local database.
     * This method fetches CPU data from predefined URLs, parses the data,
     * ensures uniqueness of the CPUs, and updates the local database with the new data.
     */
    @Override
    public void retrieveData() {
        List<String> cpuUrls = Arrays.asList(MAC_BENCHMARKS_URL, PROCESSOR_BENCHMARKS_URL);

        cpuRepository.deleteAll();

        Map<String, Cpu> uniqueCpus = new HashMap<>();

        for (String url : cpuUrls) {
            try {
                HttpResponse<String> response = sendHttpRequest(url);
                Cpu[] cpus = parseCpuData(response.body());

                if (url.equals(cpuUrls.get(0))) {
                    for (Cpu cpu : cpus) {
                        String normalizedCpuName = normalizeCpuName(cpu.getDescription());
                        cpu.setName(normalizedCpuName);
                        cpu.setDescription(normalizedCpuName);
                        uniqueCpus.putIfAbsent(cpu.getName(), cpu);
                    }
                } else {
                    for (Cpu cpu : cpus) {
                        uniqueCpus.putIfAbsent(cpu.getName(), cpu);
                    }
                }

                LOGGER.info("Retrieved {} unique CPUs from {}", uniqueCpus.size(), url);
            } catch (IOException | InterruptedException e) {
                LOGGER.error("Failed to update CPU data from {}", url, e);
            }
        }

        cpuRepository.saveAll(uniqueCpus.values()); // Saves the unique CPU data to the database
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
     * Parses the JSON response body into an array of Cpu objects.
     * @param responseBody The JSON response body to parse.
     * @return An array of Cpu objects.
     * @throws JsonProcessingException If parsing fails.
     */
    private Cpu[] parseCpuData(String responseBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);
        JsonNode devicesNode = rootNode.get("devices");

        return mapper.treeToValue(devicesNode, Cpu[].class);
    }

    /**
     * Normalizes the CPU name by removing any frequency information.
     * @param cpuName The CPU name to normalize.
     * @return The normalized CPU name.
     */
    private String normalizeCpuName(String cpuName) {
        return cpuName.replaceAll(" @.*", "");
    }
}