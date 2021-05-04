package com.waracle.cakemgr.persistence;

import com.waracle.cakemgr.entities.Cake;

import java.net.URI;

public final class CakeConverter {
    private CakeConverter() {}

    public static CakeEntity toCakeEntity(Cake cake) {
        var cakeEntity = new CakeEntity();

        cakeEntity.setId(cake.id());
        cakeEntity.setTitle(cake.title());
        cakeEntity.setDescription(cake.description());
        cakeEntity.setImageUrl(cake.imageURL().toString());

        return cakeEntity;
    }



    public static Cake toCake(CakeEntity cakeEntity) {
        return new Cake(cakeEntity.getId(),
                    cakeEntity.getTitle(),
                cakeEntity.getDescription(),
                URI.create(cakeEntity.getImageUrl()));
   }
}
