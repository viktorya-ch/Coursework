package org.skypro.Star.Bank;


import org.glassfish.hk2.utilities.cache.Cache;
import org.junit.jupiter.api.Test;
import org.skypro.Star.Bank.controller.ManagementController;
import org.skypro.Star.Bank.repository.CachedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.data.relational.core.sql.When.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManagementController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CachedUserRepository cachedUserRepository;

    @MockitoBean
    private CacheManager cacheManager;

    @MockitoBean
    private BuildProperties buildProperties;

    @Test

    void clearCaches_ShouldReturnNoContent() throws Exception {
        Cache mockCache = mock(Cache.class);
        when(cacheManager.getCacheNames()).thenReturn(List.of("testCache"));
        when(cacheManager.getCache("testCache")).thenReturn(mockCache);
        doNothing().when(cachedUserRepository).clearAllCaches();

        mockMvc.perform(post("/management/clear-caches"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getServiceInfo_ShouldReturnInfo() throws Exception {
        when(buildProperties.getName()).thenReturn("test-service");
        when(buildProperties.getVersion()).thenReturn("1.0.0");

        mockMvc.perform(get("/management/info")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("test-service"))
                .andExpect(jsonPath("$.version").value("1.0.0"));
    }
}

