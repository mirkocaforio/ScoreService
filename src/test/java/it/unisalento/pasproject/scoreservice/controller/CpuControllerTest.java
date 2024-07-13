package it.unisalento.pasproject.scoreservice.controller;

import it.unisalento.pasproject.scoreservice.TestSecurityConfig;
import it.unisalento.pasproject.scoreservice.dto.CpuDTO;
import it.unisalento.pasproject.scoreservice.dto.CpuListDTO;
import it.unisalento.pasproject.scoreservice.dto.ResourceNameDTO;
import it.unisalento.pasproject.scoreservice.dto.ResourceNameListDTO;
import it.unisalento.pasproject.scoreservice.service.ScoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CpuController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Import(TestSecurityConfig.class)
public class CpuControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScoreService scoreService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CpuControllerTest.class);

    @Test
    @WithMockUser(roles = "UTENTE", username = "test@test.com")
    void getAllCpusReturnsCpuListWithMultipleCpus() throws Exception {
        CpuListDTO cpuListDTO = new CpuListDTO();

        CpuDTO cpuDTO1 = new CpuDTO();
        cpuDTO1.setName("Intel i7");
        cpuDTO1.setScore(100);

        CpuDTO cpuDTO2 = new CpuDTO();
        cpuDTO2.setName("AMD Ryzen 5");
        cpuDTO2.setScore(90);

        cpuListDTO.setCpuList(List.of(cpuDTO1, cpuDTO2));

        when(scoreService.getAllCpus()).thenReturn(cpuListDTO);

        mockMvc.perform(get("/api/scores/cpus"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cpuList").isArray())
                .andExpect(jsonPath("$.cpuList").isNotEmpty())
                .andExpect(jsonPath("$.cpuList[0].name").exists())
                .andExpect(jsonPath("$.cpuList[1].name").exists()).andReturn();
    }

    @Test
    @WithMockUser(roles = "UTENTE", username = "test@test.com")
    void getAllCpusHandlesNullCpuList() throws Exception {
        when(scoreService.getAllCpus()).thenReturn(null);

        mockMvc.perform(get("/api/scores/cpus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpus").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "UTENTE", username = "test@test.com")
    void getAllCpusHandlesEmptyCpuList() throws Exception {
        CpuListDTO cpuListDTO = new CpuListDTO();

        when(scoreService.getAllCpus()).thenReturn(cpuListDTO);

        mockMvc.perform(get("/api/scores/cpus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpuList").isEmpty());
    }

    @Test
    @WithMockUser(roles = "UTENTE", username = "test@test.com")
    void getAllCpuNamesReturnsNameListWithMultipleNames() throws Exception {
        ResourceNameListDTO resourceNameListDTO = new ResourceNameListDTO();
        ResourceNameDTO resourceNameDTO1 = new ResourceNameDTO();
        resourceNameDTO1.setName("Intel i7");
        ResourceNameDTO resourceNameDTO2 = new ResourceNameDTO();
        resourceNameDTO2.setName("AMD Ryzen 5");
        resourceNameListDTO.setList(List.of(resourceNameDTO1, resourceNameDTO2));

        when(scoreService.getAllCpuNames()).thenReturn(resourceNameListDTO);

        mockMvc.perform(get("/api/scores/cpus/names"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list").isArray())
                .andExpect(jsonPath("$.list").isNotEmpty())
                .andExpect(jsonPath("$.list[0].name").exists())
                .andExpect(jsonPath("$.list[1].name").exists()).andReturn();
    }

    @Test
    @WithMockUser(roles = "UTENTE", username = "test@test.com")
    void getAllCpuNamesHandlesNullNameList() throws Exception {
        when(scoreService.getAllCpuNames()).thenReturn(null);

        mockMvc.perform(get("/api/scores/cpus/names"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "UTENTE", username = "test@test.com")
    void getAllCpuNamesHandlesEmptyNameList() throws Exception {
        ResourceNameListDTO resourceNameListDTO = new ResourceNameListDTO();
        resourceNameListDTO.setList(Collections.emptyList());

        when(scoreService.getAllCpuNames()).thenReturn(resourceNameListDTO);

        mockMvc.perform(get("/api/scores/cpus/names"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list").isEmpty());
    }
}
