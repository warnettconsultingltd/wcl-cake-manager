package com.waracle.cakemgr.service;

import com.waracle.cakemgr.entities.SeedDataCake;
import com.waracle.cakemgr.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SeedDataService {

    @Autowired
    private SeedDataRetriever seedDataRetriever;

    @Autowired
    private CakeRepository cakeRepository;

    @PostConstruct
    public void populateDatabaseWithSeedData() {
        var seedData = seedDataRetriever.getSeedData();

        seedData.stream().map(this::convertSeedDataToCake)
                         .forEach(cakeRepository::save);
    }


    private CakeEntity convertSeedDataToCake(SeedDataCake cake) {
        var cakeEntity = new CakeEntity();
        cakeEntity.setTitle(cake.title());
        cakeEntity.setDescription(cake.desc());
        cakeEntity.setImageUrl(cake.image());
        return cakeEntity;
    }

}
