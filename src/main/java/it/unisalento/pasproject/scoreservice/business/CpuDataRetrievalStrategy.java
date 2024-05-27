package it.unisalento.pasproject.scoreservice.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.pasproject.scoreservice.domain.Gpu;
import it.unisalento.pasproject.scoreservice.repositories.CpuRepository;
import it.unisalento.pasproject.scoreservice.domain.Cpu;
import it.unisalento.pasproject.scoreservice.repositories.GpuRepository;
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
public class CpuDataRetrievalStrategy implements DataRetrievalStrategy {
    private final Logger LOGGER = LoggerFactory.getLogger(CpuDataRetrievalStrategy.class);
    private final CpuRepository cpuRepository;

    private static final String MAC_BENCHMARKS_URL = "https://browser.geekbench.com/mac-benchmarks.json";
    private static final String PROCESSOR_BENCHMARKS_URL = "https://browser.geekbench.com/processor-benchmarks.json";

    @Autowired
    public CpuDataRetrievalStrategy(CpuRepository cpuRepository) {
        this.cpuRepository = cpuRepository;
    }

    @Override
    public void retrieveData() {
        List<String> cpuUrls = Arrays.asList(MAC_BENCHMARKS_URL, PROCESSOR_BENCHMARKS_URL);

        cpuRepository.deleteAll(); // Rimuove la vecchia collezione se esiste

        Map<String, Cpu> uniqueCpus = new HashMap<>();

        for (String url : cpuUrls) {
            try {
                HttpResponse<String> response = sendHttpRequest(url);
                Cpu[] cpus = parseCpuData(response.body());

                for (Cpu cpu : cpus) {
                    if (url.equals(cpuUrls.getFirst())) {
                        String normalizedCpuName = normalizeCpuName(cpu.getDescription());
                        cpu.setName(normalizedCpuName);
                        cpu.setDescription(normalizedCpuName);
                    }
                    uniqueCpus.putIfAbsent(cpu.getName(), cpu);
                }

                LOGGER.info("Retrieved {} unique CPUs from {}", uniqueCpus.size(), url);
            } catch (IOException | InterruptedException e) {
                LOGGER.error("Failed to update CPU data from {}", url, e);
            }
        }

        cpuRepository.saveAll(uniqueCpus.values()); // Salva i dati CPU unici nel database
    }

    private HttpResponse<String> sendHttpRequest(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    private Cpu[] parseCpuData(String responseBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);
        JsonNode devicesNode = rootNode.get("devices");

        return mapper.treeToValue(devicesNode, Cpu[].class);
    }

    private String normalizeCpuName(String cpuName) {
        return cpuName.replaceAll(" @.*", "");
    }
}
