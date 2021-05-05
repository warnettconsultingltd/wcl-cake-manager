package com.waracle.cakemgr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.entities.Cake;
import com.waracle.cakemgr.entities.SeedDataCake;
import com.waracle.cakemgr.persistence.CakeConverter;
import com.waracle.cakemgr.persistence.CakeEntity;
import com.waracle.cakemgr.persistence.CakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CakeDataService {
    private final CakeRepository cakeRepository;

    @Autowired
    public CakeDataService(CakeRepository cakeRepository) {
        this.cakeRepository = cakeRepository;
    }

    public void insertCake(Cake cake) {
        var cakeEntity = new CakeEntity();
        cakeEntity.setTitle(cake.title());
        cakeEntity.setDescription(cake.description());
        cakeEntity.setImageUrl(cake.imageURL().toString());
        cakeRepository.save(cakeEntity);
    }

    public boolean existsForTitle(String title) {
        return cakeRepository.existsByTitle(title);
    }

    public List<Cake> getAllCakes() {
        return cakeRepository.findAll().stream().map(CakeConverter::toCake)
                .collect(Collectors.toList());
    }

    public String getAllCakesAsJson() {

        final var objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(getAllCakes());
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return "";
    }
}
