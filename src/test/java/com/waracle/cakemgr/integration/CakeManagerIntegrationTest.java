package com.waracle.cakemgr.integration;

import com.waracle.cakemgr.entities.Cake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CakeManagerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void checkThatMainPageContainsCorrectCakeTableData() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("cakes", hasSize(5)))
                .andExpect(model().attribute("cakes",
                        contains(any(Cake.class),
                                any(Cake.class),
                                any(Cake.class),
                                any(Cake.class),
                                any(Cake.class))));
    }

    @Test
    public void checkThatMainPageContainsCorrectCakeJsonData() throws Exception {
        this.mockMvc.perform(get("/cakes"))
                .andExpect(view().name("cakes"))
                .andExpect(model().attribute("cakes_json", notNullValue()));
    }

}
