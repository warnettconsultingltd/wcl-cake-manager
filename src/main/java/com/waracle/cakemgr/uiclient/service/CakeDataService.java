package com.waracle.cakemgr.uiclient.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.entities.Cake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CakeDataService {
    private final CakeRetriever cakeRetriever;

    public CakeDataService(CakeRetriever cakeRetriever) {
        this.cakeRetriever = cakeRetriever;
    }

    public List<Cake> getAllCakes() {
        return cakeRetriever.getAllCakes();
    }

    public String getAllCakesAsJson() {
        return cakeRetriever.getAllCakesAsJson();
    }
}
