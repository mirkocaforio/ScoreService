package it.unisalento.pasproject.scoreservice.controller;

import it.unisalento.pasproject.scoreservice.TestSecurityConfig;
import it.unisalento.pasproject.scoreservice.dto.GpuDTO;
import it.unisalento.pasproject.scoreservice.dto.GpuListDTO;
import it.unisalento.pasproject.scoreservice.dto.ResourceNameDTO;
import it.unisalento.pasproject.scoreservice.dto.ResourceNameListDTO;
import it.unisalento.pasproject.scoreservice.service.ScoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GpuController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Import(TestSecurityConfig.class)
public class GpuControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScoreService scoreService;

    @Test
    @WithMockUser(roles = "UTENTE", username = "test@test.com")
    void getAllGpusReturnsGpuListWithMultipleGpus() throws Exception {
        GpuListDTO gpuListDTO = new GpuListDTO();

        GpuDTO gpuDTO1 = new GpuDTO();
        gpuDTO1.setName("NVIDIA GTX 1080");
        gpuDTO1.setDescription("High-end GPU");
        gpuDTO1.setCuda(100.0);
        gpuDTO1.setOpencl(150.0);
        gpuDTO1.setMetal(8.0);
        gpuDTO1.setVulkan(256.0);

        GpuDTO gpuDTO2 = new GpuDTO();
        gpuDTO2.setName("AMD Radeon RX 580");
        gpuDTO2.setDescription("Mid-range GPU");
        gpuDTO2.setCuda(85.0);
        gpuDTO2.setOpencl(80.0);
        gpuDTO2.setMetal(75.0);
        gpuDTO2.setVulkan(70.0);

        gpuListDTO.setGpuList(List.of(gpuDTO1, gpuDTO2));

        given(scoreService.getAllGpus()).willReturn(gpuListDTO);

        mockMvc.perform(get("/api/scores/gpus"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gpuList").isArray())
                .andExpect(jsonPath("$.gpuList").isNotEmpty())
                .andExpect(jsonPath("$.gpuList[0].name").value("NVIDIA GTX 1080"))
                .andExpect(jsonPath("$.gpuList[1].name").value("AMD Radeon RX 580"));
    }

    @Test
    @WithMockUser(roles = "UTENTE", username = "test@test.com")
    void getAllGpusHandlesNullGpuList() throws Exception {
        when(scoreService.getAllGpus()).thenReturn(null);

        mockMvc.perform(get("/api/scores/gpus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gpuList").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "UTENTE", username = "test@test.com")
    void getAllGpusHandlesEmptyGpuList() throws Exception {
        GpuListDTO gpuListDTO = new GpuListDTO();
        gpuListDTO.setGpuList(Collections.emptyList());

        when(scoreService.getAllGpus()).thenReturn(gpuListDTO);

        mockMvc.perform(get("/api/scores/gpus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gpuList").isEmpty());
    }

    @Test
    @WithMockUser(roles = "UTENTE", username = "test@test.com")
    void getAllGpuNamesReturnsNameListWithMultipleNames() throws Exception {
        ResourceNameListDTO resourceNameListDTO = new ResourceNameListDTO();

        ResourceNameDTO resourceNameDTO1 = new ResourceNameDTO();
        resourceNameDTO1.setName("NVIDIA GTX 1080");

        ResourceNameDTO resourceNameDTO2 = new ResourceNameDTO();
        resourceNameDTO2.setName("AMD Radeon RX 580");

        resourceNameListDTO.setList(List.of(resourceNameDTO1, resourceNameDTO2));

        when(scoreService.getAllGpuNames()).thenReturn(resourceNameListDTO);

        mockMvc.perform(get("/api/scores/gpus/names"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list").isArray())
                .andExpect(jsonPath("$.list").isNotEmpty())
                .andExpect(jsonPath("$.list[0].name").value("NVIDIA GTX 1080"))
                .andExpect(jsonPath("$.list[1].name").value("AMD Radeon RX 580"));
    }

    @Test
    @WithMockUser(roles = "UTENTE", username = "test@test.com")
    void getAllGpuNamesHandlesNullNameList() throws Exception {
        when(scoreService.getAllGpuNames()).thenReturn(null);

        mockMvc.perform(get("/api/scores/gpus/names"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "UTENTE", username = "test@test.com")
    void getAllGpuNamesHandlesEmptyNameList() throws Exception {
        ResourceNameListDTO resourceNameListDTO = new ResourceNameListDTO();
        resourceNameListDTO.setList(Collections.emptyList());

        when(scoreService.getAllGpuNames()).thenReturn(resourceNameListDTO);

        mockMvc.perform(get("/api/scores/gpus/names"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list").isEmpty());
    }
}
