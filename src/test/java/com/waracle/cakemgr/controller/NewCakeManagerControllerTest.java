package com.waracle.cakemgr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.entities.Cake;
import com.waracle.cakemgr.service.CakeDataServiceTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.waracle.cakemgr.service.CakeDataService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CakeManagerController.class)
public class NewCakeManagerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CakeDataService mockCakeDataService;

    @Test
    public void checkThatMainPageContainsCorrectHeaderText() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("Welcome to the Cake Manager application!")))
                .andExpect(content().string(containsString("My UI design skills suck by the way")));
    }

    @Test
    public void checkThatMainPageContainsCorrectCakeTableData() throws Exception {
        lenient().when(mockCakeDataService.getAllCakes()).thenReturn(generateTestData());

        this.mockMvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(model().attribute("cakes", Matchers.equalTo(generateTestData())));
    }

    @Test
    public void checkThatMainPageContainsCorrectCakeJsonData() throws Exception {
        lenient().when(mockCakeDataService.getAllCakesAsJson()).thenReturn(generateTestDataAsJson());

        this.mockMvc.perform(get("/cakes"))
                .andExpect(view().name("cakes"))
                .andExpect(model().attribute("cakes_json", generateTestDataAsJson()));
    }

    private List<Cake> generateTestData() {
        var cakes = new ArrayList<Cake>();
        cakes.add(new Cake(UUID.fromString("11111111-1111-1111-1111-111111111111"), "title 1", "title 1", URI.create("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")));
        cakes.add(new Cake(UUID.fromString("22222222-2222-2222-2222-222222222222"), "title 2", "title 2", URI.create("http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg")));
        cakes.add(new Cake(UUID.fromString("33333333-3333-3333-3333-333333333333"), "title 3", "title 3", URI.create("http://cornandco.com/wp-content/uploads/2014/05/birthday-cake-popcorn.jpg")));
        return cakes;
    }

    private String generateTestDataAsJson() {
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(generateTestData());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }



}

