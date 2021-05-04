package com.waracle.cakemgr.uiclient.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.entities.Cake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CakeRetriever {
    private String cakesUrl;
    private String jsonCakesUrl;

    public CakeRetriever() {}

    public CakeRetriever(@Value("${cakes.url") String cakesUrl, @Value("${cakes.json.url") String jsonCakesUrl) {
        this.cakesUrl = cakesUrl;
        this.jsonCakesUrl = jsonCakesUrl;
    }

    public List<Cake> getAllCakes() {
        final var seedDataCakes = new ArrayList<Cake>();

        try {
            seedDataCakes.addAll(readCakesFromExternalSource());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return seedDataCakes;
    }

    List<Cake> readCakesFromExternalSource() throws IOException {
        final var objectMapper = new ObjectMapper();
        return objectMapper.readValue(new URL(cakesUrl),
                new TypeReference<>() {
                });
    }
    
    public String getAllCakesAsJson() {
        var restTemplate = new RestTemplate();
        return restTemplate.getForObject(URI.create(jsonCakesUrl), String.class);
    }

}
