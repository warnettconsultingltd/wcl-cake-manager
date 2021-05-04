package com.waracle.cakemgr.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.entities.Cake;
import com.waracle.cakemgr.persistence.CakeEntity;
import com.waracle.cakemgr.persistence.CakeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CakeDataServiceTest {
    private CakeDataService dataService;

    @Mock
    private CakeRepository mockCakeRepository;

    @BeforeEach
    public void setupDataService() {
        lenient().when(mockCakeRepository.findAll()).thenReturn(persistedCakeEntities());
        final var objectMapper = new ObjectMapper();

        dataService = new CakeDataService(mockCakeRepository);
    }

    @Test
    public void checkThatListOfCakesCorrectlyReturned() {
        lenient().when(mockCakeRepository.findAll()).thenReturn(persistedCakeEntities());

        assertEquals(expectedCakes(), dataService.getAllCakes());
    }

    private List<Cake> expectedCakes() {
        var cakes = new ArrayList<Cake>();

        cakes.add(new Cake(UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "title 1",
                "description 1",
                URI.create("http://images/1.jpg")));
        cakes.add(new Cake(UUID.fromString("22222222-2222-2222-2222-222222222222"),
                "title 2",
                "description 2",
                URI.create("http://images/2.jpg")));
        cakes.add(new Cake(UUID.fromString("33333333-3333-3333-3333-333333333333"),
                "title 3",
                "description 3",
                URI.create("http://images/3.jpg")));

        return cakes;
    }

    private List<CakeEntity> persistedCakeEntities() {
        var cakes = new ArrayList<CakeEntity>();

        var firstCakeEntity = new CakeEntity();
        firstCakeEntity.setId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        firstCakeEntity.setTitle("title 1");
        firstCakeEntity.setDescription("description 1");
        firstCakeEntity.setImageUrl("http://images/1.jpg");

        cakes.add(firstCakeEntity);

        var secondCakeEntity = new CakeEntity();
        secondCakeEntity.setId(UUID.fromString("22222222-2222-2222-2222-222222222222"));
        secondCakeEntity.setTitle("title 2");
        secondCakeEntity.setDescription("description 2");
        secondCakeEntity.setImageUrl("http://images/2.jpg");

        cakes.add(secondCakeEntity);

        var thirdCakeEntity = new CakeEntity();
        thirdCakeEntity.setId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
        thirdCakeEntity.setTitle("title 3");
        thirdCakeEntity.setDescription("description 3");
        thirdCakeEntity.setImageUrl("http://images/3.jpg");

        cakes.add(thirdCakeEntity);

        return cakes;
    }

    private String expectedJson() {
        return "[{id=\"1\",title=\"Batenburg\",description=\"funny colours inside\",imageURL=\"http://images/1.gif\"},{id=\"2\",title=\"Eccles\",description=\"funny fruit inside\",imageURL=\"http://images/2.gif\"}]";
    }

}
