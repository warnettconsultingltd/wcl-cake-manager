package com.waracle.cakemgr.service;

import com.waracle.cakemgr.entities.Cake;
import com.waracle.cakemgr.service.CakeDataService;
import com.waracle.cakemgr.service.CakeRetriever;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class CakeDataServiceTest {
    private CakeDataService dataService;

    @Mock
    private CakeRetriever mockCakeRetriever;

    @BeforeEach
    public void setupDataService() {
        Mockito.lenient().when(mockCakeRetriever.getAllCakes()).thenReturn(expectedCakes());

        dataService = new CakeDataService(mockCakeRetriever);
    }

    @Test
    public void checkThatListOfCakesCorrectlyReturned() {
        Mockito.lenient().when(mockCakeRetriever.getAllCakesAsJson()).thenReturn(expectedJson());

        Assertions.assertEquals(expectedCakes(), dataService.getAllCakes());
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
                "title 3",
                URI.create("http://images/3.jpg")));
        return cakes;
    }

    private String expectedJson() {
        return "[{id=\"1\",title=\"Batenburg\",description=\"funny colours inside\",imageURL=\"http://images/1.gif\"},{id=\"2\",title=\"Eccles\",description=\"funny fruit inside\",imageURL=\"http://images/2.gif\"}]";
    }

}
